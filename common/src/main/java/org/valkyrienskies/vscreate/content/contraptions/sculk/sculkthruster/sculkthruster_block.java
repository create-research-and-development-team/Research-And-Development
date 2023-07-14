package org.valkyrienskies.vscreate.content.contraptions.sculk.sculkthruster;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class sculkthruster_block extends Block {
    public sculkthruster_block(Properties properties) {
        super(properties);
    }

    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        entity.push(0,10,0);
        super.stepOn(level, pos, state, entity);
    }
}
