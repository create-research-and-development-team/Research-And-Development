package org.valkyrienskies.vsrnd.fabric;



import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.valkyrienskies.vsrnd.VSCreateMod;
import org.valkyrienskies.vsrnd.content.World.Rutile.RutileFeature;

import javax.annotation.Resource;
import java.util.Arrays;

public class VSCreateFabricFeatures {

    private static ConfiguredFeature<?, ?> RUTILE_CLUSTER_CONFIGURED = new ConfiguredFeature
            (new RutileFeature(NoneFeatureConfiguration.CODEC), new NoneFeatureConfiguration());

    public static PlacedFeature RUTILE_CLUSTER_PLACED = new PlacedFeature(
            Holder.direct(RUTILE_CLUSTER_CONFIGURED),
            Arrays.asList(
                    CountPlacement.of(20), // number of veins per chunk
                    InSquarePlacement.spread(), // spreading horizontally
                    HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(64))
            )); // height


    static public void init() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
                new ResourceLocation(VSCreateMod.MOD_ID, "Rutile_Cluster"), RUTILE_CLUSTER_CONFIGURED);
        Registry.register(BuiltinRegistries.PLACED_FEATURE, new ResourceLocation("tutorial", "overworld_wool_ore"),
                RUTILE_CLUSTER_PLACED);
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Decoration.UNDERGROUND_ORES,
                ResourceKey.create(Registry.PLACED_FEATURE_REGISTRY,new ResourceLocation(VSCreateMod.MOD_ID, "Rutile_Cluster"))  );
    }
}
