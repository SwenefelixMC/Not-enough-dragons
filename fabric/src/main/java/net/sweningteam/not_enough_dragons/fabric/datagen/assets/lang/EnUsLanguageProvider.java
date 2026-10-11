package net.sweningteam.not_enough_dragons.fabric.datagen.assets.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.registry.ModBlocks;
import net.sweningteam.not_enough_dragons.common.registry.ModEntityTypes;
import net.sweningteam.not_enough_dragons.common.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class EnUsLanguageProvider extends FabricLanguageProvider {
    public EnUsLanguageProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, "en_us",registryLookup);
    }

    private Identifier getEntityTypeKey(EntityType entity){
        return BuiltInRegistries.ENTITY_TYPE.getKey(entity);
    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {

        //Items
        translationBuilder.add(ModItems.NIGHT_FURY.get(),"Night Fury");
        translationBuilder.add(ModItems.GOLD_COIN.get(), "Gold Coin");
        translationBuilder.add(ModItems.NIGHT_FURY_EGG.get(), "Night Fury Egg");

        translationBuilder.add(ModItems.MOLTEN_GROCKLE_IRON.get(),"Molten Gronckle Iron");
        translationBuilder.add(ModItems.MOLTEN_RAW_GROCKLE_IRON.get(),"Molten Raw Gronckle Iron");
        translationBuilder.add(ModItems.RAW_GROCKLE_IRON.get(),"Raw Gronckle Iron");
        translationBuilder.add(ModItems.GRONCKLE_IRON_INGOT.get(),"Gronckle Iron Ingot");
        translationBuilder.add(ModItems.GRONCKLE_IRON_NUGGET.get(),"Gronckle Iron Nugget");

        translationBuilder.add(ModItems.GRONCKLE.get(),"Gronckle");
        translationBuilder.add(ModItems.GRONCKLE_EGG.get(),"Gronckle Egg");

        translationBuilder.add(ModItems.MOLTEN_DRAGON_PROVED_STEEL.get(),"Molten Dragon Proved Steel");
        translationBuilder.add(ModItems.DRAGON_PROVED_STEEL_SCRAPS.get(),"Dragon Proved Steel Scraps");
        translationBuilder.add(ModItems.DRAGON_PROVED_STEEL_INGOT.get(),"Dragon Proved Steel Ingot");

        translationBuilder.add(ModBlocks.DRAGON_NIP.get().asItem(),"Dragon Nip");

        translationBuilder.add(ModItems.GRONCKLE_IRON_SWORD.get(),"Gronckle Iron Sword");
        translationBuilder.add(ModItems.GRONCKLE_IRON_SHOVEL.get(),"Gronckle Iron Shovel");
        translationBuilder.add(ModItems.GRONCKLE_IRON_PICKAXE.get(),"Gronckle Iron Pickaxe");
        translationBuilder.add(ModItems.GRONCKLE_IRON_AXE.get(),"Gronckle Iron Axe");
        translationBuilder.add(ModItems.GRONCKLE_IRON_HOE.get(),"Gronckle Iron Hoe");
        //Blocks
        translationBuilder.add(ModBlocks.NIGHT_FURY_SCALES.get().asItem(), "Night Fury scales");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_BLOCK.get().asItem(), "Block of Gronckle Iron");

        translationBuilder.add(ModBlocks.GRONCKLE_IRON_STAIRS.get().asItem(), "Gronckle Iron Stairs");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_SLAB.get().asItem(), "Gronckle Iron Slab");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_BARS.get().asItem(), "Gronckle Iron Bars");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_DOOR.get().asItem(), "Gronckle Iron Door");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_TRAPDOOR.get().asItem(), "Gronckle Iron Trapdoor");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_CHAIN.get().asItem(), "Gronckle Iron Chain");

        translationBuilder.add(ModBlocks.RAW_GRONCKLE_IRON_BLOCK.get().asItem(), "Block of Raw Gronckle Iron");
        translationBuilder.add(ModBlocks.RAW_GRONCKLE_IRON_STAIRS.get().asItem(), "Raw Gronckle Iron Stairs");
        translationBuilder.add(ModBlocks.RAW_GRONCKLE_IRON_SLAB.get().asItem(), "Raw Gronckle Iron Slab");
        translationBuilder.add(ModBlocks.MOLTEN_GRONCKLE_IRON_LAVA.get().asItem(), "Molten Gronckle Iron");
        translationBuilder.add(ModBlocks.MOLTEN_GRONCKLE_IRON_BLOCK.get().asItem(), "Block of Molten Gronckle Iron");

        translationBuilder.add(ModBlocks.DRAGON_PROVED_STEEL_BLOCK.get().asItem(), "Dragon Proved Steel Block");
        translationBuilder.add(ModBlocks.DRAGON_PROVED_STEEL_STAIRS.get().asItem(), "Dragon Proved Steel Stairs");
        translationBuilder.add(ModBlocks.DRAGON_PROVED_STEEL_SLAB.get().asItem(), "Dragon Proved Steel Slab");

        translationBuilder.add(ModBlocks.GRONCKLE_SCALES.get().asItem(), "Gronckle scales");

        translationBuilder.add(ModBlocks.DRAGON_MANUAL.get().asItem(), "Dragon Manual");

        //Entity Types
        translationBuilder.add(getEntityTypeKey(ModEntityTypes.NIGHT_FURY.get()).toLanguageKey("entity"),"Night Fury");
        translationBuilder.add(getEntityTypeKey(ModEntityTypes.PLASMA_BLAST.get()).toLanguageKey("entity"),"Plasma Blast");

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

        //Keymappings
        translationBuilder.add("key.not_enough_dragons.ability.dragon_main_attack", "Dragon Main Attack");
    }
}
