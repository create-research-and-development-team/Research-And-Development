package org.valkyrienskies.vsrnd.content.World.Rutile;

import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import org.valkyrienskies.vsrnd.VSCreateBlocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RutileFeature extends Feature<NoneFeatureConfiguration> {
	List<Vec3i> offsets = new ArrayList<Vec3i>();

	public RutileFeature(Codec<NoneFeatureConfiguration> codec) {
		super(codec);
	}

	public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {

		WorldGenLevel worldGenLevel = context.level();
		BlockPos blockPos = context.origin();

		System.out.println("PLACING RUTILE AT");
		System.out.println(blockPos);
		Random random = context.random();

		if (!worldGenLevel.getBlockState(blockPos).is(BlockTags.BASE_STONE_OVERWORLD)) {
			return false;
		}

		for (Direction direction : Direction.values()) {
			if (worldGenLevel.getBlockState(blockPos.relative(direction)).isAir()) {
				BlockState block = VSCreateBlocks.RUTILE_CLUSTER.getDefaultState()
																.setValue(BlockStateProperties.FACING, direction);

				worldGenLevel.setBlock(blockPos.relative(direction), block, 2);
				return true;
			}
		}
		return false;
	}
}
