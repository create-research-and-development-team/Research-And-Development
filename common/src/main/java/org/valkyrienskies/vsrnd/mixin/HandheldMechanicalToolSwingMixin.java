package org.valkyrienskies.vsrnd.mixin;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.valkyrienskies.vsrnd.VSCreateMod;

@Mixin(LivingEntity.class)
public abstract class HandheldMechanicalToolSwingMixin {
    @Shadow
    public abstract ItemStack getMainHandItem();

    @Inject(method = "swing(Lnet/minecraft/world/InteractionHand;Z)V", at = @At("HEAD"), cancellable = true)
    public void onSwing(InteractionHand hand, boolean updateSelf, CallbackInfo ci) {
        if (VSCreateMod.getSkipSwingItems().contains(getMainHandItem().getItem())) {
            ci.cancel();
        }
    }
}
