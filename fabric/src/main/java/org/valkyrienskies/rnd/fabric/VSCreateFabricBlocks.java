package org.valkyrienskies.rnd.fabric;

import com.simibubi.create.content.AllSections;
import org.valkyrienskies.rnd.VSCreateMod;


import static org.valkyrienskies.rnd.VSCreateMod.REGISTRATE;

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
