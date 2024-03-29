package org.valkyrienskies.vsrnd.fabric.Fluid;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import io.github.fabricators_of_create.porting_lib.util.FluidStack;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankItem;
import org.valkyrienskies.vsrnd.fabric.VSCreateFabricBlockEntities;

public class TitaniumTankItem_FABRIC extends TitaniumTankItem {
	public TitaniumTankItem_FABRIC(Block p_i48527_1_, Properties p_i48527_2_) {
		super(p_i48527_1_, p_i48527_2_);
	}

	@Override
	public InteractionResult place(BlockPlaceContext ctx) {
		InteractionResult initialResult = super.place(ctx);
		if (!initialResult.consumesAction()) {
			return initialResult;
		}
		tryMultiPlace(ctx);
		return initialResult;
	}

	private void tryMultiPlace(BlockPlaceContext ctx) {
		Player player = ctx.getPlayer();
		if (player == null) {
			return;
		}
		if (player.isShiftKeyDown()) {
			return;
		}
		Direction face = ctx.getClickedFace();
		if (!face.getAxis()
				 .isVertical()) {
			return;
		}
		ItemStack stack = ctx.getItemInHand();
		Level world = ctx.getLevel();
		BlockPos pos = ctx.getClickedPos();
		BlockPos placedOnPos = pos.relative(face.getOpposite());
		BlockState placedOnState = world.getBlockState(placedOnPos);

		if (!TitaniumTankBlock_FABRIC.isTank(placedOnState)) {
			return;
		}

		TitaniumTankBlockEntity_FABRIC tankAt = ConnectivityHandler.partAt(VSCreateFabricBlockEntities.TITANIUM_TANK.get(),
																		   world,
																		   placedOnPos
																		  );
		if (tankAt == null) {
			return;
		}
		TitaniumTankBlockEntity_FABRIC controllerBE = tankAt.getControllerBE();
		if (controllerBE == null) {
			return;
		}

		int width = controllerBE.getWidth();
		if (width == 1) {
			return;
		}

		int tanksToPlace = 0;
		BlockPos startPos = face == Direction.DOWN ? controllerBE.getBlockPos()
																 .below()
												   : controllerBE.getBlockPos()
																 .above(controllerBE.getHeight());

		if (startPos.getY() != pos.getY()) {
			return;
		}

		for (int xOffset = 0; xOffset < width; xOffset++) {
			for (int zOffset = 0; zOffset < width; zOffset++) {
				BlockPos offsetPos = startPos.offset(xOffset, 0, zOffset);
				BlockState blockState = world.getBlockState(offsetPos);
				if (TitaniumTankBlock_FABRIC.isTank(blockState)) {
					continue;
				}
				if (!blockState.getMaterial()
							   .isReplaceable()) {
					return;
				}
				tanksToPlace++;
			}
		}
	}

	@Override
	protected boolean updateCustomBlockEntityTag(BlockPos p_195943_1_, Level p_195943_2_, Player p_195943_3_,
												 ItemStack p_195943_4_, BlockState p_195943_5_) {
		MinecraftServer minecraftserver = p_195943_2_.getServer();
		if (minecraftserver == null) {
			return false;
		}
		CompoundTag nbt = p_195943_4_.getTagElement("BlockEntityTag");
		if (nbt != null) {
			nbt.remove("Luminosity");
			nbt.remove("Size");
			nbt.remove("Height");
			nbt.remove("Controller");
			nbt.remove("LastKnownPos");
			if (nbt.contains("TankContent")) {
				FluidStack fluid = FluidStack.loadFluidStackFromNBT(nbt.getCompound("TankContent"));
				if (!fluid.isEmpty()) {
					fluid.setAmount(Math.min(TitaniumTankBlockEntity_FABRIC.getCapacityMultiplier(),
											 fluid.getAmount()));
					nbt.put("TankContent", fluid.writeToNBT(new CompoundTag()));
				}
			}
		}
		return super.updateCustomBlockEntityTag(p_195943_1_, p_195943_2_, p_195943_3_, p_195943_4_, p_195943_5_);
	}
}
