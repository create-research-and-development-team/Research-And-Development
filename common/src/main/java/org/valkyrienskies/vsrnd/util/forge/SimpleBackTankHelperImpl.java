package org.valkyrienskies.vsrnd.util.forge;

import net.minecraft.world.item.Item;

public class SimpleBackTankHelperImpl {
	public static Item.Properties getProperties(Item.Properties properties, int maxDamage, int maxBackTankUses) {
		return properties.defaultDurability(maxDamage);
	}
}
