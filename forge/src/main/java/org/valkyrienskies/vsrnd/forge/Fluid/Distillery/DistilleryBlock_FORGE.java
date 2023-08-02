package org.valkyrienskies.vsrnd.forge.Fluid.Distillery;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.valkyrienskies.vsrnd.VSCreateBlockEntities;
import org.valkyrienskies.vsrnd.content.Fluids.Distillery.DistilleryBlock;
import org.valkyrienskies.vsrnd.content.Fluids.Distillery.DistilleryBlockEntity;
import org.valkyrienskies.vsrnd.content.Fluids.FermentingTank.FermentingTankBlockEntity;
import org.valkyrienskies.vsrnd.forge.VSCreateForgeBlockEntities;

public class DistilleryBlock_FORGE extends DistilleryBlock {
    public DistilleryBlock_FORGE(Properties p_i48440_1_) {
        super(p_i48440_1_);
    }

    @Override
    public BlockEntityType<? extends DistilleryBlockEntity_FORGE> getBlockEntityType() {
        return (BlockEntityType) VSCreateForgeBlockEntities.DISTILLERY.get();
    }


}
