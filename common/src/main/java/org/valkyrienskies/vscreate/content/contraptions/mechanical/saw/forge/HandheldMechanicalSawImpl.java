package org.valkyrienskies.vscreate.content.contraptions.mechanical.saw.forge;

import com.simibubi.create.content.curiosities.armor.BackTankUtil;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import org.lwjgl.system.NonnullDefault;
import org.valkyrienskies.vscreate.content.contraptions.mechanical.saw.HandheldMechanicalSaw;

import java.util.function.Consumer;

@NonnullDefault
public class HandheldMechanicalSawImpl extends HandheldMechanicalSaw {
    public HandheldMechanicalSawImpl(Tier tier, int attackBonus, float attackSpeedBonus, Properties properties) {
        super(tier, attackBonus, attackSpeedBonus, properties);
    }

    public static <T extends LivingEntity> int getItemDamage(ItemStack stack, int amount, T entity, Consumer<T> onBroken) {
        if(BackTankUtil.canAbsorbDamage(entity, MAX_BACKTANK_USES)) {
            return 0;
        } else {
            return amount;
        }
    }
}
