package org.valkyrienskies.vsrnd.foundation.ship;


import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.valkyrienskies.core.api.ships.ServerShip;
import org.valkyrienskies.core.impl.datastructures.DenseBlockPosSet;
import org.valkyrienskies.mod.common.assembly.ShipAssemblyKt;

import java.util.HashSet;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class ShipAssembler {

    BlockPos centerPos;
    DenseBlockPosSet blocks;
    ServerLevel level;

    DenseBlockPosSet rangedBlocks = new DenseBlockPosSet();

    HashSet<BlockEntity> devices;
    private static final long MAX_ASSEMBLED_BLOCKS = 114514;
    private Queue<BlockPos> blockQueue = new LinkedBlockingQueue<>();

    public ShipAssembler(BlockPos centerPos, Level level) {
        this.centerPos = centerPos;
        this.level =  (ServerLevel) level;
        this.blocks = new DenseBlockPosSet();
        this.blockQueue.add(centerPos);
    }

    private boolean isValid(BlockPos blockPos) {
        BlockState blockState = this.level.getBlockState(blockPos);
        return !blockState.isAir() && !(blockState.getBlock() instanceof LiquidBlock) && !(blockState.getBlock() instanceof SnowyDirtBlock);
    }





    public ServerShip assemble(BlockPos Helm) {
        this.blocks.add(Helm.getX(),Helm.getY(),Helm.getZ());
        for (int x = -3; x < 3; x++) {
            for (int z = -3; z < 3; z++) {
                this.blocks.add(Helm.getX()+x,Helm.getY(),Helm.getZ()+z);
            }
        }
        return ShipAssemblyKt.createNewShipWithBlocks(this.centerPos, this.blocks, this.level);
    }


    public HashSet<BlockEntity> getDevices() {
        return this.devices;
    }
}