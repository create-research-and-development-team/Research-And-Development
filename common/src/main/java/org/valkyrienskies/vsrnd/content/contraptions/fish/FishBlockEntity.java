package org.valkyrienskies.vsrnd.content.contraptions.fish;

import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.valkyrienskies.vsrnd.VSCreatePackets;

import java.util.List;

import static com.simibubi.create.foundation.utility.worldWrappers.WrappedClientWorld.mc;

public class FishBlockEntity extends KineticBlockEntity {

    @Override
    protected void write(CompoundTag compound, boolean clientPacket) {


        compound.putFloat("VSrnd$headangle",HeadTarget);
        compound.putFloat("VSrnd$tailangle",TailTarget);
        compound.putFloat("VSrnd$flipperangle",FlipperTarget);
        compound.putFloat("VSrnd$jawangle",JawTarget);
        super.write(compound, clientPacket);

    }

    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        super.read(compound, clientPacket);
        if (compound.contains("VSrnd$headangle")) {
            HeadTarget = compound.getFloat("VSrnd$headangle");
            TailTarget = compound.getFloat("VSrnd$tailangle");
            FlipperTarget = compound.getFloat("VSrnd$flipperangle");
            JawTarget = compound.getFloat("VSrnd$jawangle");
        }
    }

    public LerpedFloat Head = LerpedFloat.angular();
    public LerpedFloat Tail = LerpedFloat.angular();
    public LerpedFloat Flipper = LerpedFloat.angular();
    public LerpedFloat Jaw = LerpedFloat.angular();
    protected float HeadTarget = 0;
    protected float TailTarget = 0;
    protected float FlipperTarget = 0;
    protected float JawTarget = 0;
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

    public void SetTailAngle(float angle) {
        TailTarget = angle;
    }
    public void SetFlipperAngle(float angle) {
        FlipperTarget = angle;
    }
    public void SetJawAngle(float angle) {
        JawTarget = angle;
    }

    public void PacketAngle() {
        VSCreatePackets.sendToNear(this.getLevel(),this.getBlockPos(),100,new FishPacket(this));
    }

    public float GetHeadAngle() {
        return HeadTarget;
    }
    public float GetTailAngle() {
        return TailTarget;
    }
    public float GetFlipperAngle() {
        return FlipperTarget;
    }
    public float GetJawAngle() {
        return JawTarget;
    }

    @Override
    public void tick() {
        super.tick();

        if (level.isClientSide()) {

            Head.chase(HeadTarget, 0.2f, LerpedFloat.Chaser.EXP);
            Tail.chase(TailTarget, 0.2f, LerpedFloat.Chaser.EXP);
            Flipper.chase(FlipperTarget, 0.2f, LerpedFloat.Chaser.EXP);
            Jaw.chase(JawTarget, 0.2f, LerpedFloat.Chaser.EXP);

            Head.tickChaser();
            Tail.tickChaser();
            Flipper.tickChaser();
            Jaw.tickChaser();
        }
    }
}
