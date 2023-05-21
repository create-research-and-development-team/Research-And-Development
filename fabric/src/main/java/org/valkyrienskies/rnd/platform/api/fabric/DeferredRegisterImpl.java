package org.valkyrienskies.rnd.platform.api.fabric;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class DeferredRegisterImpl<T> implements org.valkyrienskies.rnd.platform.api.DeferredRegister<T> {
    private final Registry<T> registry;
    private final String modId;

    private DeferredRegisterImpl(Registry<T> registry, String modId) {
        this.registry = registry;
        this.modId = modId;
    }

    public static <T> org.valkyrienskies.rnd.platform.api.DeferredRegister<T> create(Registry<T> registry, String mod_id) {
        return new DeferredRegisterImpl<T>(registry, mod_id);
    }

    @Override
    public void register(String id, Supplier<T> value) {
        Registry.register(registry, new ResourceLocation(modId, id), value.get());
    }

    @Override
    public void registerAll() {

    }

}
