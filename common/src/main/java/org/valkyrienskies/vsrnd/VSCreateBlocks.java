package org.valkyrienskies.vsrnd;


import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;


import net.minecraft.world.level.block.state.BlockBehaviour;
import org.valkyrienskies.vsrnd.content.blocks.Yeast;
import org.valkyrienskies.vsrnd.content.sculk.blocks.Cocoon.CocoonBlock;
import org.valkyrienskies.vsrnd.content.sculk.blocks.Rutile.RutileClusterBlock;
import org.valkyrienskies.vsrnd.content.sculk.blocks.SculkThruster.SculkThrusterBlock;

import static org.valkyrienskies.vsrnd.RNDRegistrate.REGISTRATE;


public class VSCreateBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> VSCreateMod.BASE_CREATIVE_TAB);
    }

    //they got rid of sections
    //What the fuck is a section???
    public static final BlockEntry<Block> TITANIUM_BLOCK = REGISTRATE.block("titanium_block", Block::new)
            .simpleItem()
            .register();

    public static final BlockEntry<SculkThrusterBlock> SCULK_THRUSTER = REGISTRATE.block("sculk_thruster", SculkThrusterBlock::new)
            .simpleItem()
            .register();


    public static final BlockEntry<Yeast> YEAST = REGISTRATE.block("yeast", Yeast::new)
            .simpleItem()
            .register();

    public static final BlockEntry<RutileClusterBlock> RUTILE_CLUSTER = REGISTRATE.block("rutile_cluster", RutileClusterBlock::new)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .addLayer(() -> RenderType::cutoutMipped)
            .simpleItem()
            .register();


//    public static final BlockEntry<TitaniumTankBlock> TITANIUM_TANK = REGISTRATE.block("titanium_tank", TitaniumTankBlock::regular)
//            .initialProperties(SharedProperties::copperMetal)
//            .properties(BlockBehaviour.Properties::noOcclusion)
//            .properties(p -> p.isRedstoneConductor((p1, p2, p3) -> true))
//            .blockstate(new TitaniumTankGenerator()::generate)
//            .onRegister(CreateRegistrate.blockModel(() -> TitaniumTankModel::standard))
//            .addLayer(() -> RenderType::cutoutMipped)
//            .item(TitaniumTankItem::new)
//            .model(AssetLookup.customBlockItemModel("_", "block_single_window"))
//            .build()
//            .register();

    public static final BlockEntry<CocoonBlock> COCOON = REGISTRATE.block("cocoon", CocoonBlock::new)
            .simpleItem()
            .register();


    public static void register() {
    }
}
