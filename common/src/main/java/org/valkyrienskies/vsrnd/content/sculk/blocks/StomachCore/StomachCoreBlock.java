package org.valkyrienskies.vsrnd.content.sculk.blocks.StomachCore;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.valkyrienskies.vsrnd.VSCreateBlockEntities;


public class StomachCoreBlock extends BaseEntityBlock {
    public StomachCoreBlock(Properties properties) {
        super(properties);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new StomachCoreBlockEntity(VSCreateBlockEntities.STOMACH_CORE_BLOCK_ENTITY.get(),pos,state);
    }
}
