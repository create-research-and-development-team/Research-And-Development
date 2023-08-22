package org.valkyrienskies.vsrnd.forge;


import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.valkyrienskies.vsrnd.VSCreateMod;

import java.util.Collection;

public abstract class VSCreateGroupBase extends CreativeModeTab {

	public VSCreateGroupBase(String id) {
		super(VSCreateMod.MOD_ID + "." + id);
	}

	@Override
	@OnlyIn(Dist.CLIENT)
	public void fillItemList(NonNullList<ItemStack> items) {
		addItems(items, true);
		addBlocks(items);
		addItems(items, false);
	}

	@OnlyIn(Dist.CLIENT)
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

	@OnlyIn(Dist.CLIENT)
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
		return null;
	}

	protected Collection<RegistryEntry<Block>> getBlocks() {
		return null;
	}

}
//    public static final CreativeModeTab GROUP = new CreativeModeTab(MOD_ID) {
//        @Override
//        public ItemStack makeIcon() {
//            return AllClockworkBlocks.PROPELLOR_BEARING.asStack();
//        }
//
//        @Override
//        public void fillItemList(NonNullList<ItemStack> list) {
//            list.addAll(Arrays.asList(
//                    AllClockworkBlocks.PROPELLOR_BEARING.asStack()
//            ));
//        }
//    };
//
//    public static void register() {
//        ClockWorkModForge.REGISTRATE.creativeModeTab(() -> GROUP, "Valkyrien Clockwork");
//    }
//
//}
