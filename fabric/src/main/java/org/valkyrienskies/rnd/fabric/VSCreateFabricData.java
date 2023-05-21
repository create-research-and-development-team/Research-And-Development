package org.valkyrienskies.rnd.fabric;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.valkyrienskies.rnd.VSCreateMod;

public class VSCreateFabricData implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        ExistingFileHelper helper = ExistingFileHelper.standard();
        VSCreateMod.REGISTRATE.setupDatagen(generator, helper);
        VSCreateModFabric.gatherData(generator, helper);
    }
}
