package org.valkyrienskies.vsrnd.util.builder;

import com.simibubi.create.foundation.data.VirtualFluidBuilder;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.fabric.SimpleFlowableFluid;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import org.valkyrienskies.vsrnd.util.fluid.RNDFlowingFluid;

public class BuilderHooks {
    @ExpectPlatform
    public static <T extends RNDFlowingFluid, P> RNDFluidBuilder<T, P> createFluidBuilder(
            AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceLocation stillTexture,
            ResourceLocation flowingTexture, NonNullFunction<RNDFlowingFluid.Properties, T> fluidFactory
    ) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <T extends SimpleFlowableFluid, P> VirtualFluidBuilder<T, P> create(AbstractRegistrate<?> owner, P parent,
                                                                                      String name, BuilderCallback callback, ResourceLocation stillTexture, ResourceLocation flowingTexture,
                                                                                      NonNullFunction<RNDFlowingFluid.Properties, T> fluidFactory
    ) {
        throw new AssertionError();
    }
}
