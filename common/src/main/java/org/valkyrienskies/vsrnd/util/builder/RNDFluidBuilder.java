package org.valkyrienskies.vsrnd.util.builder;

import com.google.common.annotations.Beta;
import com.google.common.base.Preconditions;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BlockBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateLangProvider;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.Fluid;
import org.jetbrains.annotations.Nullable;
import org.valkyrienskies.vsrnd.util.fluid.RNDFlowingFluid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
public abstract class RNDFluidBuilder<T extends RNDFlowingFluid, P> extends AbstractBuilder<Fluid, T, P, RNDFluidBuilder<T, P>> {
    protected final String sourceName, bucketName;

    protected final ResourceLocation stillTexture, flowingTexture;
    protected final NonNullFunction<RNDFlowingFluid.Properties, T> fluidFactory;

    @Nullable
    protected Boolean defaultSource, defaultBlock, defaultBucket;

    protected NonNullConsumer<RNDFlowingFluid.Properties> fluidProperties;

    protected @Nullable Supplier<RenderType> layer = null;

    @Nullable
    protected NonNullSupplier<? extends RNDFlowingFluid> source;
    protected final List<TagKey<Fluid>> tags = new ArrayList<>();

    public RNDFluidBuilder(AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback, ResourceLocation stillTexture,
                           ResourceLocation flowingTexture, NonNullFunction<RNDFlowingFluid.Properties, T> fluidFactory) {
        super(owner, parent, "flowing_" + name, callback, Registry.FLUID_REGISTRY);
        this.sourceName = name;
        this.bucketName = name + "_bucket";
        this.stillTexture = stillTexture;
        this.flowingTexture = flowingTexture;
        this.fluidFactory = fluidFactory;
        this.defaultSource = Boolean.TRUE;

        String bucketName = this.bucketName;
        this.fluidProperties = p -> p.bucket(() -> owner.get(bucketName, Registry.ITEM_REGISTRY).get())
                .block(() -> owner.<Block, LiquidBlock>get(name, Registry.BLOCK_REGISTRY).get());
    }

    public static <P> RNDFluidBuilder<RNDFlowingFluid.Flowing, P> create(AbstractRegistrate<?> owner, P parent,
                                                                         String name, BuilderCallback callback, ResourceLocation stillTexture, ResourceLocation flowingTexture) {
        return BuilderHooks.createFluidBuilder(owner, parent, name, callback, stillTexture, flowingTexture,
                RNDFlowingFluid.Flowing::new);
    }

    public RNDFluidBuilder<T, P> fluidProperties(NonNullConsumer<RNDFlowingFluid.Properties> cons) {
        fluidProperties = fluidProperties.andThen(cons);
        return this;
    }

   // public RNDFluidBuilder<T, P> defaultLang() {return lang(RegistrateLangProvider.toEnglishName(sourceName));}

    //public RNDFluidBuilder<T, P> lang(String name) {return lang(flowing -> FluidHelper.getDescriptionId(flowing.getSource()), name);}

    public RNDFluidBuilder<T, P> renderType(Supplier<RenderType> layer) {
        if (this.layer == null) {
            onRegister(this::registerRenderType);
        }
        this.layer = layer;
        return this;
    }

    protected abstract void registerRenderType(T entry);

    public RNDFluidBuilder<T, P> defaultSource() {
        if (this.defaultSource != null) {
            throw new IllegalStateException("Cannot set a default source after a custom source has been created");
        }
        this.defaultSource = true;
        return this;
    }

    public RNDFluidBuilder<T, P> source(NonNullFunction<RNDFlowingFluid.Properties, ? extends RNDFlowingFluid> factory) {
        this.defaultSource = false;
        this.source = NonNullSupplier.lazy(() -> factory.apply(makeProperties()));
        return this;
    }

    public RNDFluidBuilder<T, P> defaultBlock() {
        if (this.defaultBlock != null) {
            throw new IllegalStateException("Cannot set a default block after a custom block has been created");
        }
        this.defaultBlock = true;
        return this;
    }

    public BlockBuilder<LiquidBlock, RNDFluidBuilder<T, P>> block() {
        return block1(LiquidBlock::new);
    }

    public abstract <B extends LiquidBlock> BlockBuilder<B, RNDFluidBuilder<T, P>> block(NonNullBiFunction<NonNullSupplier<? extends T>,
            BlockBehaviour.Properties, ? extends B> factory);

    @SuppressWarnings("unchecked")
    public <B extends LiquidBlock> BlockBuilder<B, RNDFluidBuilder<T, P>> block1(NonNullBiFunction<? extends T,
            BlockBehaviour.Properties, ? extends B> factory) {
        return block((supplier, settings) -> ((NonNullBiFunction<T, BlockBehaviour.Properties, ? extends B>) factory)
                .apply(supplier.get(), settings));
    }

    @Beta
    public RNDFluidBuilder<T, P> noBlock() {
        if (this.defaultBlock == Boolean.FALSE) {
            throw new IllegalStateException("Only one call to block/noBlock per builder allowed");
        }
        this.defaultBlock = false;
        return this;
    }

    public RNDFluidBuilder<T, P> defaultBucket() {
        if (this.defaultBucket != null) {
            throw new IllegalStateException("Cannot set a default bucket after a custom bucket has been created");
        }
        defaultBucket = true;
        return this;
    }

    public ItemBuilder<BucketItem, RNDFluidBuilder<T, P>> bucket() {
        return bucket(BucketItem::new);
    }

    public abstract <I extends BucketItem> ItemBuilder<I, RNDFluidBuilder<T, P>> bucket(NonNullBiFunction<? extends RNDFlowingFluid,
            Item.Properties, ? extends I> factory);

    public RNDFluidBuilder<T, P> noBucket() {
        if (this.defaultBucket == Boolean.FALSE) {
            throw new IllegalStateException("Only one call to bucket/noBucket per builder allowed");
        }
        this.defaultBucket = false;
        return this;
    }

    @SafeVarargs
    public final RNDFluidBuilder<T, P> tag(TagKey<Fluid>... tags) {
        RNDFluidBuilder<T, P> ret = this.tag(ProviderType.FLUID_TAGS, tags);
        if (this.tags.isEmpty()) {
            ret.getOwner().setDataGenerator(ret.sourceName, getRegistryKey(), ProviderType.FLUID_TAGS,
                    prov -> this.tags.stream().map(prov::tag).forEach(p -> p.add(getSource())));
        }
        this.tags.addAll(Arrays.asList(tags));
        return ret;
    }

    @SafeVarargs
    public final RNDFluidBuilder<T, P> removeTag(TagKey<Fluid>... tags) {
        this.tags.removeAll(Arrays.asList(tags));
        return this.removeTag(ProviderType.FLUID_TAGS, tags);
    }

    protected RNDFlowingFluid getSource() {
        NonNullSupplier<? extends RNDFlowingFluid> source = this.source;
        Preconditions.checkNotNull(source, "Fluid has no source block: " + sourceName);
        return source.get();
    }

    protected RNDFlowingFluid.Properties makeProperties() {
        NonNullSupplier<? extends RNDFlowingFluid> source = this.source;
        RNDFlowingFluid.Properties ret =
                new RNDFlowingFluid.Properties(source == null ? null : source::get, asSupplier());
        fluidProperties.accept(ret);
        return ret;
    }

    protected abstract void registerDefaultRenderer(T flowing);
}
