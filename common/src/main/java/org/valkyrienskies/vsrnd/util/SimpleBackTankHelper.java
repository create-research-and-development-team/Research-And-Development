package org.valkyrienskies.vsrnd.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.Item;

public class SimpleBackTankHelper {
    @ExpectPlatform
    public static Item.Properties getProperties(Item.Properties properties, int maxDamage, int maxBackTankUses) {
        return properties.defaultDurability(maxDamage);
    }
}