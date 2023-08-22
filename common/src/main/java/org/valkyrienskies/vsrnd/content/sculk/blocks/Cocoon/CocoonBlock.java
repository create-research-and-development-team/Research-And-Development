package org.valkyrienskies.vsrnd.content.sculk.blocks.Cocoon;

	import com.google.common.base.Predicates;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.AbstractSimpleShaftBlock;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import com.simibubi.create.content.kinetics.steamEngine.PoweredShaftBlock;
import com.simibubi.create.foundation.placement.PlacementOffset;
import com.simibubi.create.foundation.placement.PoleHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.valkyrienskies.vsrnd.VSCreateBlockEntities;
import org.valkyrienskies.vsrnd.VSCreateBlocks;

import java.util.function.Predicate;

public class CocoonBlock extends AbstractSimpleShaftBlock {
	public CocoonBlock(Properties properties) {
		super(properties);
	}

	public static boolean isShaft(BlockState state) {
		return VSCreateBlocks.COCOON.has(state);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
								 BlockHitResult ray) {
		return InteractionResult.PASS;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		return AllShapes.SIX_VOXEL_POLE.get(state.getValue(AXIS));
	}

	@Override
	public BlockEntityType<? extends KineticBlockEntity> getBlockEntityType() {
		return VSCreateBlockEntities.COCOON_BLOCK_ENTITY.get();
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState stateForPlacement = super.getStateForPlacement(context);
		return pickCorrectShaftType(stateForPlacement, context.getLevel(), context.getClickedPos());
	}

	public static BlockState pickCorrectShaftType(BlockState stateForPlacement, Level level, BlockPos pos) {
		if (PoweredShaftBlock.stillValid(stateForPlacement, level, pos)) {
			return PoweredShaftBlock.getEquivalent(stateForPlacement);
		}
		return stateForPlacement;
	}

	@Override
	public float getParticleTargetRadius() {
		return .35f;
	}

	@Override
	public float getParticleInitialRadius() {
		return .125f;
	}

	@MethodsReturnNonnullByDefault
	private static class PlacementHelper extends PoleHelper<Direction.Axis> {
		// used for extending a shaft in its axis, like the piston poles. works with
		// shafts and cogs

		private PlacementHelper() {
			super(state -> state.getBlock() instanceof AbstractSimpleShaftBlock
						   || state.getBlock() instanceof PoweredShaftBlock, state -> state.getValue(AXIS), AXIS);
		}

		@Override
		public Predicate<ItemStack> getItemPredicate() {
			return i -> i.getItem() instanceof BlockItem
						&& ((BlockItem) i.getItem()).getBlock() instanceof AbstractSimpleShaftBlock;
		}

		@Override
		public Predicate<BlockState> getStatePredicate() {
			return Predicates.or(AllBlocks.SHAFT::has, AllBlocks.POWERED_SHAFT::has, VSCreateBlocks.COCOON::has);
		}

		@Override
		public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos,
										 BlockHitResult ray) {
			PlacementOffset offset = super.getOffset(player, world, state, pos, ray);
			if (offset.isSuccessful()) {
				offset.withTransform(offset.getTransform()
										   .andThen(s -> ShaftBlock.pickCorrectShaftType(s,
																						 world,
																						 offset.getBlockPos())));
			}
			return offset;
		}

	}

}
