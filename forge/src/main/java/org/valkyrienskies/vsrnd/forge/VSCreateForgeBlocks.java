package org.valkyrienskies.vsrnd.forge;


import org.valkyrienskies.vsrnd.VSCreateMod;
//import org.valkyrienskies.clockwork.forge.content.contraptions.combustion_engine.ForgeCombustionEngineBlock;

import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static org.valkyrienskies.vsrnd.VSCreateMod.REGISTRATE;

public class VSCreateForgeBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> VSCreateMod.BASE_CREATIVE_TAB);
    }

    //////// Propellor Bearing ////////



//    public static final BlockEntry<ForgeCombustionEngineBlock> COMBUSTION_ENGINE =
//            REGISTRATE.block("combustion_engine", ForgeCombustionEngineBlock::new)
//                    .initialProperties(SharedProperties::copperMetal)
//                    .properties(p -> p.color(MaterialColor.COLOR_ORANGE))
//                    .transform(pickaxeOnly())
//                    .blockstate((c, p) -> p.horizontalFaceBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
//                    .item()
//                    .transform(customItemModel())
//                    .register();


    public static void register() {
    }
}
