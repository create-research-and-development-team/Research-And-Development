package org.valkyrienskies.vscreate.content.contraptions.sculk.sculkthruster;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Random;

public class sculkthruster_block extends Block {

    public int cooldown = 0;

    public sculkthruster_block(Properties properties) {

        super(properties);

    }


    // stepOn is dogshit, but it's gonna use sculk shit eventually anyway
    // so it doesn't matter
    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {

        if (cooldown <= 0) {
            cooldown = 10;
            Vec3 startPos = entity.position();
            for (int i = 0; i < 180; i += 2) {
                level.addParticle(ParticleTypes.END_ROD, startPos.x, startPos.y - 0.1, startPos.z, Math.cos(i) * 0.2 * Math.random(), 0.1, Math.sin(i) * 0.2 * Math.random());
            }

            entity.push(0, 2, 0);

        }
        super.stepOn(level, pos, state, entity);
    }


    // TODO: Make Cooldown less shit, I refuse to believe this is the best way to
    // make a cooldown. Ticking shit bad for lag and all that
    @Override
    public void animateTick(BlockState state, Level level, BlockPos pos, Random random) {

        if (cooldown > 0) {
            cooldown -= 1;
        }

        super.animateTick(state, level, pos, random);
    }
}
