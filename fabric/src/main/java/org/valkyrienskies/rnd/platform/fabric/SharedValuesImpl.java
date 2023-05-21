package org.valkyrienskies.rnd.platform.fabric;

import com.simibubi.create.foundation.item.render.CustomRenderedItemModelRenderer;
import net.fabricmc.fabric.api.client.rendering.v1.BuiltinItemRendererRegistry;
import net.minecraft.world.item.CreativeModeTab;
import org.valkyrienskies.rnd.fabric.VSCreateGroup;
import org.valkyrienskies.rnd.platform.VSCItem;
import org.valkyrienskies.rnd.platform.api.network.PacketChannel;

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

    public static BiConsumer<VSCItem, CustomRenderedItemModelRenderer<?>> customRenderedRegisterer() {
        return BuiltinItemRendererRegistry.INSTANCE::register;
    }

}
