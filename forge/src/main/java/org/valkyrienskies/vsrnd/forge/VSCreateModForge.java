package org.valkyrienskies.vsrnd.forge;

import com.simibubi.create.AllParticleTypes;
import com.simibubi.create.AllRecipeTypes;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.valkyrienskies.vsrnd.*;
import org.valkyrienskies.vsrnd.forge.integration.cc_tweaked.VSCreateForgePeripheralProviders;

import static org.valkyrienskies.vsrnd.VSCreateMod.REGISTRATE;

@Mod(VSCreateMod.MOD_ID)
public class VSCreateModForge {
    boolean happendClientSetup = false;

    public VSCreateModForge() {
        // Submit our event bus to let architectury register our content on the right time
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        modEventBus.addListener(this::clientSetup);
        REGISTRATE.registerEventListeners(modEventBus);
        modEventBus.addListener(this::onModelRegistry);
        modEventBus.addListener(this::entityRenderers);

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

        VSCreateForgeParticles.init(modEventBus);
        VSCreateForgeRecipes.register(modEventBus);

        VSCreateMod.init();
        VSCreatePackets.init();

        VSCreateForgeMobEffects.register(modEventBus);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            FMLJavaModLoadingContext.get()
                    .getModEventBus().addListener(VSCreateForgeParticles::register);

            // In create itself they do it FMLClientSetupEvent this does not work (what a scam)
            // It prob gets staticly loaded earlier and well yhea...
            VSCreatePartials.init();
            modEventBus.addListener(AllParticleTypes::registerFactories);
            // TODO forge partials

            ShaderLoader.init(modEventBus);
        });

        if (FMLLoader.getLoadingModList().getModFileById("computercraft") != null)
            VSCreateForgePeripheralProviders.register();
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(VSCreateMod.MOD_ID, path);
    }

    void clientSetup(final FMLClientSetupEvent event) {
        if (happendClientSetup) return;
        happendClientSetup = true;

        VSCreateMod.initClient();


        ItemBlockRenderTypes.setRenderLayer(VSCreateBlocks.FISHBLOCK.get(), RenderType.cutout());

    }

    void entityRenderers(final EntityRenderersEvent.RegisterRenderers event) {

    }

    void onModelRegistry(final ModelRegistryEvent event) {

    }
}
