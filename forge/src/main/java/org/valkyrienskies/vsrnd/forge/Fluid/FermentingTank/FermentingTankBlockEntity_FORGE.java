package org.valkyrienskies.vsrnd.forge.Fluid.FermentingTank;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.processing.basin.BasinBlockEntity;
import com.simibubi.create.foundation.recipe.RecipeFinder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;
import org.valkyrienskies.vsrnd.content.Fluids.FermentingTank.FermentingTankBlockEntity;
import org.valkyrienskies.vsrnd.forge.VSCreateForgeRecipes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



public class FermentingTankBlockEntity_FORGE extends FermentingTankBlockEntity {

    private static final Object FermentingRecipesKey = new Object();
    protected Object getRecipeCacheKey() {
        return FermentingRecipesKey;
    }
    private int FermentedTicks = 0;
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
        if (blockEntity instanceof FermentingTankBlockEntity)
            return (FermentingTankBlockEntity_FORGE) blockEntity;
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        if (isController()) {
                List<Recipe<?>> recipes = getMatchingRecipes();
                if (!recipes.isEmpty()) {
                    FermentedTicks += 1;
                    if (FermentedTicks >= 200) {
                        FermentingTankRecipe_FORGE recipe = (FermentingTankRecipe_FORGE) recipes.get(0);
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
}
