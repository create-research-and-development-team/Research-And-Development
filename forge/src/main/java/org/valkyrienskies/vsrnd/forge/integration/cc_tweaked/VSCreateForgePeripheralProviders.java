package org.valkyrienskies.vsrnd.forge.integration.cc_tweaked;

import dan200.computercraft.api.ComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class VSCreateForgePeripheralProviders {
	public static void register() {
		ComputerCraftAPI.registerPeripheralProvider(new ClockworkPeripheralProvider());
	}

	public static class ClockworkPeripheralProvider implements IPeripheralProvider {

		@NotNull
		@Override
		public LazyOptional<IPeripheral> getPeripheral(@NotNull Level level, @NotNull BlockPos blockPos, @NotNull Direction direction) {
			return null;
		}
	}
}
