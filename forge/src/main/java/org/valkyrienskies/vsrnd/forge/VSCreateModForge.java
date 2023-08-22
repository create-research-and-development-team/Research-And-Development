package org.valkyrienskies.vsrnd.forge;

import com.simibubi.create.AllParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.valkyrienskies.vsrnd.*;
import org.valkyrienskies.vsrnd.forge.integration.cc_tweaked.VSCreateForgePeripheralProviders;

public class VSCreateModForge {
	boolean happenedClientSetup = false;

	public VSCreateModForge() {

		VSCreateBlocks.register();
		VSCreateForgeBlocks.register();

		// ForgeClockWorkTags.init();

		VSCreateItems.register();
		VSCreateForgeItems.register();

		VSCreateBlockEntities.register();
		VSCreateForgeBlockEntities.register();

		VSCreateEntities.register();
		VSCreateForgeEntities.register();

		VSCreateFluids.register();
		VSCreateForgeFluids.register();


		VSCreateSounds.register();
		// TODO forge sounds

		//VSCreateForgeParticles.init(modEventBus);
		//VSCreateForgeFeatures.register(modEventBus);

		VSCreateMod.init();
		VSCreatePackets.init();

		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
			IEventBus modEventBus = FMLJavaModLoadingContext.get()
															.getModEventBus();
			modEventBus.addListener(VSCreateForgeParticles::register);

			// In create itself they do it FMLClientSetupEvent this does not work (what a scam)
			// It prob gets staticly loaded earlier and well yhea...
			VSCreatePartials.init();
			modEventBus.addListener(AllParticleTypes::registerFactories);
			// TODO forge partials

			ShaderLoader.init(modEventBus);
		});

        if (FMLLoader.getLoadingModList().getModFileById("computercraft") != null) {
            VSCreateForgePeripheralProviders.register();
        }
	}

	public static ResourceLocation asResource(String path) {
		return new ResourceLocation(VSCreateMod.MOD_ID, path);
	}

	void clientSetup(final FMLClientSetupEvent event) {
		if (happenedClientSetup) return;
		happenedClientSetup = true;

		VSCreateMod.initClient();
	}

	void entityRenderers(final EntityRenderersEvent.RegisterRenderers event) {

	}

	void onModelRegistry(final ModelRegistryEvent event) {

	}
}
