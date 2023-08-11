package org.valkyrienskies.vsrnd.content.World.Rutile;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.valkyrienskies.vsrnd.VSCreateBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class RutileFeature extends Feature<NoneFeatureConfiguration> {
    List<Vec3i> offsets = new ArrayList<Vec3i>();
    public RutileFeature(Codec<NoneFeatureConfiguration> codec) {
        super(codec);
        offsets.add(new Vec3i(1,0,0));
        offsets.add(new Vec3i(-1,0,0));
        offsets.add(new Vec3i(0,1,0));
        offsets.add(new Vec3i(0,-1,0));
        offsets.add(new Vec3i(0,0,1));
        offsets.add(new Vec3i(0,0,-1));
    }



    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

        WorldGenLevel worldGenLevel = context.level();
        BlockPos blockPos = context.origin();

        System.out.println("PLACING RUTILE AT");
        System.out.println(blockPos);
        Random random = context.random();
        Stream<TagKey<Block>> StreamTags = worldGenLevel.getBlockState(blockPos).getTags();
        List<TagKey<Block>> Blocktags = StreamTags.toList();

        if (worldGenLevel.getBlockState(blockPos).isAir())
            return false;

        for (Direction direction : Direction.values()) {
            if (worldGenLevel.getBlockState(blockPos.relative(direction)).isAir()) {
                worldGenLevel.setBlock(blockPos.relative(direction), VSCreateBlocks.RUTILE_CLUSTER.getDefaultState(),2);
                return true;
            }
        }


        return false;

    }
}
