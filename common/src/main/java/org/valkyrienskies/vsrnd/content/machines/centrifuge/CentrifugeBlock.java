package org.valkyrienskies.vsrnd.content.machines.centrifuge;

import com.simibubi.create.content.contraptions.bearing.BearingBlock;
import com.simibubi.create.foundation.block.IBE;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class CentrifugeBlock extends BearingBlock implements IBE<CentrifugeBlockEntity> {
	public CentrifugeBlock(Properties properties) {
		super(properties);
	}

	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn,
								 BlockHitResult hit) {
		if (!player.mayBuild()) {
			return InteractionResult.FAIL;
		}
		if (player.isShiftKeyDown()) {
			return InteractionResult.FAIL;
		}
		if (player.getItemInHand(handIn)
				  .isEmpty()) {
			if (worldIn.isClientSide) {
				return InteractionResult.SUCCESS;
			}
			withBlockEntityDo(worldIn, pos, be -> {
				if (be.isRunning()) {
					be.disassemble();
					return;
				}
				be.assembleNextTick = true;
			});
			return InteractionResult.SUCCESS;
		}
		return InteractionResult.PASS;
	}

	@Override
	public Class<CentrifugeBlockEntity> getBlockEntityClass() {
		return null;
	}

	@Override
	public BlockEntityType<CentrifugeBlockEntity> getBlockEntityType() {
		return null;
	}
	//this needs to be eased in and out using the util potato made
}
