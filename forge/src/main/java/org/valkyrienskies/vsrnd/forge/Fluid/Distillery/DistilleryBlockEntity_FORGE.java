package org.valkyrienskies.vsrnd.forge.Fluid.Distillery;

import com.simibubi.create.foundation.blockEntity.behaviour.fluid.SmartFluidTankBehaviour;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import org.valkyrienskies.vsrnd.content.Fluids.Distillery.DistilleryBlockEntity;
import org.valkyrienskies.vsrnd.forge.VSCreateForgeRecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class DistilleryBlockEntity_FORGE extends DistilleryBlockEntity {
    public DistilleryTankRecipe_FORGE recipe;
    private static final Object DistilleryRecipeKeys = new Object();
    protected Object getRecipeCacheKey() {
        return DistilleryRecipeKeys;
    }
    public int DistilledTicks = 0;

    private net.minecraft.world.level.material.Fluid oldFluid;
    private int oldAmount;
    public DistilleryBlockEntity_FORGE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private  <C extends Container> boolean filterRecipe(Recipe<C> recipe) {
        return DistilleryTankRecipe_FORGE.match(this, recipe);
    }


    protected List<Recipe<?>> getMatchingRecipes() {
        if (this.inputTank.isEmpty()) {
            return new ArrayList<>(); }

        List<Recipe<?>> list = RecipeFinder.get(getRecipeCacheKey(), level, this::matchStaticFilters);
        return list.stream()
                .filter(this::filterRecipe)
                .sorted((r1, r2) -> r2.getIngredients()
                        .size()
                        - r1.getIngredients()
                        .size())
                .collect(Collectors.toList());
    }

    private boolean matchStaticFilters(Recipe<?> recipe) {
        return recipe.getType() == VSCreateForgeRecipes.FERMENTING.getType();
    }


    @Override
    public void tick() {
        super.tick();


        DistilleryTankRecipe_FORGE TempRecipe;
        List<Recipe<?>> recipes = getMatchingRecipes();
        if (!recipes.isEmpty()) {
            TempRecipe = (DistilleryTankRecipe_FORGE) recipes.get(0);
        } else {
            TempRecipe = null;
        }

        if(TempRecipe!=recipe) {
            DistilledTicks = 0;
            recipe = TempRecipe;
        }

        if (recipe != null) {
            DistilledTicks += 1;
            int duration =  recipe.getProcessingDuration();
            if (duration==0) {
                duration = 10;
            }

            if (DistilledTicks >= duration) {
                DistilledTicks = 0;

                FluidStack result = recipe.getFluidResults().get(0);
                FluidIngredient input = recipe.getFluidIngredients().get(0);

                IFluidHandler availableFluids = (IFluidHandler)this.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY);
                FluidIngredient ingredient = recipe.getFluidIngredients().get(0);

                if (availableFluids != null) {
                    for(int tank = 0; tank < availableFluids.getTanks(); ++tank) {
                        FluidStack fluidStack = availableFluids.getFluidInTank(tank);
                        if (ingredient.test(fluidStack) && ingredient.getRequiredAmount()<=fluidStack.getAmount()) {
                            availableFluids.drain(tank, IFluidHandler.FluidAction.SIMULATE);
                        }
                    }
                }
            }
        } else {
            DistilledTicks = 0;
        }
        //System.out.println(super.tankInventory.getFluid().getTranslationKey());


    }




}