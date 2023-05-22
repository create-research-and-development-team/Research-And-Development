package org.valkyrienskies.vscreate;


import com.tterrag.registrate.util.entry.BlockEntityEntry;
import org.valkyrienskies.vscreate.content.contraptions.propeller.PropellerBearingBlockEntity;
import org.valkyrienskies.vscreate.content.contraptions.propeller.PropellerBearingRenderer;

import static org.valkyrienskies.vscreate.VSCreateMod.REGISTRATE;

public class VSCreateBlockEntities {

    // Kinetics
    public static final BlockEntityEntry<PropellerBearingBlockEntity> PROPELLOR_BEARING = REGISTRATE
            .tileEntity("propellor_bearing", PropellerBearingBlockEntity::new)
//            .instance(() -> BearingInstance::new)
            .validBlocks(VSCreateBlocks.PROPELLER_BEARING)
            .renderer(() -> PropellerBearingRenderer::new)
            .register();

    public static void register() {
    }
}
