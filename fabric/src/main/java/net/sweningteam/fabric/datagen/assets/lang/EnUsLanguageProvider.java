package net.sweningteam.fabric.datagen.assets.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.registry.ModBlocks;
import net.sweningteam.common.registry.ModEntityTypes;
import net.sweningteam.common.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class EnUsLanguageProvider extends FabricLanguageProvider {
    public EnUsLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us",registryLookup);
    }

    private ResourceLocation getEntityTypeKey(EntityType entity){
        return BuiltInRegistries.ENTITY_TYPE.getKey(entity);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {

        //Items
        translationBuilder.add(ModItems.NIGHT_FURY.get(),"Night Fury");
        translationBuilder.add(ModItems.GOLD_COIN.get(), "Gold Coin");
        translationBuilder.add(ModItems.NIGHT_FURY_EGG.get(), "Night Fury Egg");

        //Blocks
        translationBuilder.add(ModBlocks.NIGHT_FURY_SCALES.get().asItem(), "Night Fury scales");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_BLOCK.get().asItem(), "Block of Gronckel Iron");

        translationBuilder.add(ModBlocks.GRONCKLE_LAVA.get().asItem(), "Gronckel Lava");
        translationBuilder.add(ModBlocks.GRONCKLE_LAVA_BLOCK.get().asItem(), "Block of Gronckel Lava");

        translationBuilder.add(ModBlocks.GRONCKLE_IRON_STAIRS.get().asItem(), "Gronckel Iron Stairs");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_SLAB.get().asItem(), "Gronckel Iron Slab");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_BARS.get().asItem(), "Gronckel Iron Bars");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_DOOR.get().asItem(), "Gronckel Iron Door");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_TRAPDOOR.get().asItem(), "Gronckel Iron Trapdoor");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_CHAIN.get().asItem(), "Gronckel Iron Chain");

        translationBuilder.add(ModBlocks.RAW_GRONCKLE_IRON_BLOCK.get().asItem(), "Block of Raw Gronckel Iron");
        translationBuilder.add(ModBlocks.RAW_GRONCKLE_IRON_STAIRS.get().asItem(), "Raw Gronckel Iron Stairs");
        translationBuilder.add(ModBlocks.RAW_GRONCKLE_IRON_SLAB.get().asItem(), "Raw Gronckel Iron Slab");
        translationBuilder.add(ModBlocks.MOLTEN_GRONCKLE_IRON.get().asItem(), "Molten Gronckel Iron");
        translationBuilder.add(ModBlocks.MOLTEN_GRONCKLE_IRON_BLOCK.get().asItem(), "Block of Molten Gronckel Iron");

        //Entity Types
        translationBuilder.add(getEntityTypeKey(ModEntityTypes.NIGHT_FURY.get()).toLanguageKey("entity"),"Night Fury");
        translationBuilder.add(getEntityTypeKey(ModEntityTypes.PLASMA_BLAST.get()).toLanguageKey("entity"),"Pasma Blast");

        //CreativeModeTaps
        translationBuilder.add("itemGroup." + NotEnoughDragons.MOD_ID + "."+"ned_dragons","NED Dragons");
        translationBuilder.add("itemGroup." + NotEnoughDragons.MOD_ID + "."+"ned_misc_itms","NED Misc Items");
        translationBuilder.add("itemGroup." + NotEnoughDragons.MOD_ID + "."+"ned_blocks","NED Blocks");

        //Villagers
        translationBuilder.add("villager.not_enough_dragons.viking", "Viking");

        //Texts
        translationBuilder.add("text.not_enough_dragons.dragon.get_trust", "Your trust is: ");
        translationBuilder.add("text.not_enough_dragons.dragon.dragon.not_eat", "This Dragon doesn't want to eat right now");
        translationBuilder.add("text.not_enough_dragons.dragon.dragon.not_tame","You can't tamed this Dragon because it is already tamed by {}!");
        translationBuilder.add("text.not_enough_dragons.dragon.dragon.not_tame_no_name", "You can't tame this Dragon because it is already tamed!");

        translationBuilder.add("config.not_enough_dragons.titel", "Not enough Dragons Config");
    }
}
