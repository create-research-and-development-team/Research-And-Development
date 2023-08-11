package org.valkyrienskies.vsrnd.forge;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import net.minecraft.core.Holder;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.valkyrienskies.vsrnd.VSCreateMod;
import org.valkyrienskies.vsrnd.content.World.Rutile.RutileFeature;
import net.minecraftforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class VSCreateForgeFeatures {
    public static final DeferredRegister<Feature<?>> FEATURES =
            DeferredRegister.create(ForgeRegistries.FEATURES, VSCreateMod.MOD_ID);

    public static final RegistryObject<Feature<NoneFeatureConfiguration>> RUTILE_CLUSTER = FEATURES.register("rutile_cluster", () -> new RutileFeature(NoneFeatureConfiguration.CODEC));


    public static void register(IEventBus eventBus) {
        System.out.println("PRINTING SHIT AAAAAAAAAA");
        System.out.println(RUTILE_CLUSTER.getId());
        FEATURES.register(eventBus);
    }
}