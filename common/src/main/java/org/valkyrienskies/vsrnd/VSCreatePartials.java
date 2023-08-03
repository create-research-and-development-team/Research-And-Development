package org.valkyrienskies.vsrnd;

import com.jozufozu.flywheel.core.PartialModel;


public class VSCreatePartials {


    private static PartialModel block(String path) {
        return new PartialModel(VSCreateMod.asResource("block/" + path));
    }

    private static PartialModel entity(String path) {
        return new PartialModel(VSCreateMod.asResource("entity/" + path));
    }


    public static final PartialModel
            FISH_HEAD = block("fish/head"), FISH_TAIL = block("fish/tail"), FISH_FLIPPER = block("fish/flipper"), FISH_JAW = block("fish/jaw")
            ;

    public static void init() {
        // init static fields
    }
}
