package org.valkyrienskies.vscreate.integration.cc;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IPeripheral;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.valkyrienskies.vscreate.content.contraptions.propeller.PropellerBearingBlockEntity;

public class PropellerBearingPeripheral implements IPeripheral {
    private final Level level;
    private final BlockPos pos;
    private final PropellerBearingBlockEntity propellor;

    public PropellerBearingPeripheral(PropellerBearingBlockEntity be) {
        this.propellor = be;
        this.level = be.getLevel();
        this.pos = be.getBlockPos();
    }

    @NotNull
    @Override
    public String getType() {
        return "propellor";
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return level != null && level.getBlockEntity(pos) instanceof PropellerBearingBlockEntity;
    }

    @LuaFunction
    public final boolean isValid() {
        return this.propellor.isValid();
    }

    @LuaFunction
    public final boolean isInverted() {
        return this.propellor.isInverted();
    }

    @LuaFunction
    public final float getAngularSpeed() {
        return this.propellor.getAngularSpeed();
    }

    @LuaFunction
    public final int getSailCount() {
        return this.propellor.getSailCount();
    }
}
