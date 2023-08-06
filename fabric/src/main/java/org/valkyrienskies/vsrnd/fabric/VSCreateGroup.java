package org.valkyrienskies.vsrnd.fabric;


import net.minecraft.world.item.ItemStack;
import org.valkyrienskies.vsrnd.VSCreateBlocks;

import java.util.EnumSet;

public class VSCreateGroup extends VSCreateGroupBase {

    public VSCreateGroup() {
        super("base");
    }


    @Override
    public ItemStack makeIcon() {
        return VSCreateBlocks.TITANIUM_BLOCK.asStack();
    }
}
