package org.valkyrienskies.vsrnd.platform.fabric;

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidStorage;
import org.valkyrienskies.vsrnd.platform.SmartFluidTankBlockEntity;

public class FallbackFabricTransfer {

	public static void init() {
		FluidStorage.SIDED.registerFallback((world, pos, state, be, face) -> {
			if (be instanceof SmartFluidTankBlockEntity t) {
				return ((FabricVSCFluidTankBehaviour) t.getFluidTankBehaviour()).getCapability();
			}
			return null;
		});
	}

}
