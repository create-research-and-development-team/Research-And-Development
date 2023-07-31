package org.valkyrienskies.vsrnd.forge;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.valkyrienskies.vsrnd.VSCreateMod;
import org.valkyrienskies.vsrnd.effect.DrunkEffect;

public class VSCreateForgeMobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS
            = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, VSCreateMod.MOD_ID);

    public static final RegistryObject<MobEffect> DRUNK = MOB_EFFECTS.register("drunk",
            () -> new DrunkEffect(MobEffectCategory.HARMFUL, 16095298));

    public static void register(IEventBus eventBus) {
        MOB_EFFECTS.register(eventBus);
    }
}
