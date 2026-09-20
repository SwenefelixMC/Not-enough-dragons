package net.sweningteam.not_enough_dragons.fabric.datagen.assets.lang;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.core.HolderLookup;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.registry.ModBlocks;
import net.sweningteam.not_enough_dragons.common.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class DeDeLanguageProvider extends FabricLanguageProvider {
    public DeDeLanguageProvider(FabricPackOutput packOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(packOutput, "de_de", registryLookup);
    }
//    public DeDeLanguageProvider(PackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
//        super(dataOutput, "de_de", registryLookup);
//    }

    @Override
    public void generateTranslations(HolderLookup.Provider provider, TranslationBuilder translationBuilder) {
        // Items
        translationBuilder.add(ModItems.NIGHT_FURY.get(),"Nachtschatten");
        translationBuilder.add(ModItems.GOLD_COIN.get(), "Goldmünze");
        translationBuilder.add(ModItems.NIGHT_FURY_EGG.get(), "Nachtschatten-Ei");

        translationBuilder.add(ModItems.MOLTEN_GROCKLE_IRON.get(),"Geschmolzenes Gronckel-Eisen");
        translationBuilder.add(ModItems.MOLTEN_RAW_GROCKLE_IRON.get(),"Geschmolzenes rohes Gronckel-Eisen");
        translationBuilder.add(ModItems.RAW_GROCKLE_IRON.get(),"Rohes Gronkel-Eisen");
        translationBuilder.add(ModItems.GRONCKLE_IRON_INGOT.get(),"Gronckel-Eisenbarren");
        translationBuilder.add(ModItems.GRONCKLE_IRON_NUGGET.get(),"Gronckel-Eisenklumpen");

        translationBuilder.add(ModItems.GRONCKLE.get(),"Gronckel");
        translationBuilder.add(ModItems.GRONCKLE_EGG.get(),"Gronckel-Ei");

        translationBuilder.add(ModItems.MOLTEN_DRAGON_PROVED_STEAL.get(),"Geschmolzener drachensicherer Stahl");
        translationBuilder.add(ModItems.DRAGON_PROVED_STEAL_SCRAPS.get(),"Drachensichere Stahlplatten");
        translationBuilder.add(ModItems.DRAGON_PROVED_STEAL_INGOT.get(),"Drachensicherer Stahlbarren");

        translationBuilder.add(ModBlocks.DRAGON_NIP.get().asItem(),"Drachenminze");

        translationBuilder.add(ModItems.GRONCKLE_IRON_SWORD.get(),"Gronckel-Eisenschwert");
        translationBuilder.add(ModItems.GRONCKLE_IRON_SHOVEL.get(),"Gronckel-Eisenschaufel");
        translationBuilder.add(ModItems.GRONCKLE_IRON_PICKAXE.get(),"Gronckel-Eisenspitzhacke");
        translationBuilder.add(ModItems.GRONCKLE_IRON_AXE.get(),"Gronckel-Eisenaxt");
        translationBuilder.add(ModItems.GRONCKLE_IRON_HOE.get(),"Gronckel-Eisenhacke");
        //Blocks
        translationBuilder.add(ModBlocks.NIGHT_FURY_SCALES.get().asItem(), "Nachtschatten-Schuppen");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_BLOCK.get().asItem(), "Gronckel-Eisenblock");

        translationBuilder.add(ModBlocks.GRONCKLE_LAVA.get().asItem(), "Gronckel-Lava");
        translationBuilder.add(ModBlocks.GRONCKLE_LAVA_BLOCK_LAVA.get().asItem(), "Gronckel-Lavablock");

        translationBuilder.add(ModBlocks.GRONCKLE_IRON_STAIRS.get().asItem(), "Gronckel-Eisentreppe");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_SLAB.get().asItem(), "Gronckel-Eisenstufe");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_BARS.get().asItem(), "Gronckel-Eisengitter");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_DOOR.get().asItem(), "Gronckel-Eisentür");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_TRAPDOOR.get().asItem(), "Gronckel-Eisenfalltür");
        translationBuilder.add(ModBlocks.GRONCKLE_IRON_CHAIN.get().asItem(), "Gronckel-Eisenkette");

        translationBuilder.add(ModBlocks.RAW_GRONCKLE_IRON_BLOCK.get().asItem(), "Block aus rohem Gronckel-Eisen");
        translationBuilder.add(ModBlocks.RAW_GRONCKLE_IRON_STAIRS.get().asItem(), "Treppe aus rohem Gronckel-Eisen");
        translationBuilder.add(ModBlocks.RAW_GRONCKLE_IRON_SLAB.get().asItem(), "Stufe aus rohem Gronckel-Eisen");
        translationBuilder.add(ModBlocks.MOLTEN_GRONCKLE_IRON_LAVA.get().asItem(), "Geschmolzenes Gronckel-Eisen");
        translationBuilder.add(ModBlocks.MOLTEN_GRONCKLE_IRON_BLOCK.get().asItem(), "Block aus geschmolzenem Gronckel-Eisen");

        translationBuilder.add(ModBlocks.DRAGON_PROVED_STEAL_BLOCK.get().asItem(), "Drachensicherer Stahlblock");
        translationBuilder.add(ModBlocks.DRAGON_PROVED_STEAL_STAIRS.get().asItem(), "Drachensichere Stahltreppe");
        translationBuilder.add(ModBlocks.DRAGON_PROVED_STEAL_SLAB.get().asItem(), "Drachensichere Stahlstufe");

        translationBuilder.add(ModBlocks.GRONCKLE_SCALES.get().asItem(), "Gronckel-Schuppen");

        translationBuilder.add(ModBlocks.DRAGON_MANUAL.get().asItem(), "Buch der Drachen");

        //Entity Types
        translationBuilder.add(getEntityTypeKey(ModEntityTypes.NIGHT_FURY.get()).toLanguageKey("entity"),"Nachtschatten");
        translationBuilder.add(getEntityTypeKey(ModEntityTypes.PLASMA_BLAST.get()).toLanguageKey("entity"),"Plasmastrahl");

        //CreativeModeTaps
        translationBuilder.add("itemGroup." + NotEnoughDragons.MOD_ID + "."+"ned_dragons","NED Drachen");
        translationBuilder.add("itemGroup." + NotEnoughDragons.MOD_ID + "."+"ned_misc_itms","NED Verschiedenes");
        translationBuilder.add("itemGroup." + NotEnoughDragons.MOD_ID + "."+"ned_blocks","NED Blöcke");

        //Villagers
        translationBuilder.add("villager.not_enough_dragons.viking", "Wikinger");

        //Texts
        translationBuilder.add("text.not_enough_dragons.dragon.get_trust", "Dein Vertrauen ist: ");
        translationBuilder.add("text.not_enough_dragons.dragon.dragon.not_eat", "Dieser Drache möchte gerade nichts fressen.");
        translationBuilder.add("text.not_enough_dragons.dragon.dragon.not_tame","Du kannst diesen Drachen nicht zähmen, da er bereits von {} gezähmt wurde!");
        translationBuilder.add("text.not_enough_dragons.dragon.dragon.not_tame_no_name", "Du kannst diesen Drachen nicht zähmen, da er bereits gezähmt wurde!");

        translationBuilder.add("config.not_enough_dragons.titel", "Not enough Dragons - Einstellungen");
    }
}