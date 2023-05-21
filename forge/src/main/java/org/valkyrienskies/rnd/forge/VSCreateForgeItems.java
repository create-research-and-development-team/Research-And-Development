package org.valkyrienskies.rnd.forge;

import com.simibubi.create.content.AllSections;
import org.valkyrienskies.rnd.VSCreateMod;

import static org.valkyrienskies.rnd.VSCreateMod.REGISTRATE;

public class VSCreateForgeItems {
    static {
        REGISTRATE.creativeModeTab(() -> VSCreateMod.BASE_CREATIVE_TAB);
    }

    static {
        REGISTRATE.startSection(AllSections.CURIOSITIES);
    }

    public static void register() {
    }
}
