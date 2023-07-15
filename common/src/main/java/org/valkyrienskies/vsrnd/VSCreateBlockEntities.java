package org.valkyrienskies.vsrnd;


import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntity;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityInstance;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockEntityRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankBlockEntity;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankRenderer;
import org.valkyrienskies.vsrnd.content.sculk.blocks.Cacoon.CacoonBlockEntity;
import org.valkyrienskies.vsrnd.content.sculk.blocks.Cacoon.CacoonBlockEntityInstance;
import org.valkyrienskies.vsrnd.content.sculk.blocks.Cacoon.CacoonBlockEntityRenderer;


import static org.valkyrienskies.vsrnd.VSCreateMod.REGISTRATE;
public class VSCreateBlockEntities {

    // Kinetics

    public static final BlockEntityEntry<TitaniumTankBlockEntity> TITANIUM_TANK = REGISTRATE
            .blockEntity("fluid_tank", TitaniumTankBlockEntity::new)
            .validBlocks(VSCreateBlocks.TITANIUM_TANK)
            .renderer(() -> TitaniumTankRenderer::new)
            .register();

    public static final BlockEntityEntry<CacoonBlockEntity> CACOON_BLOCK_ENTITY = Create.REGISTRATE
            .blockEntity("cacoon_entity", CacoonBlockEntity::new)
            .instance(() -> CacoonBlockEntityInstance::new, false)
            .validBlocks(VSCreateBlocks.CACOON)
            .renderer(() -> CacoonBlockEntityRenderer::new)
            .register();
    public static void register() {
    }
}
