package net.sweningteam.not_enough_dragons.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.datagen.ModDragonFoodComponentConfiguration;
import net.sweningteam.not_enough_dragons.common.entity.villager.ModTradeSets;
import net.sweningteam.not_enough_dragons.common.entity.villager.ModTrades;
import net.sweningteam.not_enough_dragons.common.registry.ModRegistries;
import net.sweningteam.not_enough_dragons.common.worldgen.structures.placements.ModStrucutreSetsConfiguration;
import net.sweningteam.not_enough_dragons.common.worldgen.features.ModFeatureConfiguration;
import net.sweningteam.not_enough_dragons.common.worldgen.features.ModPlacedFeatureConfiguration;
import net.sweningteam.not_enough_dragons.common.worldgen.structures.ModStructuresConfiguration;
import net.sweningteam.not_enough_dragons.common.worldgen.structures.ModTemplatePoolsConfiguration;
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
        pack.addProvider(ModRegistryDataProvider::new);

        pack.addProvider(ModVillagerTradeTagProvider::new);

        pack.addProvider(ModDragonFoodComponentTagProvider::new);
    }

    @Override
    public void buildRegistry(RegistrySetBuilder registryBuilder) {
        registryBuilder.add(Registries.VILLAGER_TRADE, ModTrades::bootstrap);
        registryBuilder.add(Registries.TRADE_SET, ModTradeSets::bootstrap);
        registryBuilder.add(Registries.FEATURE, ModFeatureConfiguration::boostrap);
        registryBuilder.add(Registries.PLACED_FEATURE, ModPlacedFeatureConfiguration::boostrap);
        registryBuilder.add(Registries.STRUCTURE, ModStructuresConfiguration::boostrap);
        registryBuilder.add(Registries.TEMPLATE_POOL, ModTemplatePoolsConfiguration::bootstrap);
        registryBuilder.add(Registries.STRUCTURE_SET, ModStrucutreSetsConfiguration::bootstrap);
        registryBuilder.add(ModRegistries.DRAGON_FOOD_COMPONENT, ModDragonFoodComponentConfiguration::bootstrap);
    }
}
