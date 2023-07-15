package org.valkyrienskies.vsrnd;


import com.simibubi.create.Create;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankBlockEntity;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankRenderer;
import org.valkyrienskies.vsrnd.content.sculk.blocks.Cocoon.CocoonBlockEntity;
import org.valkyrienskies.vsrnd.content.sculk.blocks.Cocoon.CacoonBlockEntityInstance;
import org.valkyrienskies.vsrnd.content.sculk.blocks.Cocoon.CocoonBlockEntityRenderer;


import static org.valkyrienskies.vsrnd.VSCreateMod.REGISTRATE;
public class VSCreateBlockEntities {

    // Kinetics

    public static final BlockEntityEntry<TitaniumTankBlockEntity> TITANIUM_TANK = REGISTRATE
            .blockEntity("fluid_tank", TitaniumTankBlockEntity::new)
            .validBlocks(VSCreateBlocks.TITANIUM_TANK)
            .renderer(() -> TitaniumTankRenderer::new)
            .register();

    public static final BlockEntityEntry<CocoonBlockEntity> COCOON_BLOCK_ENTITY = Create.REGISTRATE
            .blockEntity("cacoon_entity", CocoonBlockEntity::new)
            .instance(() -> CacoonBlockEntityInstance::new, false)
            .validBlocks(VSCreateBlocks.COCOON)
            .renderer(() -> CocoonBlockEntityRenderer::new)
            .register();
    public static void register() {
    }
}
