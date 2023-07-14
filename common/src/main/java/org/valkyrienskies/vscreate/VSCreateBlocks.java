package org.valkyrienskies.vscreate;


import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;

import static org.valkyrienskies.vscreate.VSCreateMod.REGISTRATE;
import  org.valkyrienskies.vscreate.content.contraptions.sculk.sculkthruster.sculkthruster_block;

public class VSCreateBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> VSCreateMod.BASE_CREATIVE_TAB);
    }

    //they got rid of sections

    public static final BlockEntry<Block> TITANIUM_BLOCK = REGISTRATE.block("titanium_block", Block::new)
            .simpleItem()
            .register();

    public static final BlockEntry<sculkthruster_block> SCULK_THRUSTER = REGISTRATE.block("sculk_thruster", sculkthruster_block::new)
            .simpleItem()
            .register();

    public static void register() {
    }
}
