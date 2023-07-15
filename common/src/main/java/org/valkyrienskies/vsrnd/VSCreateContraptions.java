package org.valkyrienskies.vsrnd;


import com.simibubi.create.content.contraptions.ContraptionType;
import org.valkyrienskies.vsrnd.content.contraptions.centrifuge.CentrifugeContraption;

public class VSCreateContraptions {
    public static final ContraptionType
            CENTRIFUGE = ContraptionType.register(VSCreateMod.asResource("centrifuge").toString(), CentrifugeContraption::new);

    public static void init() {
    }
}
