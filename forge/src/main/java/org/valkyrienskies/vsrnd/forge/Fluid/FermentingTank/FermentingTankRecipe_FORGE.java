package org.valkyrienskies.vsrnd.forge.Fluid.FermentingTank;


import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import org.valkyrienskies.vsrnd.forge.VSCreateForgeRecipes;

public class FermentingTankRecipe_FORGE extends ProcessingRecipe<SmartInventory> {


	public FermentingTankRecipe_FORGE(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
		this(VSCreateForgeRecipes.FERMENTING, params);
	}

	public FermentingTankRecipe_FORGE(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
		super(typeInfo, params);
	}

	static public boolean match(FermentingTankBlockEntity_FORGE fermentTank, Recipe<?> recipe) {

		if (recipe instanceof FermentingTankRecipe_FORGE) {
			return ((FermentingTankRecipe_FORGE) recipe).getFluidIngredients()
														.get(0)
														.test(fermentTank.getTankInventory().getFluid());
		}

		return false;
	}

	@Override
	protected int getMaxInputCount() {
		return 0;
	}

	@Override
	protected int getMaxOutputCount() {
		return 1;
	}

	@Override
	protected boolean canRequireHeat() {
		return false;
	}

	@Override
	protected int getMaxFluidInputCount() {
		return 1;
	}

	@Override
	protected int getMaxFluidOutputCount() {
		return 1;
	}

	public FluidIngredient getRequiredFluid() {
		if (fluidIngredients.isEmpty()) {
			throw new IllegalStateException("Filling Recipe: " + id.toString() + " has no fluid ingredient!");
		}
		return fluidIngredients.get(0);
	}

	@Override
	public boolean matches(SmartInventory container, Level level) {
		return false;
	}
}
