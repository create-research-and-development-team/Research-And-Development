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
import org.valkyrienskies.vsrnd.content.Fluids.BaseTankBlock;

import java.util.function.Consumer;


public class TitaniumTankBlock extends BaseTankBlock implements IWrenchable, IBE<TitaniumTankBlockEntity> {


    protected TitaniumTankBlock(Properties p_i48440_1_, Properties creative) {
        super(p_i48440_1_);
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


    @Override
    public InteractionResult onWrenched(BlockState state, UseOnContext context) {
        withBlockEntityDo(context.getLevel(), context.getClickedPos(), TitaniumTankBlockEntity::toggleWindows);
        return InteractionResult.SUCCESS;
    }


    @Override
    public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && (state.getBlock() != newState.getBlock() || !newState.hasBlockEntity())) {
            BlockEntity be = world.getBlockEntity(pos);
            if (!(be instanceof TitaniumTankBlockEntity))
                return;
            TitaniumTankBlockEntity tankBE = (TitaniumTankBlockEntity) be;
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

