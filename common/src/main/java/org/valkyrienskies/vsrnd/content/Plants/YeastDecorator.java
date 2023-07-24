package org.valkyrienskies.vsrnd.content.Plants;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CocoaBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.treedecorators.CocoaDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.valkyrienskies.vsrnd.VSCreateBlocks;
import org.valkyrienskies.vsrnd.VSCreateMod;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;

public class YeastDecorator extends TreeDecorator {
    public static final Codec<YeastDecorator> CODEC = Codec.floatRange(0.0F, 1.0F).fieldOf("probability").xmap(YeastDecorator::new, (decorator) -> {
        return decorator.probability;
    }).codec();
    private final float probability;

    public YeastDecorator(float probability) {
        this.probability = probability;
    }

    protected TreeDecoratorType<?> type() {
        return VSCreateMod.YEAST_DECORATOR;
    }

    public void place(LevelSimulatedReader level, BiConsumer<BlockPos, BlockState> blockSetter, Random random, List<BlockPos> logPositions, List<BlockPos> leafPositions) {
        if (!(random.nextFloat() >= this.probability)) {
            int i = ((BlockPos)logPositions.get(0)).getY();
            logPositions.stream().filter((pos) -> {
                return pos.getY() - i <= 2;
            }).forEach((pos) -> {
                Iterator var4 = Direction.Plane.HORIZONTAL.iterator();

                while(var4.hasNext()) {
                    Direction direction = (Direction)var4.next();
                    if (random.nextFloat() <= 0.25F) {
                        Direction direction2 = direction.getOpposite();
                        BlockPos blockPos = pos.offset(direction2.getStepX(), 0, direction2.getStepZ());
                        if (Feature.isAir(level, blockPos)) {
                            blockSetter.accept(blockPos, (BlockState)((BlockState) VSCreateBlocks.YEAST.getDefaultState().setValue(Yeast.AGE, random.nextInt(3))).setValue(Yeast.FACING, direction));
                        }
                    }
                }

            });
        }
    }
}