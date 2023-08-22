package org.valkyrienskies.vsrnd.platform.forge;

import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import com.simibubi.create.foundation.item.render.SimpleCustomRenderer;
import net.minecraft.world.item.CreativeModeTab;
import org.valkyrienskies.vsrnd.forge.VSCreateGroup;
import org.valkyrienskies.vsrnd.forge.mixin.accessors.ItemAccessor;
import org.valkyrienskies.vsrnd.platform.VSCItem;
import org.valkyrienskies.vsrnd.platform.api.network.PacketChannel;

import java.util.function.BiConsumer;

public class SharedValuesImpl {
	private static final CreativeModeTab TAB = new VSCreateGroup();
	private static final PacketChannel CHANNEL = new PacketChannelImpl();

	public static CreativeModeTab creativeTab() {
		return TAB;
	}

	public static PacketChannel getPacketChannel() {
		return CHANNEL;
	}

	public static BiConsumer<VSCItem, CustomRenderedItemModelRenderer> customRenderedRegisterer() {
		return (item, renderer) -> ((ItemAccessor) item).setRenderProperties(SimpleCustomRenderer.create(item,
																										 renderer));
	}

}
