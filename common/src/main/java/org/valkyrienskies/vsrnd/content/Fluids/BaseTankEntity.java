package org.valkyrienskies.vsrnd.content.Fluids;

import static java.lang.Math.abs;

import java.util.List;

import javax.annotation.Nullable;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.fluids.tank.BoilerData;
import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.fluids.tank.FluidTankBlock.Shape;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.blockEntity.IMultiBlockEntityContainer;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import com.simibubi.create.foundation.utility.animation.LerpedFloat.Chaser;
import com.simibubi.create.infrastructure.config.AllConfigs;

import io.github.fabricators_of_create.porting_lib.block.CustomRenderBoundingBoxBlockEntity;
import io.github.fabricators_of_create.porting_lib.transfer.TransferUtil;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTank;
import io.github.fabricators_of_create.porting_lib.transfer.fluid.FluidTransferable;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributeHandler;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariantAttributes;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class BaseTankEntity extends SmartBlockEntity implements IHaveGoggleInformation, IMultiBlockEntityContainer.Fluid, CustomRenderBoundingBoxBlockEntity, FluidTransferable {

    private static final int MAX_SIZE = 3;

    protected boolean forceFluidLevelUpdate;
    protected SmartFluidTank tankInventory;
    protected FluidTank exposedTank;
    protected BlockPos controller;
    protected BlockPos lastKnownPos;
    protected boolean updateConnectivity;
    protected boolean window;
    protected int luminosity;
    protected int width;
    protected int height;
    

    private static final int SYNC_RATE = 8;
    protected int syncCooldown;
    protected boolean queuedSync;

    // For rendering purposes only
    private LerpedFloat fluidLevel;

    public BaseTankEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        tankInventory = createInventory();
        forceFluidLevelUpdate = true;
        updateConnectivity = false;
        window = true;
        height = 1;
        width = 1;
//		refreshCapability(); // fabric: lazy init to prevent access too early
    }

    protected SmartFluidTank createInventory() {
        return new SmartFluidTank(getCapacityMultiplier(), this::onFluidStackChanged);
    }

    protected void updateConnectivity() {
        updateConnectivity = false;
        if (level.isClientSide)
            return;
        if (!isController())
            return;
        ConnectivityHandler.formMulti(this);
    }

    @Override
    public void tick() {
        super.tick();
        if (syncCooldown > 0) {
            syncCooldown--;
            if (syncCooldown == 0 && queuedSync)
                sendData();
        }

        if (lastKnownPos == null)
            lastKnownPos = getBlockPos();
        else if (!lastKnownPos.equals(worldPosition) && worldPosition != null) {
            onPositionChanged();
            return;
        }

        if (updateConnectivity)
            updateConnectivity();
        if (fluidLevel != null)
            fluidLevel.tickChaser();
        
    }

    @Override
    public BlockPos getLastKnownPos() {
        return lastKnownPos;
    }

    @Override
    public boolean isController() {
        return controller == null || worldPosition.getX() == controller.getX()
                && worldPosition.getY() == controller.getY() && worldPosition.getZ() == controller.getZ();
    }

    @Override
    public void initialize() {
        super.initialize();
        sendData();
        if (level.isClientSide)
            invalidateRenderBoundingBox();
    }

    private void onPositionChanged() {
        removeController(true);
        lastKnownPos = worldPosition;
    }

    protected void onFluidStackChanged(FluidStack newFluidStack) {
        if (!hasLevel())
            return;

        FluidVariantAttributeHandler handler = FluidVariantAttributes.getHandlerOrDefault(newFluidStack.getFluid());
        FluidVariant variant = newFluidStack.getType();
        int luminosity = (int) (handler.getLuminance(variant) / 1.2f);
        boolean reversed = handler.isLighterThanAir(variant);
        int maxY = (int) ((getFillState() * height) + 1);

        for (int yOffset = 0; yOffset < height; yOffset++) {
            boolean isBright = reversed ? (height - yOffset <= maxY) : (yOffset < maxY);
            int actualLuminosity = isBright ? luminosity : luminosity > 0 ? 1 : 0;

            for (int xOffset = 0; xOffset < width; xOffset++) {
                for (int zOffset = 0; zOffset < width; zOffset++) {
                    BlockPos pos = this.worldPosition.offset(xOffset, yOffset, zOffset);
                    BaseTankEntity tankAt = ConnectivityHandler.partAt(getType(), level, pos);
                    if (tankAt == null)
                        continue;
                    level.updateNeighbourForOutputSignal(pos, tankAt.getBlockState()
                            .getBlock());
                    if (tankAt.luminosity == actualLuminosity)
                        continue;
                    tankAt.setLuminosity(actualLuminosity);
                }
            }
        }

        if (!level.isClientSide) {
            setChanged();
            sendData();
        }

        if (isVirtual()) {
            if (fluidLevel == null)
                fluidLevel = LerpedFloat.linear()
                        .startWithValue(getFillState());
            fluidLevel.chase(getFillState(), .5f, Chaser.EXP);
        }
    }

    protected void setLuminosity(int luminosity) {
        if (level.isClientSide)
            return;
        if (this.luminosity == luminosity)
            return;
        this.luminosity = luminosity;
        updateStateLuminosity();
    }

    protected void updateStateLuminosity() {
        if (level.isClientSide)
            return;
        int actualLuminosity = luminosity;
        BaseTankEntity controllerBE = getControllerBE();
        if (controllerBE == null || !controllerBE.window)
            actualLuminosity = 0;
        refreshBlockState();
        BlockState state = getBlockState();
        if (state.getValue(FluidTankBlock.LIGHT_LEVEL) != actualLuminosity) {
            level.setBlock(worldPosition, state.setValue(FluidTankBlock.LIGHT_LEVEL, actualLuminosity), 23);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public BaseTankEntity getControllerBE() {
        if (isController())
            return this;
        BlockEntity blockEntity = level.getBlockEntity(controller);
        if (blockEntity instanceof BaseTankEntity)
            return (BaseTankEntity) blockEntity;
        return null;
    }

    public void applyFluidTankSize(int blocks) {
        tankInventory.setCapacity(blocks * getCapacityMultiplier());
        long overflow = tankInventory.getFluidAmount() - tankInventory.getCapacity();
        if (overflow > 0)
            TransferUtil.extract(tankInventory, tankInventory.variant, overflow);
        forceFluidLevelUpdate = true;
    }

    public void removeController(boolean keepFluids) {
        if (level.isClientSide)
            return;
        updateConnectivity = true;
        if (!keepFluids)
            applyFluidTankSize(1);
        controller = null;
        width = 1;
        height = 1;
        onFluidStackChanged(tankInventory.getFluid());

        BlockState state = getBlockState();
        if (FluidTankBlock.isTank(state)) {
            state = state.setValue(FluidTankBlock.BOTTOM, true);
            state = state.setValue(FluidTankBlock.TOP, true);
            state = state.setValue(FluidTankBlock.SHAPE, window ? Shape.WINDOW : Shape.PLAIN);
            getLevel().setBlock(worldPosition, state, 23);
        }

        refreshCapability();
        setChanged();
        sendData();
    }

    public void toggleWindows() {
        BaseTankEntity be = getControllerBE();
        if (be == null)
            return;
        be.setWindows(!be.window);
    }



    public void sendDataImmediately() {
        syncCooldown = 0;
        queuedSync = false;
        sendData();
    }

    @Override
    public void sendData() {
        if (syncCooldown > 0) {
            queuedSync = true;
            return;
        }
        super.sendData();
        queuedSync = false;
        syncCooldown = SYNC_RATE;
    }

    public void setWindows(boolean window) {
        this.window = window;
        for (int yOffset = 0; yOffset < height; yOffset++) {
            for (int xOffset = 0; xOffset < width; xOffset++) {
                for (int zOffset = 0; zOffset < width; zOffset++) {

                    BlockPos pos = this.worldPosition.offset(xOffset, yOffset, zOffset);
                    BlockState blockState = level.getBlockState(pos);
                    if (!FluidTankBlock.isTank(blockState))
                        continue;

                    Shape shape = Shape.PLAIN;
                    if (window) {
                        // SIZE 1: Every tank has a window
                        if (width == 1)
                            shape = Shape.WINDOW;
                        // SIZE 2: Every tank has a corner window
                        if (width == 2)
                            shape = xOffset == 0 ? zOffset == 0 ? Shape.WINDOW_NW : Shape.WINDOW_SW
                                    : zOffset == 0 ? Shape.WINDOW_NE : Shape.WINDOW_SE;
                        // SIZE 3: Tanks in the center have a window
                        if (width == 3 && abs(abs(xOffset) - abs(zOffset)) == 1)
                            shape = Shape.WINDOW;
                    }

                    level.setBlock(pos, blockState.setValue(FluidTankBlock.SHAPE, shape), 23);
                    BlockEntity be = level.getBlockEntity(pos);
                    if (be instanceof BaseTankEntity tankAt)
                        tankAt.updateStateLuminosity();
                    level.getChunkSource()
                            .getLightEngine()
                            .checkBlock(pos);
                }
            }
        }
    }


    @Override
    public void setController(BlockPos controller) {
        if (level.isClientSide && !isVirtual())
            return;
        if (controller.equals(this.controller))
            return;
        this.controller = controller;
        refreshCapability();
        setChanged();
        sendData();
    }

    private void refreshCapability() {
        exposedTank = handlerForCapability();
    }

    private FluidTank handlerForCapability() {
        if (isController()) {
            return tankInventory;
        } else if (getControllerBE() != null) {
            return getControllerBE().handlerForCapability();
        }
        return new FluidTank(0);
    }



    @Override
    public BlockPos getController() {
        return isController() ? worldPosition : controller;
    }

    @Override
    protected AABB createRenderBoundingBox() {
        if (isController())
            return super.createRenderBoundingBox().expandTowards(width - 1, height - 1, width - 1);
        else
            return super.createRenderBoundingBox();
    }

    @Nullable
    public BaseTankEntity getOtherFluidTankBlockEntity(Direction direction) {
        BlockEntity otherBE = level.getBlockEntity(worldPosition.relative(direction));
        if (otherBE instanceof BaseTankEntity)
            return (BaseTankEntity) otherBE;
        return null;
    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        BaseTankEntity controllerBE = getControllerBE();
        if (controllerBE == null)
            return false;
        return containedFluidTooltip(tooltip, isPlayerSneaking,
                controllerBE.getFluidStorage(null));
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);

        BlockPos controllerBefore = controller;
        int prevSize = width;
        int prevHeight = height;
        int prevLum = luminosity;

        updateConnectivity = compound.contains("Uninitialized");
        luminosity = compound.getInt("Luminosity");
        controller = null;
        lastKnownPos = null;

        if (compound.contains("LastKnownPos"))
            lastKnownPos = NbtUtils.readBlockPos(compound.getCompound("LastKnownPos"));
        if (compound.contains("Controller"))
            controller = NbtUtils.readBlockPos(compound.getCompound("Controller"));

        if (isController()) {
            window = compound.getBoolean("Window");
            width = compound.getInt("Size");
            height = compound.getInt("Height");
            tankInventory.setCapacity(getTotalTankSize() * getCapacityMultiplier());
            tankInventory.readFromNBT(compound.getCompound("TankContent"));
            if (tankInventory.getSpace() < 0) {
                try (Transaction t = TransferUtil.getTransaction()) {
                    tankInventory.extract(tankInventory.variant, -tankInventory.getSpace(), t);
                    t.commit();
                }
            }
        }



        if (compound.contains("ForceFluidLevel") || fluidLevel == null)
            fluidLevel = LerpedFloat.linear()
                    .startWithValue(getFillState());

        if (!clientPacket)
            return;

        boolean changeOfController =
                controllerBefore == null ? controller != null : !controllerBefore.equals(controller);
        if (changeOfController || prevSize != width || prevHeight != height) {
            if (hasLevel())
                level.sendBlockUpdated(getBlockPos(), getBlockState(), getBlockState(), 16);
            if (isController())
                tankInventory.setCapacity(getCapacityMultiplier() * getTotalTankSize());
            invalidateRenderBoundingBox();
        }
        if (isController()) {
            float fillState = getFillState();
            if (compound.contains("ForceFluidLevel") || fluidLevel == null)
                fluidLevel = LerpedFloat.linear()
                        .startWithValue(fillState);
            fluidLevel.chase(fillState, 0.5f, Chaser.EXP);
        }
        if (luminosity != prevLum && hasLevel())
            level.getChunkSource()
                    .getLightEngine()
                    .checkBlock(worldPosition);

        if (compound.contains("LazySync"))
            fluidLevel.chase(fluidLevel.getChaseTarget(), 0.125f, Chaser.EXP);
    }

    public float getFillState() {
        return (float) tankInventory.getFluidAmount() / tankInventory.getCapacity();
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        if (updateConnectivity)
            compound.putBoolean("Uninitialized", true);
        if (lastKnownPos != null)
            compound.put("LastKnownPos", NbtUtils.writeBlockPos(lastKnownPos));
        if (!isController())
            compound.put("Controller", NbtUtils.writeBlockPos(controller));
        if (isController()) {
            compound.putBoolean("Window", window);
            compound.put("TankContent", tankInventory.writeToNBT(new CompoundTag()));
            compound.putInt("Size", width);
            compound.putInt("Height", height);
        }
        compound.putInt("Luminosity", luminosity);
        super.write(compound, clientPacket);

        if (!clientPacket)
            return;
        if (forceFluidLevelUpdate)
            compound.putBoolean("ForceFluidLevel", true);
        if (queuedSync)
            compound.putBoolean("LazySync", true);
        forceFluidLevelUpdate = false;
    }

    @Override
    public void invalidate() {
        super.invalidate();
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        registerAwardables(behaviours, AllAdvancements.STEAM_ENGINE_MAXED, AllAdvancements.PIPE_ORGAN);
    }

    public FluidTank getTankInventory() {
        return tankInventory;
    }

    public int getTotalTankSize() {
        return width * width * height;
    }

    public static int getMaxSize() {
        return MAX_SIZE;
    }

    public static long getCapacityMultiplier() {
        return AllConfigs.server().fluids.fluidTankCapacity.get() * FluidConstants.BUCKET;
    }

    public static int getMaxHeight() {
        return AllConfigs.server().fluids.fluidTankMaxHeight.get();
    }

    public LerpedFloat getFluidLevel() {
        return fluidLevel;
    }

    public void setFluidLevel(LerpedFloat fluidLevel) {
        this.fluidLevel = fluidLevel;
    }

    @Override
    public void preventConnectivityUpdate() {
        updateConnectivity = false;
    }

    // fabric: see comment in FluidTankItem
    public void queueConnectivityUpdate() {
        updateConnectivity = true;
    }

    @Override
    public void notifyMultiUpdated() {
        BlockState state = this.getBlockState();
        if (FluidTankBlock.isTank(state)) { // safety
            state = state.setValue(FluidTankBlock.BOTTOM, getController().getY() == getBlockPos().getY());
            state = state.setValue(FluidTankBlock.TOP, getController().getY() + height - 1 == getBlockPos().getY());
            level.setBlock(getBlockPos(), state, 6);
        }
        if (isController())
            setWindows(window);
        onFluidStackChanged(tankInventory.getFluid());
        setChanged();
    }

    @Override
    public void setExtraData(@Nullable Object data) {
        if (data instanceof Boolean)
            window = (boolean) data;
    }

    @Override
    @Nullable
    public Object getExtraData() {
        return window;
    }

    @Override
    public Object modifyExtraData(Object data) {
        if (data instanceof Boolean windows) {
            windows |= window;
            return windows;
        }
        return data;
    }

    @Override
    public Direction.Axis getMainConnectionAxis() {
        return Direction.Axis.Y;
    }

    @Override
    public int getMaxLength(Direction.Axis longAxis, int width) {
        if (longAxis == Direction.Axis.Y)
            return getMaxHeight();
        return getMaxWidth();
    }

    @Override
    public int getMaxWidth() {
        return MAX_SIZE;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    public boolean hasTank() {
        return true;
    }

    @Override
    public long getTankSize(int tank) {
        return getCapacityMultiplier();
    }

    @Override
    public void setTankSize(int tank, int blocks) {
        applyFluidTankSize(blocks);
    }

    @Override
    public FluidTank getTank(int tank) {
        return tankInventory;
    }

    @Override
    public FluidStack getFluid(int tank) {
        return tankInventory.getFluid()
                .copy();
    }

    @Nullable
    @Override
    public Storage<FluidVariant> getFluidStorage(@Nullable Direction direction) {
        if (exposedTank == null)
            refreshCapability();
        return exposedTank;
    }
}
