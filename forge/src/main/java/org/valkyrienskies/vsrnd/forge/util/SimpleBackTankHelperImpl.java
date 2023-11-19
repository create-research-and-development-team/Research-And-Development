package org.valkyrienskies.vsrnd.forge.util;

import net.minecraft.world.item.Item;

public class SimpleBackTankHelperImpl {
	public static Item.Properties getProperties(Item.Properties properties, int maxDamage, int maxBackTankUses) {
		return properties.defaultDurability(maxDamage);
	}
}
