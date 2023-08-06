package org.valkyrienskies.vsrnd.util.fluid;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.jetbrains.annotations.NotNull;

public class VirtualFluid extends RNDFlowingFluid {
    public VirtualFluid(Properties properties) {
        super(properties);
    }

    @Override
    public @NotNull Fluid getSource() {
        return super.getSource();
    }

    @Override
    public @NotNull Fluid getFlowing() {
        return this;
    }

    @Override
    public Item getBucket() {
        return Items.AIR;
    }

    @Override
    protected BlockState createLegacyBlock(FluidState state) {
        return Blocks.AIR.defaultBlockState();
    }

    @Override
    public boolean isSource(@NotNull FluidState fluidState) {
        return false;
    }

    @Override
    public int getAmount(@NotNull FluidState fluidState) {
        return 0;
    }
}
