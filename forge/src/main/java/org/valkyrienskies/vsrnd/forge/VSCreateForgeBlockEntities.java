package org.valkyrienskies.vsrnd.forge;


//import com.tterrag.registrate.util.entry.BlockEntityEntry;
//import org.valkyrienskies.clockwork.ClockWorkBlocks;
//import org.valkyrienskies.clockwork.content.contraptions.combustion_engine.CombustionEngineBlockEntity;
//import org.valkyrienskies.clockwork.content.contraptions.combustion_engine.CombustionEngineRenderer;
//import org.valkyrienskies.clockwork.forge.content.contraptions.combustion_engine.ForgeCombustionEngineBlockEntity;

import com.simibubi.create.content.fluids.tank.FluidTankRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import org.valkyrienskies.vsrnd.content.Fluids.FermentingTank.FermentingTankBlockEntity;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankRenderer;
import org.valkyrienskies.vsrnd.forge.Fluid.FermentingTank.FermentingTankBlockEntity_FORGE;
import org.valkyrienskies.vsrnd.forge.Fluid.FermentingTank.FermentingTankBlock_FORGE;
import org.valkyrienskies.vsrnd.forge.Fluid.TitaniumTank.TitaniumTankBlockEntity_FORGE;

import static org.valkyrienskies.vsrnd.RNDRegistrate.REGISTRATE;

public class VSCreateForgeBlockEntities {



    // Kinetics


    public static final BlockEntityEntry<TitaniumTankBlockEntity_FORGE> TITANIUM_TANK = REGISTRATE
            .blockEntity("titanium_tank", TitaniumTankBlockEntity_FORGE::new)
            .validBlocks(VSCreateForgeBlocks.TITANIUM_TANK_FORGE)
            .renderer(() -> TitaniumTankRenderer::new)
            .register();

    public static final BlockEntityEntry<FermentingTankBlockEntity_FORGE> FERMENTING_TANK = REGISTRATE
            .blockEntity("fermenting_tank", FermentingTankBlockEntity_FORGE::new)
            .validBlocks(VSCreateForgeBlocks.FERMENTING_TANK_FORGE)
            .renderer(() -> FluidTankRenderer::new)
            .register();
    public static void register() {
    }
}
