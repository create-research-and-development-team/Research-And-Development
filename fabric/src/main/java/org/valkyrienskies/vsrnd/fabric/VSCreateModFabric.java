package org.valkyrienskies.vsrnd.fabric;

import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import org.valkyrienskies.mod.fabric.common.ValkyrienSkiesModFabric;
import org.valkyrienskies.vsrnd.*;
import org.valkyrienskies.vsrnd.fabric.integration.cc_restiched.VSCreateFabricPeripheralProviders;
import org.valkyrienskies.vsrnd.platform.fabric.FallbackFabricTransfer;

import static org.valkyrienskies.vsrnd.RNDRegistrate.REGISTRATE;


public class VSCreateModFabric implements ModInitializer {

	@Override
	public void onInitialize() {
		// force VS2 to load before eureka
		new ValkyrienSkiesModFabric().onInitialize();

		VSCreateBlocks.register();
		VSCreateFabricBlocks.register();

		// TODO common items
		VSCreateItems.register();
		VSCreateFabricItems.register();

		VSCreateBlockEntities.register();
		VSCreateFabricBlockEntities.register();

		// TODO common entities
		VSCreateEntities.register();
		VSCreateFabricEntities.register();

		VSCreateFluids.register();
		VSCreateFabricFluids.register();

		VSCreateSounds.register();
		VSCreateFabricSounds.prepare();

		REGISTRATE.register();

		VSCreateMod.init();
		VSCreateModFabric.init();

		VSCreateFabricFeatures.init();

        if (FabricLoader.getInstance().isModLoaded("computercraft")) {
            VSCreateFabricPeripheralProviders.register();
        }
	}

	public static void init() {
		VSCreateParticles.init();

		VSCreateFabricParticles.init();
		VSCreateFabricSounds.init();
		FallbackFabricTransfer.init();

		//ClockworkCommonEvents.register();
	}

	@Environment(EnvType.CLIENT)
	public static class Client implements ClientModInitializer {
		public static void registerInputEvents() {
		}

		@Override
		public void onInitializeClient() {
			VSCreateMod.initClient();

			VSCreatePartials.init();
			VSCreateFabricPartials.init();

			VSCreateParticles.initClient();
			VSCreateFabricParticles.initClient();

			registerClientEvents();
			registerClientEvents();
			ShaderLoader.init();
		}

		public static void registerClientEvents() {
		}
	}

	public static class ModMenu implements ModMenuApi {
	}
}
