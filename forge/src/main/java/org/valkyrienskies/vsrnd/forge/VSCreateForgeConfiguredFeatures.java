package org.valkyrienskies.vsrnd.forge;
import net.minecraft.core.Registry;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import org.valkyrienskies.vsrnd.VSCreateMod;

public class VSCreateForgeConfiguredFeatures {
	public static final DeferredRegister<ConfiguredFeature<?, ?>> CONFIGURED_FEATURES = DeferredRegister.create(
			Registry.CONFIGURED_FEATURE_REGISTRY, VSCreateMod.MOD_ID);

	public static final RegistryObject<ConfiguredFeature<?, ?>> RUTILE_CLUSTER = CONFIGURED_FEATURES.register("rutile_cluster",
			() -> new ConfiguredFeature<>(VSCreateForgeFeatures.RUTILE_CLUSTER.get(), new NoneFeatureConfiguration()));

	public static void register(IEventBus eventBus) {
		CONFIGURED_FEATURES.register(eventBus);
	}
}
