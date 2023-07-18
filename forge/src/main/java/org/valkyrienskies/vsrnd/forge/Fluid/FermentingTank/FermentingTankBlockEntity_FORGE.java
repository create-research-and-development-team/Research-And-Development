package org.valkyrienskies.vsrnd.forge.Fluid.FermentingTank;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.valkyrienskies.vsrnd.content.Fluids.FermentingTank.FermentingTankBlockEntity;

public class FermentingTankBlockEntity_FORGE extends FermentingTankBlockEntity {
    public FermentingTankBlockEntity_FORGE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }


    @Override
    public FermentingTankBlockEntity_FORGE getControllerBE() {
        if (isController())
            return this;
        BlockEntity blockEntity = level.getBlockEntity(controller);
        if (blockEntity instanceof FermentingTankBlockEntity)
            return (FermentingTankBlockEntity_FORGE) blockEntity;
        return null;
    }
}
