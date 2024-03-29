package org.valkyrienskies.vsrnd.forge;


import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.valkyrienskies.vsrnd.VSCreateMod;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankGenerator;
import org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank.TitaniumTankModel;
import org.valkyrienskies.vsrnd.forge.Fluid.FermentingTank.FermentingTankBlock_FORGE;
import org.valkyrienskies.vsrnd.forge.Fluid.FermentingTank.FermentingTankItem_FORGE;
import org.valkyrienskies.vsrnd.forge.Fluid.TitaniumTank.TitaniumTankBlock_FORGE;
import org.valkyrienskies.vsrnd.forge.Fluid.TitaniumTank.TitaniumTankItem_FORGE;
import org.valkyrienskies.vsrnd.foundation.AssetLookup;

import static org.valkyrienskies.vsrnd.RNDRegistrate.REGISTRATE;

public class VSCreateForgeBlocks {

	public static final BlockEntry<TitaniumTankBlock_FORGE> TITANIUM_TANK_FORGE = REGISTRATE.block("titanium_tank",
																								   TitaniumTankBlock_FORGE::regular)
																							.initialProperties(
																									SharedProperties::copperMetal)
																							.properties(BlockBehaviour.Properties::noOcclusion)
																							//.properties(p -> p.isRedstoneConductor((p1, p2, p3) -> true))
																							.blockstate(new TitaniumTankGenerator()::generate)
																							.onRegister(CreateRegistrate.blockModel(
																									() -> TitaniumTankModel::standard))
																							.addLayer(() -> RenderType::cutoutMipped)
																							.item(TitaniumTankItem_FORGE::new)
																							.model(AssetLookup.customBlockItemModel(
																									"_",
																									"block_single_window"))
																							.build()
																							.register();

	//////// Propellor Bearing ////////
	public static final BlockEntry<FermentingTankBlock_FORGE> FERMENTING_TANK_FORGE = REGISTRATE.block("fermenting_tank",
																									   FermentingTankBlock_FORGE::regular)
																								.initialProperties(
																										SharedProperties::wooden)
																								.properties(
																										BlockBehaviour.Properties::noOcclusion)
																								//.properties(p -> p.isRedstoneConductor((p1, p2, p3) -> true))
																								.blockstate(new TitaniumTankGenerator()::generate)
																								.onRegister(
																										CreateRegistrate.blockModel(
																												() -> TitaniumTankModel::standard))
																								.addLayer(() -> RenderType::cutoutMipped)
																								.item(FermentingTankItem_FORGE::new)
																								.model(AssetLookup.customBlockItemModel(
																										"_",
																										"block_single_window"))
																								.build()
																								.register();

	static {
		REGISTRATE.creativeModeTab(() -> VSCreateMod.BASE_CREATIVE_TAB);
	}

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
