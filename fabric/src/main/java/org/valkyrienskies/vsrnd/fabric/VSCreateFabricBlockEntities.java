package org.valkyrienskies.vsrnd.fabric;


import com.tterrag.registrate.util.entry.BlockEntityEntry;
import org.valkyrienskies.vsrnd.VSCreateMod;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankRenderer;
import org.valkyrienskies.vsrnd.fabric.Fluid.TitaniumTankBlockEntity_FABRIC;

import static org.valkyrienskies.vsrnd.RNDRegistrate.REGISTRATE;


public class VSCreateFabricBlockEntities {

	public static final BlockEntityEntry<TitaniumTankBlockEntity_FABRIC> TITANIUM_TANK = REGISTRATE
			.blockEntity("titanium_tank", TitaniumTankBlockEntity_FABRIC::new)
			.validBlocks(VSCreateFabricBlocks.TITANIUM_TANK)
			.renderer(() -> TitaniumTankRenderer::new)
			.register();

	// Kinetics

	static {
		REGISTRATE.creativeModeTab(() -> VSCreateMod.BASE_CREATIVE_TAB);
	}

	public static void register() {
	}
}
