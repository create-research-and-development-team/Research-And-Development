package org.valkyrienskies.vsrnd.content.items.tools.drill.forge;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import org.lwjgl.system.NonnullDefault;
import org.valkyrienskies.vsrnd.content.items.tools.drill.HandheldMechanicalDrill;

import java.util.function.Consumer;

@NonnullDefault
public class HandheldMechanicalDrillImpl extends HandheldMechanicalDrill {
    public HandheldMechanicalDrillImpl(Tier tier, int attackBonus, float attackSpeedBonus, Properties properties) {
        super(tier, attackBonus, attackSpeedBonus, properties);
    }

    public static <T extends LivingEntity> int getItemDamage(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        if(BacktankUtil.canAbsorbDamage(entity, MAX_BACKTANK_USES)) {
            return 0;
        } else {
            return amount;
        }
    }
}
