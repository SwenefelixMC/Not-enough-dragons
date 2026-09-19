package net.sweningteam.not_enough_dragons.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.fabric.datagen.assets.ModModelProvider;
import net.sweningteam.not_enough_dragons.fabric.datagen.assets.lang.DeDeLanguageProvider;
import net.sweningteam.not_enough_dragons.fabric.datagen.assets.lang.EnUsLanguageProvider;
import net.sweningteam.not_enough_dragons.fabric.datagen.data.Loot.ModBlockLootTableProvider;
import net.sweningteam.not_enough_dragons.fabric.datagen.data.ModRecipeProvider;
import net.sweningteam.not_enough_dragons.fabric.datagen.data.tags.*;

public class NotEnoughDragonsDatagen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        NotEnoughDragons.LOGGER.info("Starting datagen");
        var pack = fabricDataGenerator.createPack();
        pack.addProvider(EnUsLanguageProvider::new);
        pack.addProvider(DeDeLanguageProvider::new);
        pack.addProvider(ModModelProvider::new);
//        pack.addProvider((FabricDataGenerator.Pack.Factory<ModItemDefinitionProvider>) ModItemDefinitionProvider::new);
//        pack.addProvider((FabricDataGenerator.Pack.Factory<ModBlockModelDefinationsProvider>) ModBlockModelDefinationsProvider::new);

        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModBlockTagProvider::new);

        pack.addProvider(ModBlockLootTableProvider::new);

        pack.addProvider(ModBiomeTagProvider::new);
        pack.addProvider(ModEntityTagProvider::new);
        pack.addProvider(ModPoiTypeTags::new);

        pack.addProvider(ModRecipeProvider::new);
    }
}
