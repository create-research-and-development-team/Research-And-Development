package org.valkyrienskies.vsrnd.forge.JEI.Categories.animations;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.compat.jei.category.animations.AnimatedPress;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.core.Direction;
import org.valkyrienskies.vsrnd.VSCreateBlocks;
import org.valkyrienskies.vsrnd.forge.VSCreateForgeBlocks;

public class AnimatedFermentingTank extends AnimatedKinetics {

    @Override
    public void draw(PoseStack matrixStack, int xOffset, int yOffset) {
        matrixStack.pushPose();
        matrixStack.translate(xOffset, yOffset, 200);
        matrixStack.mulPose(Vector3f.XP.rotationDegrees(-15.5f));
        matrixStack.mulPose(Vector3f.YP.rotationDegrees(22.5f));
        int scale = 23;



        blockElement(VSCreateForgeBlocks.FERMENTING_TANK_FORGE.getDefaultState())
                .scale(scale)
                .render(matrixStack);

        matrixStack.popPose();
    }


}
