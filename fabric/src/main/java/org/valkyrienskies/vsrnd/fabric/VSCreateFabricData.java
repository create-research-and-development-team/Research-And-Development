package org.valkyrienskies.vsrnd.fabric;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.valkyrienskies.vsrnd.VSCreateMod;

import static org.valkyrienskies.vsrnd.RNDRegistrate.REGISTRATE;

public class VSCreateFabricData implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator generator) {
        ExistingFileHelper helper = ExistingFileHelper.standard();
        REGISTRATE.setupDatagen(generator, helper);
        VSCreateModFabric.gatherData(generator, helper);
    }
}
