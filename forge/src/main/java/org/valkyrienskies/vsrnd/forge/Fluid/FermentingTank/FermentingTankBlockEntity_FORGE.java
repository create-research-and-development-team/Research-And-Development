package org.valkyrienskies.vsrnd.forge.Fluid.FermentingTank;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.crafting.conditions.FalseCondition;
import net.minecraftforge.fluids.FluidStack;
import org.valkyrienskies.vsrnd.content.Fluids.FermentingTank.FermentingTankBlockEntity;
import org.valkyrienskies.vsrnd.forge.VSCreateForgeRecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class FermentingTankBlockEntity_FORGE extends FermentingTankBlockEntity {
    public FermentingTankRecipe_FORGE recipe;
    private static final Object FermentingRecipesKey = new Object();
    protected Object getRecipeCacheKey() {
        return FermentingRecipesKey;
    }
    public int FermentedTicks = 0;

    private net.minecraft.world.level.material.Fluid oldFluid;
    private int oldAmount;
    public FermentingTankBlockEntity_FORGE(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    private  <C extends Container> boolean filterRecipe(Recipe<C> recipe) {
        return FermentingTankRecipe_FORGE.match(this, recipe);
    }


    protected List<Recipe<?>> getMatchingRecipes() {
        if (this.tankInventory.getFluid().isEmpty()) {
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
    public FermentingTankBlockEntity_FORGE getControllerBE() {
        if (isController())
            return this;
        BlockEntity blockEntity = level.getBlockEntity(controller);
        if (blockEntity instanceof FermentingTankBlockEntity_FORGE)
            return (FermentingTankBlockEntity_FORGE) blockEntity;
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        if (isController()) {

            if (recipe != null) {
                FermentedTicks += 1;
                int duration =  recipe.getProcessingDuration();
                if (duration==0) {
                    duration = 200;
                }

                if (FermentedTicks >= duration) {
                    FermentedTicks = 0;
                    FluidStack result = recipe.getFluidResults().get(0);
                    result.setAmount(super.tankInventory.getFluidAmount());
                    super.tankInventory.setFluid(result);
                }
            } else {
                FermentedTicks = 0;
            }
            //System.out.println(super.tankInventory.getFluid().getTranslationKey());


        }
    }

    @Override
    protected void onFluidStackChanged(FluidStack newFluidStack) {
        super.onFluidStackChanged(newFluidStack);


        if (isController()) {
            List<Recipe<?>> recipes = getMatchingRecipes();
            if (!recipes.isEmpty()) {
                recipe = (FermentingTankRecipe_FORGE) recipes.get(0);
            } else {
                recipe = null;
            }

            // TODO: FIX fluid mult shit
            if (oldFluid != null) {
                if (newFluidStack.getFluid() == oldFluid) {
                    float mult;
                    if (oldAmount == 0) {
                        mult = 1f;
                    } else {
                        float A = newFluidStack.getAmount();
                        float B = oldAmount;
                        float C = (A-B)/B;
                        mult = Math.max(1f-C, 0f);
                    }

                    FermentedTicks *= mult;
                } else {
                    FermentedTicks = 0;
                }


            }
            oldFluid = newFluidStack.getFluid();
            oldAmount = newFluidStack.getAmount();
        }

    }

    @Override
    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking) {
        super.addToGoggleTooltip(tooltip,isPlayerSneaking);

        FermentingTankBlockEntity_FORGE contr = getControllerBE();
        if (contr != null && contr.recipe != null) {


            Lang.text("Fermentation Process")
                    .style(ChatFormatting.GRAY)
                    .forGoggles(tooltip, 1);

            int max = contr.recipe.getProcessingDuration();
            if (max==0) {
                max = 200;
            }
            float percentage = 100*contr.FermentedTicks/max ;


            String amount = percentage + "%";
            Lang.text(amount)
                    .style(ChatFormatting.LIGHT_PURPLE)
                    .forGoggles(tooltip, 1);
        }
        return true;

    }
}
