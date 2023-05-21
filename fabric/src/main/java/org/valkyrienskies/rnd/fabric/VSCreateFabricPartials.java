package org.valkyrienskies.rnd.fabric;

import com.jozufozu.flywheel.core.PartialModel;
import org.valkyrienskies.rnd.VSCreateMod;


public class VSCreateFabricPartials {

    // Platform specific partials

    private static PartialModel block(String path) {
        return new PartialModel(VSCreateMod.asResource("block/" + path));
    }

    private static PartialModel entity(String path) {
        return new PartialModel(VSCreateMod.asResource("entity/" + path));
    }

    public static void init() {
        // init static fields
    }
}
