package org.valkyrienskies.vscreate;

import com.mojang.logging.LogUtils;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.valkyrienskies.vscreate.platform.SharedValues;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class VSCreateMod {
    public static final String MOD_ID = "vscreate";

    // versioning
    public static final int BUILD_VERSION = 1;
    public static final int NETWORK_VERSION = 1;
    public static final String NETWORK_VERSION_STR = String.valueOf(NETWORK_VERSION);

    public static final ResourceLocation NETWORK_CHANNEL = VSCreateMod.asResource("main");


    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(VSCreateMod.MOD_ID);
    public static final CreativeModeTab BASE_CREATIVE_TAB = SharedValues.creativeTab();
    public static final Logger MIXIN_LOGGER = LoggerFactory.getLogger("VSCreateMixins");
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        VSCreateContraptions.init();
        VSCreatePackets.init();
        skipSwingItems.addAll(List.of(
                VSCreateItems.HANDHELD_MECHANICAL_DRILL.get(),
                VSCreateItems.HANDHELD_MECHANICAL_SAW.get()
        ));
    }

    public static void initClient() {


    }

    private static final Set<Item> skipSwingItems = new HashSet<>();

    public static Set<Item> getSkipSwingItems() {
        return skipSwingItems;
    }

    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
