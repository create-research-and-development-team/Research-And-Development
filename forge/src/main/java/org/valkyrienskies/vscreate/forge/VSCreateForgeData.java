package org.valkyrienskies.vscreate.forge;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.valkyrienskies.vscreate.VSCreateLang;
import org.valkyrienskies.vscreate.VSCreateMod;

@Mod.EventBusSubscriber(modid = VSCreateMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class VSCreateForgeData {
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();

        generator.addProvider(new VSCreateLang(generator));
    }
}
