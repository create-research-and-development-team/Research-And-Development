package org.valkyrienskies.vsrnd;

import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import net.minecraft.resources.ResourceLocation;
import org.valkyrienskies.vsrnd.util.builder.BuilderHooks;
import org.valkyrienskies.vsrnd.util.builder.RNDFluidBuilder;
import org.valkyrienskies.vsrnd.util.fluid.RNDFlowingFluid;
import org.valkyrienskies.vsrnd.util.fluid.VirtualFluid;

import static java.util.Map.entry;

public class RNDRegistrate extends AbstractRegistrate<RNDRegistrate>  {

    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(VSCreateMod.MOD_ID);

    protected RNDRegistrate(String modid) {super(modid);}

    public <T extends RNDFlowingFluid> RNDFluidBuilder<T, RNDRegistrate> virtualFluid(String name, NonNullFunction<RNDFlowingFluid.Properties, T> factory) {
        return entry(name, c -> BuilderHooks.createFluidBuilder(self(), self(), name, c, VSCreateMod.asResource("fluid/" + name + "_still"),
                VSCreateMod.asResource("fluid/" + name + "_flow"), factory));
    }

    public <T extends RNDFlowingFluid> RNDFluidBuilder<T, RNDRegistrate> virtualFluid(String name, ResourceLocation still,
                                                                                      ResourceLocation flow, NonNullFunction<RNDFlowingFluid.Properties, T> factory) {
        return entry(name, c -> BuilderHooks.createFluidBuilder(self(), self(), name, c, still, flow, factory));
    }

    public RNDFluidBuilder<VirtualFluid, RNDRegistrate> virtualFluid(String name) {
        return entry(name, c -> BuilderHooks.createFluidBuilder(self(), self(), name, c, VSCreateMod.asResource("fluid/" + name + "_still"),
                VSCreateMod.asResource("fluid/" + name + "_flow"), VirtualFluid::new));
    }

    public RNDFluidBuilder<VirtualFluid, RNDRegistrate> virtualFluid(String name, ResourceLocation still, ResourceLocation flow) {
        return entry(name, c -> BuilderHooks.createFluidBuilder(self(), self(), name, c, still, flow, VirtualFluid::new));
    }

    public RNDFluidBuilder<RNDFlowingFluid.Flowing, RNDRegistrate> standardFluid(String name) {
        return standardFluid(name, VSCreateMod.asResource("fluid/" + name + "_still"), VSCreateMod.asResource("fluid/" + name + "_flow"));
    }

    public RNDFluidBuilder<RNDFlowingFluid.Flowing, RNDRegistrate> standardFluid(String name, ResourceLocation stillTexture,
                                                                                 ResourceLocation flowingTexture) {
        return standardFluid(this, name, stillTexture, flowingTexture);
    }

    public RNDFluidBuilder<RNDFlowingFluid.Flowing, RNDRegistrate> standardFluid(RNDRegistrate parent, String name, ResourceLocation stillTexture,
                                                                                 ResourceLocation flowingTexture) {
        return entry(name, callback -> RNDFluidBuilder.create(this, parent, name, callback, stillTexture, flowingTexture));
    }
}
