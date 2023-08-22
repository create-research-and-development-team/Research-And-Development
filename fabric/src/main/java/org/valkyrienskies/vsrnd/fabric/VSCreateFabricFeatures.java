package org.valkyrienskies.vsrnd.fabric;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.BuiltinRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.HeightRangePlacement;
import net.minecraft.world.level.levelgen.placement.InSquarePlacement;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import org.valkyrienskies.vsrnd.VSCreateMod;
import org.valkyrienskies.vsrnd.content.World.Rutile.RutileFeature;

import java.util.Arrays;

public class VSCreateFabricFeatures {

    private static ConfiguredFeature<?, ?> RUTILE_CLUSTER_CONFIGURED = new ConfiguredFeature
            (new RutileFeature(NoneFeatureConfiguration.CODEC), NoneFeatureConfiguration.INSTANCE);

    public static PlacedFeature RUTILE_CLUSTER_PLACED = new PlacedFeature(
            Holder.direct(RUTILE_CLUSTER_CONFIGURED),
            Arrays.asList(
                    CountPlacement.of(20), // number of veins per chunk
                    InSquarePlacement.spread(), // spreading horizontally
                    HeightRangePlacement.uniform(VerticalAnchor.bottom(), VerticalAnchor.absolute(64))
            )); // height


    static public void init() {
        Registry.register(BuiltinRegistries.CONFIGURED_FEATURE,
                          new ResourceLocation(VSCreateMod.MOD_ID, "rutile_cluster"),
                          RUTILE_CLUSTER_CONFIGURED);
        Registry.register(BuiltinRegistries.PLACED_FEATURE,
                          new ResourceLocation(VSCreateMod.MOD_ID, "rutile_cluster"),
                          RUTILE_CLUSTER_PLACED);
        // TODO: Fix crash related to adding rutile clusters
        /*BiomeModifications.addFeature(
                BiomeSelectors.foundInOverworld(),
                GenerationStep.Decoration.UNDERGROUND_ORES,
                ResourceKey.create(
                        Registry.PLACED_FEATURE_REGISTRY,
                        new ResourceLocation(VSCreateMod.MOD_ID, "rutile_cluster")));*/
    }
}
