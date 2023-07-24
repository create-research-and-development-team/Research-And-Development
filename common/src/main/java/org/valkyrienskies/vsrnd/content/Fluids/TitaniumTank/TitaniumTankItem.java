package org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank;

import com.simibubi.create.api.connectivity.ConnectivityHandler;
import com.simibubi.create.content.fluids.tank.FluidTankItem;


import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.ApiStatus;
import org.valkyrienskies.vsrnd.VSCreateBlockEntities;

public class TitaniumTankItem extends BlockItem {
    @ApiStatus.Internal
    public static boolean IS_PLACING_NBT = false;
    public TitaniumTankItem(Block p_i48527_1_, Properties p_i48527_2_) {
        super(p_i48527_1_, p_i48527_2_);
    }

    public static boolean checkPlacingNbt(BlockPlaceContext ctx) {
        ItemStack item = ctx.getItemInHand();
        return BlockItem.getBlockEntityData(item) != null;
    }

}
