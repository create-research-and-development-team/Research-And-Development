package org.valkyrienskies.vsrnd.content.Fluids.TitaniumTank;


import com.simibubi.create.content.fluids.tank.FluidTankBlock.Shape;
import com.simibubi.create.content.fluids.tank.FluidTankGenerator;
import com.simibubi.create.foundation.data.AssetLookup;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;
public class TitaniumTankGenerator  extends FluidTankGenerator {

    private String prefix;

    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
                                                BlockState state) {
        Boolean top = state.getValue(TitaniumTankBlock.TOP);
        Boolean bottom = state.getValue(TitaniumTankBlock.BOTTOM);
        Shape shape = state.getValue(TitaniumTankBlock.SHAPE);

        String shapeName = "middle";
        if (top && bottom)
            shapeName = "single";
        else if (top)
            shapeName = "top";
        else if (bottom)
            shapeName = "bottom";

        String modelName = shapeName + (shape == Shape.PLAIN ? "" : "_" + shape.getSerializedName());

        if (!prefix.isEmpty())
            return prov.models()
                    .withExistingParent(prefix + modelName, prov.modLoc("block/titanium_tank/block_" + modelName))
                    .texture("0", prov.modLoc("block/" + prefix + "casing"))
                    .texture("1", prov.modLoc("block/" + prefix + "titanium_tank"))
                    .texture("3", prov.modLoc("block/" + prefix + "titanium_tank_window"))
                    .texture("4", prov.modLoc("block/" + prefix + "casing"))
                    .texture("5", prov.modLoc("block/" + prefix + "titanium_tank_window_single"))
                    .texture("particle", prov.modLoc("block/" + prefix + "titanium_tank"));

        return AssetLookup.partialBaseModel(ctx, prov, modelName);
    }
}
