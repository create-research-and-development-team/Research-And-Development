package org.valkyrienskies.rnd.forge;

import com.simibubi.create.content.contraptions.particle.ICustomParticleData;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.valkyrienskies.rnd.VSCreateMod;
import org.valkyrienskies.rnd.VSCreateParticles;

import java.util.function.Supplier;

// Why all and not forge? cus it also loads the common thus all of them
public enum VSCreateForgeParticles {
    ;

    private final ParticleEntry<?> entry;

    <D extends ParticleOptions> VSCreateForgeParticles(Supplier<? extends ICustomParticleData<D>> typeFactory) {
        String name = Lang.asId(name());
        entry = new ParticleEntry<>(name, typeFactory);
    }

    public static void init(IEventBus modEventBus) {
        VSCreateParticles.init();
        ParticleEntry.REGISTER.register(modEventBus);
    }

    @OnlyIn(Dist.CLIENT)
    public static void register(ParticleFactoryRegisterEvent event) {
        VSCreateParticles.initClient();

        ParticleEngine particles = Minecraft.getInstance().particleEngine;
        for (VSCreateForgeParticles particle : values())
            particle.entry.registerFactory(particles);
    }

    public ParticleType<?> get() {
        return entry.object.get();
    }

    public String parameter() {
        return entry.name;
    }

    private static class ParticleEntry<D extends ParticleOptions> {
        private static final DeferredRegister<ParticleType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, VSCreateMod.MOD_ID);

        private final String name;
        private final Supplier<? extends ICustomParticleData<D>> typeFactory;
        private final RegistryObject<ParticleType<D>> object;

        public ParticleEntry(String name, Supplier<? extends ICustomParticleData<D>> typeFactory) {
            this.name = name;
            this.typeFactory = typeFactory;

            object = REGISTER.register(name, () -> this.typeFactory.get().createType());
        }

        @OnlyIn(Dist.CLIENT)
        public void registerFactory(ParticleEngine particles) {
            typeFactory.get()
                    .register(object.get(), particles);
        }

    }

}