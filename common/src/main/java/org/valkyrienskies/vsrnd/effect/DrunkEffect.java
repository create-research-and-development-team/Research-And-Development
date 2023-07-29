package org.valkyrienskies.vsrnd.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class DrunkEffect extends MobEffect {

    public DrunkEffect(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
        if (!pLivingEntity.level.isClientSide()) {
            float random = (float) Math.random() * 10;
            pLivingEntity.setYBodyRot(pLivingEntity.yBodyRot+random);
        }

        super.applyEffectTick(pLivingEntity, pAmplifier);
    }


}
