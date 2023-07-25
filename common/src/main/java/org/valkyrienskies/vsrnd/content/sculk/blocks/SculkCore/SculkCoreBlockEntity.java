package org.valkyrienskies.vsrnd.content.sculk.blocks.SculkCore;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.vsrnd.VSCreateBlockEntities;
import org.valkyrienskies.vsrnd.util.ship.ShipAssembler;

public class SculkCoreBlockEntity extends BlockEntity {
    private ServerShip ship = null;
    public SculkCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return VSCreateBlockEntities.SCULKCORE_BLOCK_ENTITY.get();
    }

    public void assemble() {
        if (ship==null) {
            ShipAssembler shipAssembler = new ShipAssembler(this.getBlockPos(), this.getLevel());
            this.ship = shipAssembler.assemble(this.getBlockPos());

        }
    }
}
