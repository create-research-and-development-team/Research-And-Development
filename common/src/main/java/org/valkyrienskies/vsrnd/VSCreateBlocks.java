package org.valkyrienskies.vsrnd;


import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;


import net.minecraft.world.level.material.MaterialColor;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankBlock;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankGenerator;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankItem;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankModel;
import org.valkyrienskies.vsrnd.content.sculk.blocks.Cacoon.CacoonBlock;
import org.valkyrienskies.vsrnd.content.sculk.blocks.SculkThruster.SculkThrusterBlock;
import org.valkyrienskies.vsrnd.foundation.AssetLookup;
import static org.valkyrienskies.vsrnd.VSCreateMod.REGISTRATE;



public class VSCreateBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> VSCreateMod.BASE_CREATIVE_TAB);
    }

    //they got rid of sections

    public static final BlockEntry<Block> TITANIUM_BLOCK = REGISTRATE.block("titanium_block", Block::new)
            .simpleItem()
            .register();

    public static final BlockEntry<SculkThrusterBlock> SCULK_THRUSTER = REGISTRATE.block("sculk_thruster", SculkThrusterBlock::new)
            .simpleItem()
            .register();


    public static final BlockEntry<TitaniumTankBlock> TITANIUM_TANK = REGISTRATE.block("titanium_tank", TitaniumTankBlock::regular)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            .properties(p -> p.isRedstoneConductor((p1, p2, p3) -> true))
            .blockstate(new TitaniumTankGenerator()::generate)
            .onRegister(CreateRegistrate.blockModel(() -> TitaniumTankModel::standard))
            .addLayer(() -> RenderType::cutoutMipped)
            .item(TitaniumTankItem::new)
            .model(AssetLookup.customBlockItemModel("_", "block_single_window"))
            .build()
            .register();

    public static final BlockEntry<CacoonBlock> CACOON = REGISTRATE.block("cacoon", CacoonBlock::new)
            .initialProperties(SharedProperties::stone)
            .properties(p -> p.color(MaterialColor.METAL))
            .transform(BlockStressDefaults.setImpact(1))
            .blockstate(BlockStateGen.axisBlockProvider(false))
            .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
            .simpleItem()
            .register();
    public static void register() {
    }
}
