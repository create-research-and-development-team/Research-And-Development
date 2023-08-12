package org.valkyrienskies.vsrnd.fabric;


import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import org.valkyrienskies.vsrnd.VSCreateMod;

import static org.valkyrienskies.vsrnd.RNDRegistrate.REGISTRATE;

public class VSCreateFabricItems {

    static {
        REGISTRATE.creativeModeTab(() -> VSCreateMod.BASE_CREATIVE_TAB);
    }

    \


    //Shortcuts

    private static ItemEntry<Item> ingredient(String name) {
        return REGISTRATE.item(name, Item::new)
                .register();
    }

    public static void register() {
    }
}
