package org.valkyrienskies.vscreate;

import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static com.simibubi.create.content.AllSections.MATERIALS;
import static org.valkyrienskies.vscreate.VSCreateMod.REGISTRATE;

public class VSCreateItems {



    static {
        REGISTRATE.creativeModeTab(() -> VSCreateMod.BASE_CREATIVE_TAB);
    }

    static {
        REGISTRATE.startSection(MATERIALS);
    }

    private static ItemEntry<Item> ingredient(String name) {
        return REGISTRATE.item(name, Item::new)
                .register();
    }

    private static final ItemEntry<Item> TITANIUM_INGOT = REGISTRATE.item("titanium_ingot", Item::new)
            .register();

    private static final ItemEntry<Item> TITANIUM_SHEET = REGISTRATE.item("titanium_sheet", Item::new)
            .register();

    private static final ItemEntry<SequencedAssemblyItem> DENTED_TITANIUM_SHEET = REGISTRATE.item("dented_titanium_sheet", SequencedAssemblyItem::new)
            .register();

    public static void register() {
    }
}
