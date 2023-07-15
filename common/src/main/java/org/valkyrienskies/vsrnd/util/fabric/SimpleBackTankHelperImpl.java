package org.valkyrienskies.vsrnd.util.fabric;

import com.simibubi.create.content.equipment.armor.BacktankUtil;
import net.fabricmc.fabric.api.item.v1.CustomDamageHandler;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Consumer;

public class SimpleBackTankHelperImpl {
    public static Item.Properties getProperties(Item.Properties properties, int maxDamage, int maxBackTankUses) {
        return ((FabricItemSettings)properties)
                .maxDamage(maxDamage)
                .customDamage(new BackTankDamageHandler(maxBackTankUses));
    }

    private static class BackTankDamageHandler implements CustomDamageHandler {
        private final int maxBacktankUses;
        public BackTankDamageHandler(int maxBacktankUses) {
            this.maxBacktankUses = maxBacktankUses;
        }

        @Override
        public int damage(ItemStack stack, int amount, LivingEntity entity, Consumer<LivingEntity> breakCallback) {
            if(BacktankUtil.canAbsorbDamage(entity, this.maxBacktankUses)) return 0;
            return amount;
        }
    }

}
