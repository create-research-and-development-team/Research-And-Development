package org.valkyrienskies.vscreate;

import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import org.valkyrienskies.vscreate.content.contraptions.mechanical.drill.HandheldMechanicalDrill;
import org.valkyrienskies.vscreate.content.contraptions.mechanical.saw.HandheldMechanicalSaw;


import static com.simibubi.create.content.AllSections.MATERIALS;
import static org.valkyrienskies.vscreate.VSCreateMod.REGISTRATE;

public class VSCreateItems {
    static {
        REGISTRATE.creativeModeTab(() -> VSCreateMod.BASE_CREATIVE_TAB);
    }

    public static final ItemEntry<HandheldMechanicalSaw> HANDHELD_MECHANICAL_SAW =
            REGISTRATE.item("handheld_mechanical_saw",
                            (p) -> new HandheldMechanicalSaw(Tiers.DIAMOND, 5, -3f, p))
                    .register();

    public static final ItemEntry<HandheldMechanicalDrill> HANDHELD_MECHANICAL_DRILL =
            REGISTRATE.item("handheld_mechanical_drill",
                            (p) -> new HandheldMechanicalDrill(Tiers.DIAMOND, 1, -2.8f, p))
                    .register();

    static {
        REGISTRATE.startSection(MATERIALS);
    }

    public static final ItemEntry<Item>

    RUTILE = ingredient("rutile"),
    SODIUM = ingredient("sodium");
    private static ItemEntry<Item> ingredient(String name) {
        return REGISTRATE.item(name, Item::new)
                .register();
    }

    public static final ItemEntry<Item> TITANIUM_INGOT = REGISTRATE.item("titanium_ingot", Item::new)
            .register();

    public static final ItemEntry<Item> TITANIUM_SHEET = REGISTRATE.item("titanium_sheet", Item::new)
            .register();

    public static final ItemEntry<SequencedAssemblyItem>
            DENTED_TITANIUM_INGOT = sequencedIngredient("dented_titanium_ingot");

    private static ItemEntry<SequencedAssemblyItem> sequencedIngredient(String name) {
        return REGISTRATE.item(name, SequencedAssemblyItem::new)
                .register();
    }
    public static void register() {
    }
}
