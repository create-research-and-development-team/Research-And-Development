package org.valkyrienskies.rnd;


import com.tterrag.registrate.util.entry.BlockEntityEntry;
import org.valkyrienskies.rnd.content.contraptions.propellor.PropellorBearingBlockEntity;
import org.valkyrienskies.rnd.content.contraptions.propellor.PropellorBearingRenderer;

import static org.valkyrienskies.rnd.VSCreateMod.REGISTRATE;

public class VSCreateBlockEntities {

    // Kinetics
    public static final BlockEntityEntry<PropellorBearingBlockEntity> PROPELLOR_BEARING = REGISTRATE
            .tileEntity("propellor_bearing", PropellorBearingBlockEntity::new)
//            .instance(() -> BearingInstance::new)
            .validBlocks(VSCreateBlocks.PROPELLOR_BEARING)
            .renderer(() -> PropellorBearingRenderer::new)
            .register();

    public static void register() {
    }
}
