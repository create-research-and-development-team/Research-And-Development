package org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank;

import com.simibubi.create.content.fluids.tank.FluidTankBlock;
import net.minecraft.world.level.block.state.BlockState;

public class TitaniumTankBlock extends FluidTankBlock {

    protected TitaniumTankBlock(Properties p_i48440_1_, boolean creative) {
        super(p_i48440_1_, creative);
    }

    public static TitaniumTankBlock regular(Properties p_i48440_1_) {
        return new TitaniumTankBlock(p_i48440_1_, false);
    }

    public static boolean isTank(BlockState state) {
        return state.getBlock() instanceof TitaniumTankBlock;
    }
}
