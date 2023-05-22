package org.valkyrienskies.vscreate.util;

import com.simibubi.create.content.curiosities.armor.BackTankUtil;
import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class BacktankDamageHandler implements CustomDamageHandler {
    private final int maxBacktankUses;
    public BacktankDamageHandler(int maxBacktankUses) {
        this.maxBacktankUses = maxBacktankUses;
    }

    @Override
    public int damage(ItemStack stack, int amount, LivingEntity entity, Consumer<LivingEntity> breakCallback) {
        if(BackTankUtil.canAbsorbDamage(entity, this.maxBacktankUses)) return 0;
        return amount;
    }
}
