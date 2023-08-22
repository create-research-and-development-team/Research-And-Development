package org.valkyrienskies.vsrnd.fabric;

import com.tterrag.registrate.util.entry.RegistryEntry;
import io.github.fabricators_of_create.porting_lib.util.ItemGroupUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import org.valkyrienskies.vsrnd.VSCreateBlocks;

import java.util.Arrays;
import java.util.Collection;

import static org.valkyrienskies.vsrnd.RNDRegistrate.REGISTRATE;
import static org.valkyrienskies.vsrnd.VSCreateMod.MOD_ID;

public abstract class VSCreateGroupBase extends CreativeModeTab {

	public static final CreativeModeTab GROUP = new CreativeModeTab(10, MOD_ID) {
		@Override
		public ItemStack makeIcon() {
			return VSCreateBlocks.TITANIUM_BLOCK.asStack();
		}

		@Override
		public void fillItemList(NonNullList<ItemStack> list) {
			list.addAll(Arrays.asList(
					VSCreateBlocks.TITANIUM_BLOCK.asStack()
									 ));
		}
	};

	public VSCreateGroupBase(String id) {
		super(ItemGroupUtil.expandArrayAndGetId(), MOD_ID + "." + id);
	}

	public static void register() {
		REGISTRATE.creativeModeTab(() -> GROUP, "Create: RnD");
	}

	@Override
	@Environment(EnvType.CLIENT)
	public void fillItemList(NonNullList<ItemStack> items) {
		addItems(items, true);
		addBlocks(items);
		addItems(items, false);
	}

	@Environment(EnvType.CLIENT)
	public void addItems(NonNullList<ItemStack> items, boolean specialItems) {
		ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();

		for (RegistryEntry<? extends Item> entry : getItems()) {
			Item item = entry.get();
            if (item instanceof BlockItem) {
                continue;
            }
			ItemStack stack = new ItemStack(item);
			BakedModel model = itemRenderer.getModel(stack, null, null, 0);
            if (model.isGui3d() != specialItems) {
                continue;
            }
			item.fillItemCategory(this, items);
		}
	}

	@Environment(EnvType.CLIENT)
	public void addBlocks(NonNullList<ItemStack> items) {
		for (RegistryEntry<? extends Block> entry : getBlocks()) {
			Block def = entry.get();
			Item item = def.asItem();
            if (item != Items.AIR) {
                def.fillItemCategory(this, items);
            }
		}
	}

	protected Collection<RegistryEntry<Item>> getItems() {
		return REGISTRATE
				.getAll(Registry.ITEM_REGISTRY);
	}

	protected Collection<RegistryEntry<Block>> getBlocks() {
		return REGISTRATE
				.getAll(Registry.BLOCK_REGISTRY);
	}
}
