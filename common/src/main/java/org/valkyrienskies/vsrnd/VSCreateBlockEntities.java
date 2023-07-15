package org.valkyrienskies.vsrnd;


import com.tterrag.registrate.util.entry.BlockEntityEntry;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankBlockEntity;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankRenderer;


import static org.valkyrienskies.vsrnd.VSCreateMod.REGISTRATE;
public class VSCreateBlockEntities {

    // Kinetics

    public static final BlockEntityEntry<TitaniumTankBlockEntity> TITANIUM_TANK = REGISTRATE
            .blockEntity("fluid_tank", TitaniumTankBlockEntity::new)
            .validBlocks(VSCreateBlocks.TITANIUM_TANK)
            .renderer(() -> TitaniumTankRenderer::new)
            .register();

    public static void register() {
    }
}
