package org.valkyrienskies.vsrnd.content.machines.centrifuge;

import com.simibubi.create.content.contraptions.AssemblyException;
import com.simibubi.create.content.contraptions.ControlledContraptionEntity;
import com.simibubi.create.content.contraptions.bearing.MechanicalBearingBlockEntity;
import com.simibubi.create.content.equipment.goggles.IHaveGoggleInformation;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.foundation.fluid.SmartFluidTank;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.valkyrienskies.vsrnd.platform.SmartFluidTankBlockEntity;
import org.valkyrienskies.vsrnd.util.fluid.VSCFluidTankBehaviour;

import java.util.List;

public class CentrifugeBlockEntity extends MechanicalBearingBlockEntity implements IHaveGoggleInformation, SmartFluidTankBlockEntity {


    protected SmartFluidTank tankInventory;
    public boolean active = false;
    private float prevAngle;
    protected boolean assembleNextTick;
    public int runningTicks;
    public int processingTicks;
    protected Recipe<?> currentRecipe;

    public CentrifugeBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Override
    public float getInterpolatedAngle(float partialTicks) {
        if (isVirtual())
            return Mth.lerp(partialTicks + .5f, prevAngle, angle);
        if (movedContraption == null || movedContraption.isStalled() || !running)
            partialTicks = 0;
        float angularSpeed = getAngularSpeed();
        if (sequencedAngleLimit >= 0)
            angularSpeed = (float) Mth.clamp(angularSpeed, -sequencedAngleLimit, sequencedAngleLimit);
        return Mth.lerp(partialTicks, angle, angle + angularSpeed);
    }

    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        compound.putBoolean("Running", running);
        compound.putFloat("Angle", angle);
        if (sequencedAngleLimit >= 0)
            compound.putDouble("SequencedAngleLimit", sequencedAngleLimit);
        AssemblyException.write(compound, lastException);
        super.write(compound, clientPacket);
    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        if (wasMoved) {
            super.read(compound, clientPacket);
            return;
        }

        float angleBefore = angle;
        running = compound.getBoolean("Running");
        angle = compound.getFloat("Angle");
        sequencedAngleLimit = compound.contains("SequencedAngleLimit") ? compound.getDouble("SequencedAngleLimit") : -1;
        lastException = AssemblyException.read(compound);
        super.read(compound, clientPacket);
        if (!clientPacket)
            return;
        if (running) {
            if (movedContraption == null || !movedContraption.isStalled()) {
                clientAngleDiff = AngleHelper.getShortestAngleDiff(angleBefore, angle);
                angle = angleBefore;
            }
        } else
            movedContraption = null;
    }



    @Override
    public void tick() {
        super.tick();

        if (runningTicks >= 40) {
            running = false;
            runningTicks = 0;
            return;
        }

        float speed = Math.abs(getSpeed());
        if (running && level != null) {
            if (level.isClientSide && runningTicks == 20)
                if (isVirtual() && runningTicks == 20) {
                    if (processingTicks < 0) {
                        float recipeSpeed = 1;
                        if (currentRecipe instanceof ProcessingRecipe) {
                            int t = ((ProcessingRecipe<?>) currentRecipe).getProcessingDuration();
                            if (t != 0)
                                recipeSpeed = t / 100f;
                        }

                        processingTicks = Mth.clamp((Mth.log2((int) (512 / speed))) * Mth.ceil(recipeSpeed * 15) + 1, 1, 512);




                    } else {
                        processingTicks--;
                        if (processingTicks == 0) {
                            runningTicks++;
                            processingTicks = -1;
                            sendData();
                        }
                    }
                }

            if (runningTicks != 20)
                runningTicks++;
        }

        prevAngle = angle;
        if (level.isClientSide)
            clientAngleDiff /= 2;

        if (!level.isClientSide && assembleNextTick) {
            assembleNextTick = false;
            if (running) {
                boolean canDisassemble = movementMode.get() == RotationMode.ROTATE_PLACE
                        || (isNearInitialAngle() && movementMode.get() == RotationMode.ROTATE_PLACE_RETURNED);
                if (speed == 0 && (canDisassemble || movedContraption == null || movedContraption.getContraption()
                        .getBlocks()
                        .isEmpty())) {
                    if (movedContraption != null)
                        movedContraption.getContraption()
                                .stop(level);
                    disassemble();
                    return;
                }
            } else {
                if (speed == 0 && !isWindmill())
                    return;
                assemble();
            }
        }

        if (!running)
            return;

        if (!(movedContraption != null && movedContraption.isStalled())) {
            float angularSpeed = getAngularSpeed();
            if (sequencedAngleLimit >= 0) {
                angularSpeed = (float) Mth.clamp(angularSpeed, -sequencedAngleLimit, sequencedAngleLimit);
                sequencedAngleLimit = Math.max(0, sequencedAngleLimit - Math.abs(angularSpeed));
            }
            float newAngle = angle + angularSpeed;
            angle = (float) (newAngle % 360);
        }

        applyRotation();
    }
    @Override
    public VSCFluidTankBehaviour getFluidTankBehaviour() {
        return null;
    }

    public boolean isRunning() {
        return running;
    }

    public void setAngle(float forcedAngle) {
        angle = forcedAngle;
    }

    public ControlledContraptionEntity getMovedContraption() {
        return movedContraption;
    }
}
