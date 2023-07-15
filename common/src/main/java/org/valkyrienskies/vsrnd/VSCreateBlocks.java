package org.valkyrienskies.vsrnd;

import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;


import static org.valkyrienskies.vsrnd.VSCreateMod.REGISTRATE;
import org.valkyrienskies.vsrnd.content.sculk.blocks.SculkThrusterBlock;

public class VSCreateBlocks {

    static {
        REGISTRATE.creativeModeTab(() -> VSCreateMod.BASE_CREATIVE_TAB);
    }

    //they got rid of sections

    public static final BlockEntry<Block> TITANIUM_BLOCK = REGISTRATE.block("titanium_block", Block::new)
            .simpleItem()
            .register();

    public static final BlockEntry<SculkThrusterBlock> SCULK_THRUSTER = REGISTRATE.block("sculk_thruster", SculkThrusterBlock::new)
            .simpleItem()
            .register();

    public static void register() {
    }
}
