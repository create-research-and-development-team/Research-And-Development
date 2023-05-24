package org.valkyrienskies.vscreate.fabric;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.valkyrienskies.vscreate.VSCreateMod;

import java.nio.file.Path;
import java.util.List;
import java.util.Set;

public class VSCreateFabricData implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        Path rootPath = FabricLoader.getInstance()
                .getGameDir()
                .toAbsolutePath()
                .getParent()
                .getParent()
                .getParent();

        List<Path> contentPaths = List.of(
                rootPath.resolve("fabric/src/main/resources"),
                rootPath.resolve("common/src/main/resources"));

        ExistingFileHelper helper = new ExistingFileHelper(contentPaths, Set.of("create"), false, null, null);

        VSCreateMod.REGISTRATE.setupDatagen(generator, helper);
        VSCreateModFabric.gatherData(generator, helper);
    }
}
