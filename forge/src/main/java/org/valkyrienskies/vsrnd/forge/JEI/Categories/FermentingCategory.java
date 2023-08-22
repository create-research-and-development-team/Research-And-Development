package org.valkyrienskies.vsrnd.forge.JEI.Categories;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import mezz.jei.api.forge.ForgeTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fluids.FluidStack;
import org.valkyrienskies.vsrnd.forge.Fluid.FermentingTank.FermentingTankRecipe_FORGE;
import org.valkyrienskies.vsrnd.forge.JEI.Categories.animations.AnimatedFermentingTank;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class FermentingCategory extends CreateRecipeCategory<FermentingTankRecipe_FORGE> implements IRecipeCategory<FermentingTankRecipe_FORGE> {


	private final AnimatedFermentingTank tank = new AnimatedFermentingTank();

	public FermentingCategory(Info<FermentingTankRecipe_FORGE> info) {
		super(info);
	}


	@Override
	public void setRecipe(IRecipeLayoutBuilder builder, FermentingTankRecipe_FORGE recipe, IFocusGroup focuses) {

		FluidIngredient fluidIngredient = recipe.getFluidIngredients().get(0);
		builder
				.addSlot(RecipeIngredientRole.INPUT, 50, 40)
				.setBackground(getRenderedSlot(), -1, -1)
				.addIngredients(ForgeTypes.FLUID_STACK,
								withImprovedVisibility(fluidIngredient.getMatchingFluidStacks()));

		FluidStack fluidResult = recipe.getFluidResults().get(0);


		builder
				.addSlot(RecipeIngredientRole.OUTPUT, 130, 40)
				.setBackground(getRenderedSlot(), -1, -1)
				.addIngredient(ForgeTypes.FLUID_STACK, withImprovedVisibility(fluidResult));

	}


	public void draw(FermentingTankRecipe_FORGE recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
		AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 124, 10);

//        AllGuiTextures shadow = noHeat ? AllGuiTextures.JEI_SHADOW : AllGuiTextures.JEI_LIGHT;
//        shadow.render(matrixStack, 81, 58 + (noHeat ? 10 : 30));


//        AllGuiTextures heatBar = noHeat ? AllGuiTextures.JEI_NO_HEAT_BAR : AllGuiTextures.JEI_HEAT_BAR;
//        heatBar.render(matrixStack, 4, 80);
		Minecraft.getInstance().font.draw(matrixStack, recipe.getProcessingDuration() + " ticks", 114,
										  0, 15237888);

		tank.draw(matrixStack, 80, 40);
	}
}
