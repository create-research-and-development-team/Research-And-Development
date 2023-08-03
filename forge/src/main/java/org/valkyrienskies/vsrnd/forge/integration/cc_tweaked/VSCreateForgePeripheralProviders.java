package org.valkyrienskies.vsrnd.forge.integration.cc_tweaked;

import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.valkyrienskies.vsrnd.content.contraptions.fish.FishBlockEntity;
import org.valkyrienskies.vsrnd.integration.cc.FishPeripheral;

public class VSCreateForgePeripheralProviders {
    public static void register() {
        ComputerCraftAPI.registerPeripheralProvider(new VSCreatePeripheralProvider());
    }

    public static class VSCreatePeripheralProvider implements IPeripheralProvider {

        @NotNull
        @Override
        public LazyOptional<IPeripheral> getPeripheral(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull Direction direction) {
            BlockEntity be = level.getBlockEntity(blockPos);
            if (be instanceof FishBlockEntity Fish) {
                return LazyOptional.of(() -> new FishPeripheral(Fish));
            }
            return LazyOptional.empty();
        }
    }
}
