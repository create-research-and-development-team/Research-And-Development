package org.valkyrienskies.vsrnd;


import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.kinetics.base.HorizontalHalfShaftInstance;
import com.simibubi.create.content.kinetics.clock.CuckooClockBlockEntity;
import com.simibubi.create.content.kinetics.clock.CuckooClockRenderer;
import com.tterrag.registrate.util.entry.BlockEntityEntry;
import org.valkyrienskies.vsrnd.content.contraptions.fish.FishBlockEntity;
import org.valkyrienskies.vsrnd.content.contraptions.fish.FishBlockRenderer;
import org.valkyrienskies.vsrnd.content.sculk.blocks.Cocoon.CocoonBlockEntity;
import org.valkyrienskies.vsrnd.content.sculk.blocks.Cocoon.CacoonBlockEntityInstance;
import org.valkyrienskies.vsrnd.content.sculk.blocks.Cocoon.CocoonBlockEntityRenderer;
import org.valkyrienskies.vsrnd.content.sculk.blocks.SculkCore.SculkCoreBlockEntity;
import org.valkyrienskies.vsrnd.content.sculk.blocks.StomachCore.StomachCoreBlockEntity;

import static org.valkyrienskies.vsrnd.VSCreateMod.REGISTRATE;


public class VSCreateBlockEntities {

    // Kinetics



    public static final BlockEntityEntry<CocoonBlockEntity> COCOON_BLOCK_ENTITY = REGISTRATE
            .blockEntity("cocoon_entity", CocoonBlockEntity::new)
            .instance(() -> CacoonBlockEntityInstance::new, false)
            .validBlocks(VSCreateBlocks.COCOON)
            .renderer(() -> CocoonBlockEntityRenderer::new)
            .register();
    public static final BlockEntityEntry<SculkCoreBlockEntity> SCULK_CORE_BLOCK_ENTITY = REGISTRATE
            .blockEntity("sculk_core_entity", SculkCoreBlockEntity::new)
            .validBlocks(VSCreateBlocks.SCULK_CORE)
            .register();

    public static final BlockEntityEntry<StomachCoreBlockEntity> STOMACH_CORE_BLOCK_ENTITY = REGISTRATE
            .blockEntity("stomachcore_entity", StomachCoreBlockEntity::new)
            .validBlocks(VSCreateBlocks.STOMACH_CORE)
            .register();


    public static final BlockEntityEntry<FishBlockEntity> FISH = REGISTRATE
            .blockEntity("fishblock_entity", FishBlockEntity::new)
            .validBlocks(VSCreateBlocks.FISHBLOCK)
            .renderer(() -> FishBlockRenderer::new)

            .register();

//    public static final BlockEntityEntry<FishBlockEntity> FISH = REGISTRATE
//            .blockEntity("fishblock_entity", FishBlockEntity::new)
//            .validBlocks(VSCreateBlocks.FISHBLOCK)
//            .renderer(() -> FishBlockRenderer::new)
//            .register();
    public static void register() {
    }
}
