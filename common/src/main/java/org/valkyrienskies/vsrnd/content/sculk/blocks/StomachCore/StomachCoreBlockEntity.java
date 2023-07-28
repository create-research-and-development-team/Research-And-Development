package org.valkyrienskies.vsrnd.content.sculk.blocks.StomachCore;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.valkyrienskies.vsrnd.VSCreateBlockEntities;

public class StomachCoreBlockEntity extends BlockEntity {
    public StomachCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }
    @Override
    public BlockEntityType<?> getType() {
        return VSCreateBlockEntities.STOMACH_CORE_BLOCK_ENTITY.get();
    }
}
