package org.valkyrienskies.vsrnd.fabric;

import com.simibubi.create.content.AllSections;
import org.valkyrienskies.vsrnd.VSCreateMod;


import static org.valkyrienskies.vsrnd.VSCreateMod.REGISTRATE;

public class VSCreateFabricBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> VSCreateMod.BASE_CREATIVE_TAB);
    }

    static {
        REGISTRATE.startSection(AllSections.KINETICS);
    }

    public static void register() {
    }
}
