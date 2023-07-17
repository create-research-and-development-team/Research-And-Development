package org.valkyrienskies.vsrnd.fabric;


import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.valkyrienskies.vsrnd.VSCreateMod;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankGenerator;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankModel;
import org.valkyrienskies.vsrnd.fabric.Fluid.TitaniumTankBlock_FABRIC;
import org.valkyrienskies.vsrnd.fabric.Fluid.TitaniumTankItem_FABRIC;
import org.valkyrienskies.vsrnd.foundation.AssetLookup;


import static org.valkyrienskies.vsrnd.VSCreateMod.REGISTRATE;

public class VSCreateFabricBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> VSCreateMod.BASE_CREATIVE_TAB);
    }

    public static final BlockEntry<TitaniumTankBlock_FABRIC> TITANIUM_TANK = REGISTRATE.block("fluid_tank", TitaniumTankBlock_FABRIC::regular)
            .initialProperties(SharedProperties::copperMetal)
            .properties(BlockBehaviour.Properties::noOcclusion)
            //.properties(p -> p.isRedstoneConductor((p1, p2, p3) -> true))
            //.transform(pickaxeOnly())
            .blockstate(new TitaniumTankGenerator()::generate)
            .onRegister(CreateRegistrate.blockModel(() -> TitaniumTankModel::standard))
            //.onRegister(assignDataBehaviour(new BoilerDisplaySource(), "boiler_status"))
            .addLayer(() -> RenderType::cutoutMipped)
            .item(TitaniumTankItem_FABRIC::new)
            .model(AssetLookup.customBlockItemModel("_", "block_single_window"))
            .build()
            .register();

    public static void register() {
    }
}
