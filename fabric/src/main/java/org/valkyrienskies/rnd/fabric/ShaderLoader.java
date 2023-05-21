package org.valkyrienskies.rnd.fabric;

import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManager;
import org.valkyrienskies.rnd.VSCreateMod;

public class ShaderLoader {

    public static void init() {
        final ResourceManagerHelper manager = ResourceManagerHelper.get(PackType.CLIENT_RESOURCES);
        manager.registerReloadListener(new SimpleSynchronousResourceReloadListener() {
            @Override
            public ResourceLocation getFabricId() {
                return VSCreateMod.asResource("shaders");
            }

            @Override
            public void onResourceManagerReload(ResourceManager resourceManager) {
            }
        });
    }

}
