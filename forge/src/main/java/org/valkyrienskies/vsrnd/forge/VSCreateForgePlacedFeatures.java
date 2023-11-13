package org.valkyrienskies.vsrnd.forge;

import net.minecraft.core.Registry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.valkyrienskies.vsrnd.VSCreateMod;

import java.util.List;

@Mod.EventBusSubscriber(modid = VSCreateMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class VSCreateForgePlacedFeatures {
	public static final DeferredRegister<PlacedFeature> PLACED_FEATURES = DeferredRegister.create(
			Registry.PLACED_FEATURE_REGISTRY, VSCreateMod.MOD_ID);
/*
	public static final RegistryObject<PlacedFeature> RUTILE_CLUSTER_PLACED = PLACED_FEATURES.register(
			"rutile_cluster_placed",
			() -> new PlacedFeature(VSCreateForgeConfiguredFeatures.RUTILE_CLUSTER.getHolder().get(),
									List.of(
											CountPlacement.of(10),
											InSquarePlacement.spread(),
											HeightRangePlacement.triangle(
													VerticalAnchor.aboveBottom(100),
													VerticalAnchor.bottom()
																		 ),
											BiomeFilter.biome()
										   )));*/

	public static void register(IEventBus eventBus) {
		PLACED_FEATURES.register(eventBus);
	}


	@SubscribeEvent
	public static void onBiomeLoadingEvent(BiomeLoadingEvent event) {/*
		if (event.getCategory() != Biome.BiomeCategory.NETHER && event.getCategory() != Biome.BiomeCategory.THEEND) {
			event.getGeneration()
				 .addFeature(GenerationStep.Decoration.UNDERGROUND_ORES, RUTILE_CLUSTER_PLACED.getHolder().get());
		}*/
	}
}
