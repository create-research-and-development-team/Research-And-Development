package org.valkyrienskies.vsrnd.forge;

import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.valkyrienskies.vsrnd.VSCreateMod;

public class VSCreateForgeFeatures {
	public static final DeferredRegister<Feature<?>> FEATURES =
			DeferredRegister.create(ForgeRegistries.FEATURES, VSCreateMod.MOD_ID);

	public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUTILE_CLUSTER = FEATURES.register(
			"rutile_cluster",
			() -> new org.valkyrienskies.vsrnd.content.World.Rutile.RutileFeature(NoneFeatureConfiguration.CODEC));


	public static void register() {
	}
}
