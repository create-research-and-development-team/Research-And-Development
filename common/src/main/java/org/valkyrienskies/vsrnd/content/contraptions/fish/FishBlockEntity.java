package org.valkyrienskies.vsrnd.content.contraptions.fish;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.valkyrienskies.vsrnd.VSCreatePackets;

import java.util.List;

public class FishBlockEntity extends KineticBlockEntity {

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {


        compound.putFloat("VSrnd$headangle",HeadTarget);
        super.write(compound, clientPacket);

    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        if (compound.contains("VSrnd$headangle")) {
            HeadTarget = compound.getFloat("VSrnd$headangle");
        }
    }

    public LerpedFloat Head = LerpedFloat.angular();

    protected float HeadTarget = 0;
    public FishBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    @Override
    public void addBehaviours(List<BlockEntityBehaviour> behaviours) {
        super.addBehaviours(behaviours);
    }

    public void SetHeadAngle(float angle) {
        HeadTarget = angle;
    }

    public void PacketHeadAngle(double angle) {
        SetHeadAngle((float)angle);
        VSCreatePackets.sendToNear(this.getLevel(),this.getBlockPos(),100,new FishPacket(this));
    }

    public float GetHeadAngle() {
        return HeadTarget;
    }


    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide()) {
            Head.chase(HeadTarget, 0.2f, LerpedFloat.Chaser.EXP);
            Head.tickChaser();
        }
    }
}
