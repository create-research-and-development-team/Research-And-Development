package org.valkyrienskies.vsrnd.content.Fluids.FermentingTank;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.ApiStatus;

public class FermentingTankItem extends BlockItem {
	@ApiStatus.Internal
	public static boolean IS_PLACING_NBT = false;

	public FermentingTankItem(Block p_i48527_1_, Item.Properties p_i48527_2_) {
		super(p_i48527_1_, p_i48527_2_);
	}

	public static boolean checkPlacingNbt(BlockPlaceContext ctx) {
		ItemStack item = ctx.getItemInHand();
		return BlockItem.getBlockEntityData(item) != null;
	}
}
