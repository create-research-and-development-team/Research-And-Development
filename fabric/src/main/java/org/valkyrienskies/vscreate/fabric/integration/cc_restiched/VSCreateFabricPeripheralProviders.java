package org.valkyrienskies.vscreate.fabric.integration.cc_restiched;

import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jetbrains.annotations.NotNull;
import org.valkyrienskies.vscreate.content.contraptions.propeller.PropellerBearingBlockEntity;
import org.valkyrienskies.vscreate.integration.cc.PropellerBearingPeripheral;

public class VSCreateFabricPeripheralProviders {
    public static void register() {
        ComputerCraftAPI.registerPeripheralProvider(new ClockworkPeripheralProvider());
    }

    public static class ClockworkPeripheralProvider implements IPeripheralProvider {
        @NotNull
        @Override
        public IPeripheral getPeripheral(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull Direction direction) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be instanceof PropellerBearingBlockEntity propellor)
                return new PropellerBearingPeripheral(propellor);
            return null;
        }
    }
}
