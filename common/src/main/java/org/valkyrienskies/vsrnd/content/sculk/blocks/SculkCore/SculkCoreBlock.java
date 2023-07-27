package org.valkyrienskies.vsrnd.content.sculk.blocks.SculkCore;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.jetbrains.annotations.Nullable;
import org.valkyrienskies.vsrnd.VSCreateBlockEntities;
import org.valkyrienskies.vsrnd.util.ship.ShipAssembler;

public class SculkCoreBlock extends BaseEntityBlock {


    public SculkCoreBlock(Properties properties) {
        super(properties);
    }

    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        SculkCoreBlockEntity entity = (SculkCoreBlockEntity) level.getBlockEntity(pos);
        if (entity!=null && !level.isClientSide() && player.getUseItem() == ItemStack.EMPTY) {


            entity.assemble();
            return InteractionResult.CONSUME;
        }
        return InteractionResult.PASS;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new SculkCoreBlockEntity(VSCreateBlockEntities.SCULKCORE_BLOCK_ENTITY.get(),pos,state);
    }
}
