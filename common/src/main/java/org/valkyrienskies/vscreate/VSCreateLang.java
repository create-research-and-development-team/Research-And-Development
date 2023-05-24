package org.valkyrienskies.vscreate;

import com.tterrag.registrate.fabric.LanguageProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.world.level.block.Block;

import java.io.IOException;
import java.util.function.Supplier;

public class VSCreateLang implements DataProvider {
    VSCreateAmericanEnglish americanEnglish;
    VSCreateGerman german;
    VSCreatePirate pirate;
    VSCreateBelgianDutch belgianDutch;

    public VSCreateLang (DataGenerator dataGenerator) {
        americanEnglish = new VSCreateAmericanEnglish(dataGenerator);
        german = new VSCreateGerman(dataGenerator);
        pirate = new VSCreatePirate(dataGenerator);
        belgianDutch = new VSCreateBelgianDutch(dataGenerator);
    }

    @Override
    public void run(HashCache cache) throws IOException {
        americanEnglish.run(cache);
        german.run(cache);
        pirate.run(cache);
        belgianDutch.run(cache);
    }

    @Override
    public String getName() {
        return null;
    }

    private static class VSCreateAmericanEnglish extends LanguageProvider {
        public VSCreateAmericanEnglish(DataGenerator gen) {
            super(gen, VSCreateMod.MOD_ID, "en_us");
        }

        @Override
        protected void addTranslations() {
            addBlock(VSCreateBlocks.TITANIUM_BLOCK, "Titanium Block");
            addBlock(VSCreateBlocks.PROPELLER_BEARING, "Propeller Bearing");
            addItem(VSCreateItems.DENTED_TITANIUM_INGOT, "Dented Titanium Ingot");
            addItem(VSCreateItems.HANDHELD_MECHANICAL_DRILL, "Handheld Mechanical Drill");
            addItem(VSCreateItems.HANDHELD_MECHANICAL_SAW, "Handheld Mechanical Saw");
            addItem(VSCreateItems.RUTILE, "Rutile");
            addItem(VSCreateItems.SODIUM, "Sodium");
            addItem(VSCreateItems.TITANIUM_INGOT, "Titanium Ingot");
            addItem(VSCreateItems.TITANIUM_SHEET, "Titanium Sheet");
        }
    }

    private static class VSCreateGerman extends LanguageProvider {
        public VSCreateGerman(DataGenerator gen) {
            super(gen, VSCreateMod.MOD_ID, "de_de");
        }

        @Override
        protected void addTranslations() {
        }

        private void addSimpleBlock(Supplier<? extends Block> key, String name) {
            addBlock(key, name);
            add(key.get().asItem(), name);
        }
    }

    private static class VSCreatePirate extends LanguageProvider {
        public VSCreatePirate(DataGenerator gen) {
            super(gen, VSCreateMod.MOD_ID, "en_pt");
        }

        @Override
        protected void addTranslations() {
        }

        private void addSimpleBlock(Supplier<? extends Block> key, String name) {
            addBlock(key, name);
            add(key.get().asItem(), name);
        }
    }

    private static class VSCreateBelgianDutch extends LanguageProvider {
        public VSCreateBelgianDutch(DataGenerator gen) {
            super(gen, VSCreateMod.MOD_ID, "nl_be");
        }

        @Override
        protected void addTranslations() {
        }

        private void addSimpleBlock(Supplier<? extends Block> key, String name) {
            addBlock(key, name);
            add(key.get().asItem(), name);
        }
    }
}
