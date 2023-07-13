package org.valkyrienskies.vscreate.content.contraptions.tools.drill.fabric;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import org.lwjgl.system.NonnullDefault;
import org.valkyrienskies.vscreate.content.contraptions.tools.drill.HandheldMechanicalDrill;

import java.util.function.Consumer;

@NonnullDefault
public class HandheldMechanicalDrillImpl extends HandheldMechanicalDrill {
    public HandheldMechanicalDrillImpl(Tier tier, int attackBonus, float attackSpeedBonus, Properties properties) {
        super(tier, attackBonus, attackSpeedBonus, properties);
    }

    public static <T extends LivingEntity> int getItemDamage(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        return amount;
    }
}
