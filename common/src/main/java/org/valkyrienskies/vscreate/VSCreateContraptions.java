package org.valkyrienskies.vscreate;

import com.simibubi.create.content.contraptions.components.structureMovement.ContraptionType;
import org.valkyrienskies.vscreate.content.contraptions.propeller.PropellerContraption;

public class VSCreateContraptions {
    public static final ContraptionType
            PROPELLER = ContraptionType.register(VSCreateMod.asResource("propeller").toString(), PropellerContraption::new);

    public static void init() {
    }
}
