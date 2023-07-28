package org.valkyrienskies.vsrnd.content.sculk.blocks.SculkCore;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.apache.logging.log4j.core.jmx.Server;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.api.ships.Ship;
import org.valkyrienskies.mod.common.VSGameUtilsKt;
import org.valkyrienskies.vsrnd.VSCreateBlockEntities;
import org.valkyrienskies.vsrnd.util.ship.ShipAssembler;

public class SculkCoreBlockEntity extends BlockEntity {


    public SculkCoreBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public BlockEntityType<?> getType() {
        return VSCreateBlockEntities.SCULK_CORE_BLOCK_ENTITY.get();
    }



    public boolean assemble() {

        Ship SelfShip = VSGameUtilsKt.getShipObjectManagingPos(this.getLevel(),this.getBlockPos());
        if (SelfShip == null) {
            ShipAssembler shipAssembler = new ShipAssembler(this.getBlockPos(), this.getLevel());
            shipAssembler.assemble(this.getBlockPos());
            return true;
        }


        return false;
    }


}
