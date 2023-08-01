package org.valkyrienskies.vsrnd.forge.Fluid.Distillery;

import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.content.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.blockEntity.behaviour.filtering.FilteringBehaviour;
import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.recipe.DummyCraftingContainer;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.gametest.framework.TestReporter;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.Level;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.lwjgl.system.CallbackI;
import org.valkyrienskies.vsrnd.content.Fluids.Distillery.DistilleryBlockEntity;
import org.valkyrienskies.vsrnd.forge.Fluid.FermentingTank.FermentingTankBlockEntity_FORGE;
import org.valkyrienskies.vsrnd.forge.Fluid.FermentingTank.FermentingTankRecipe_FORGE;
import org.valkyrienskies.vsrnd.forge.VSCreateForgeRecipes;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.processing.basin.BasinRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.item.SmartInventory;
import com.simibubi.create.foundation.recipe.IRecipeTypeInfo;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.valkyrienskies.vsrnd.forge.VSCreateForgeRecipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class DistilleryTankRecipe_FORGE extends ProcessingRecipe<SmartInventory> {



    public static boolean match(DistilleryBlockEntity_FORGE distillery, Recipe<?> recipe) {
        FilteringBehaviour filter = distillery.getFilter();
        if (filter == null) {
            return false;
        } else {
            if (recipe instanceof DistilleryTankRecipe_FORGE) {
                DistilleryTankRecipe_FORGE distillRecipe = (DistilleryTankRecipe_FORGE)recipe;
                boolean filterTest = filter.test((FluidStack)distillRecipe.getFluidResults().get(0));
                return filterTest ? TestRecipe(distillery, distillRecipe) : false;
            }

            return false;
        }
    }

    protected static boolean TestRecipe(DistilleryBlockEntity_FORGE distillery, DistilleryTankRecipe_FORGE recipe) {
        IFluidHandler availableFluids = (IFluidHandler)distillery.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
        FluidIngredient ingredient = recipe.getFluidIngredients().get(0);

        if (availableFluids != null) {
            for(int tank = 0; tank < availableFluids.getTanks(); ++tank) {
                FluidStack fluidStack = availableFluids.getFluidInTank(tank);
                if (ingredient.test(fluidStack) && ingredient.getRequiredAmount()<=fluidStack.getAmount()) {
                    return true;
                }
            }
        }
        return false;
    }

    public DistilleryTankRecipe_FORGE(IRecipeTypeInfo typeInfo, ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(typeInfo, params);
    }


    public DistilleryTankRecipe_FORGE(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        this(VSCreateForgeRecipes.DISTILLING, params);
    }

    @Override
    protected int getMaxInputCount() {
        return 0;
    }

    public FluidIngredient getRequiredFluid() {
        if (fluidIngredients.isEmpty())
            throw new IllegalStateException("Filling Recipe: " + id.toString() + " has no fluid ingredient!");
        return fluidIngredients.get(0);
    }
    @Override
    protected int getMaxFluidInputCount() {
        return 1;
    }

    @Override
    protected int getMaxFluidOutputCount() {
        return 1;
    }

    @Override
    protected int getMaxOutputCount() {
        return 0;
    }

    @Override
    protected boolean canRequireHeat() {
        return true;
    }

    @Override
    public boolean matches(SmartInventory container, Level level) {
        return false;
    }
}

