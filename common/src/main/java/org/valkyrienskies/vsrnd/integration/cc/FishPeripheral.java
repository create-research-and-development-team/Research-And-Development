package org.valkyrienskies.vsrnd.integration.cc;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.peripheral.speaker.SpeakerPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.valkyrienskies.vsrnd.content.contraptions.fish.FishBlockEntity;

public class FishPeripheral extends SpeakerPeripheral {

    private final Level level;
    private final BlockPos pos;
    private final FishBlockEntity FBE;
    public FishPeripheral(FishBlockEntity be) {

        this.level = be.getLevel();
        this.pos = be.getBlockPos();
        this.FBE = be;
    }

    @NotNull
    @Override
    public String getType() {
        return "fish";
    }

    @Override
    public Level getWorld() {
        return level;
    }

    @Override
    public Vec3 getPosition() {
        return new Vec3(pos.getX(),pos.getY(),pos.getZ());
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return level != null && level.getBlockEntity(pos) instanceof FishBlockEntity;
    }

    @LuaFunction
    public final void MoveHead(double angle) {
        FBE.SetHeadAngle( (float) angle);
        FBE.PacketAngle();
    }

    @LuaFunction
    public final float GetHead() {
        return FBE.GetHeadAngle();
    }

    @LuaFunction
    public final void MoveTail(double angle) {
        FBE.SetTailAngle((float) angle);
        FBE.PacketAngle();
    }

    @LuaFunction
    public final float GetTail() {
        return FBE.GetTailAngle();
    }

    @LuaFunction
    public final void MoveFlipper(double angle) {
        FBE.SetFlipperAngle((float) angle);
        FBE.PacketAngle();
    }

    @LuaFunction
    public final float GetFlipper() {
        return FBE.GetFlipperAngle();
    }

    @LuaFunction
    public final void MoveJaw(double angle) {
        FBE.SetJawAngle((float) angle);
        FBE.PacketAngle();
    }

    @LuaFunction
    public final float GetJaw() {
        return FBE.GetJawAngle();
    }
}
