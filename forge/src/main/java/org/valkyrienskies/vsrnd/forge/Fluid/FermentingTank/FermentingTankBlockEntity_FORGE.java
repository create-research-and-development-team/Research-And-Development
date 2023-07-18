package org.valkyrienskies.vsrnd.forge.Fluid.FermentingTank;

import com.simibubi.create.AllFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.valkyrienskies.vsrnd.content.Fluids.FermentingTank.FermentingTankBlockEntity;

public class FermentingTankBlockEntity_FORGE extends FermentingTankBlockEntity {
    private int FermentedTicks = 0;
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

    @Override
    public void tick() {
        super.tick();
        if (isController()) {
                if (super.tankInventory.getFluid().getTranslationKey().matches("(.*)water")) {
                FermentedTicks += 1;
                if (FermentedTicks >= 200) {
                    super.tankInventory.setFluid(new FluidStack(AllFluids.HONEY.get().getSource(), tankInventory.getFluid().getAmount()));
                }
                } else {
                    FermentedTicks = 0;
                }
            //System.out.println(super.tankInventory.getFluid().getTranslationKey());


        }
    }
}
