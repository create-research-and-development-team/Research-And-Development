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
    private ServerShip selfShip = null;
    public SculkCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return VSCreateBlockEntities.SCULKCORE_BLOCK_ENTITY.get();
    }

    public void assemble() {
        System.out.println(this.selfShip);
        System.out.println(this.selfShip==null);
        if (this.selfShip==null) {
            ShipAssembler shipAssembler = new ShipAssembler(this.getBlockPos(), this.getLevel());
            this.selfShip = shipAssembler.assemble(this.getBlockPos());
            System.out.println(this.selfShip);
        }
        System.out.println("/////////////");
    }
}
