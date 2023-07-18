package org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.equipment.wrench.IWrenchable;
import com.simibubi.create.foundation.block.IBE;
import com.simibubi.create.foundation.blockEntity.ComparatorUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import org.valkyrienskies.vsrnd.content.Fluids.BaseTankBlock;

import java.util.function.Consumer;


public class TitaniumTankBlock extends BaseTankBlock implements IWrenchable, IBE<TitaniumTankBlockEntity> {

    public static final BooleanProperty TOP = BooleanProperty.create("top");
    public static final BooleanProperty BOTTOM = BooleanProperty.create("bottom");
    public static final EnumProperty<Shape> SHAPE = EnumProperty.create("shape", TitaniumTankBlock.Shape.class);
    public static final IntegerProperty LIGHT_LEVEL = IntegerProperty.create("light_level", 0, 15);

    private boolean creative;

    protected TitaniumTankBlock(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }


    private static Properties setLightFunction(Properties properties) {
        return properties.lightLevel(state -> state.getValue(LIGHT_LEVEL));
    }


    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean moved) {
        if (oldState.getBlock() == state.getBlock())
            return;
        if (moved)
            return;
        // fabric: see comment in FluidTankItem
        Consumer<TitaniumTankBlockEntity> consumer = TitaniumTankItem.IS_PLACING_NBT
                ? TitaniumTankBlockEntity::queueConnectivityUpdate
                : TitaniumTankBlockEntity::updateConnectivity;
        withBlockEntityDo(world, pos, consumer);
    }


    // Handled via LIGHT_LEVEL state property
//	@Override
//	public int getLightEmission(BlockState state, BlockGetter world, BlockPos pos) {
//		FluidTankBlockEntity tankAt = ConnectivityHandler.partAt(getBlockEntityType(), world, pos);
//		if (tankAt == null)
//			return 0;
//		FluidTankBlockEntity controllerBE = tankAt.getControllerBE();
//		if (controllerBE == null || !controllerBE.window)
//			return 0;
//		return tankAt.luminosity;
//	}

    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        withBlockEntityDo(context.getLevel(), context.getClickedPos(), TitaniumTankBlockEntity::toggleWindows);
        return InteractionResult.SUCCESS;
    }


    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && (state.getBlock() != newState.getBlock() || !newState.hasBlockEntity())) {
            BlockEntity be = world.getBlockEntity(pos);
            if (!(be instanceof TitaniumTankBlockEntity tankBE))
                return;
            world.removeBlockEntity(pos);
            ConnectivityHandler.splitMulti(tankBE);
        }
    }

    @Override
    public Class<TitaniumTankBlockEntity> getBlockEntityClass() {
        return TitaniumTankBlockEntity.class;
    }

    @Override
    public BlockEntityType<? extends TitaniumTankBlockEntity> getBlockEntityType() {
        return null;
    }


    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {

        return getBlockEntityOptional(worldIn, pos).map(TitaniumTankBlockEntity::getControllerBE)
                .map(be -> ComparatorUtil.fractionToRedstoneLevel(be.getFillState()))
                .orElse(0);
    }


}

