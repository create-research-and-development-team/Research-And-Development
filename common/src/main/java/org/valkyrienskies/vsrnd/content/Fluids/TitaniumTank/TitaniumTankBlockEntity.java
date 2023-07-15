package org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank;

import com.simibubi.create.content.fluids.tank.FluidTankBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public class TitaniumTankBlockEntity extends FluidTankBlockEntity {


    private static final int MAX_SIZE = 3;

    protected boolean forceFluidLevelUpdate;

    protected BlockPos controller;
    protected BlockPos lastKnownPos;
    protected boolean updateConnectivity;
    protected boolean window;
    protected int luminosity;
    protected int width;
    protected int height;

    public TitaniumTankBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void updateBoilerState() {
        return;
    }


    @SuppressWarnings("unchecked")
    @Override
    public TitaniumTankBlockEntity getControllerBE() {
        if (isController())
            return this;
        BlockEntity blockEntity = level.getBlockEntity(controller);
        if (blockEntity instanceof TitaniumTankBlockEntity)
            return (TitaniumTankBlockEntity) blockEntity;
        return null;
    }
}
