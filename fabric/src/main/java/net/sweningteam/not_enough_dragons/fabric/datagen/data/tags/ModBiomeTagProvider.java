package net.sweningteam.not_enough_dragons.fabric.datagen.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.sweningteam.not_enough_dragons.common.registry.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends FabricTagsProvider<Biome> {

    public ModBiomeTagProvider(FabricPackOutput output, CompletableFuture registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }


    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateRawBuilder(ModTags.Biomes.NIGHT_FURY_SPAWNS_IN)
                .addElement(Biomes.FROZEN_PEAKS.identifier())
                .addElement(Biomes.JAGGED_PEAKS.identifier())
                .addElement(Biomes.STONY_PEAKS.identifier())
                .addElement(Biomes.SNOWY_SLOPES.identifier())
                .addElement(Biomes.WINDSWEPT_GRAVELLY_HILLS.identifier())
                .addElement(Biomes.WINDSWEPT_HILLS.identifier());

        getOrCreateRawBuilder(ModTags.Biomes.GRONCKLE_SPAWN_IN)
                .addElement(Biomes.SNOWY_PLAINS.identifier())
                .addElement(Biomes.SWAMP.identifier())
                .addElement(Biomes.MEADOW.identifier())
                .addElement(Biomes.OLD_GROWTH_BIRCH_FOREST.identifier())
                .addElement(Biomes.SAVANNA.identifier())
                .addElement(Biomes.SAVANNA_PLATEAU.identifier())
                .addElement(Biomes.WINDSWEPT_SAVANNA.identifier())
                .addElement(Biomes.PLAINS.identifier())
                .addElement(Biomes.GROVE.identifier())
                .addElement(Biomes.SUNFLOWER_PLAINS.identifier())
                .addElement(Biomes.STONY_SHORE.identifier())
                .addElement(Biomes.CHERRY_GROVE.identifier());

        getOrCreateRawBuilder(ModTags.Biomes.DRAGON_NIP_SPANS_IN)
                .addElement(Biomes.SAVANNA.identifier())
                .addElement(Biomes.SAVANNA_PLATEAU.identifier())
                .addElement(Biomes.PLAINS.identifier())
                .addElement(Biomes.GROVE.identifier())
                .addElement(Biomes.SUNFLOWER_PLAINS.identifier())
                .addElement(Biomes.MEADOW.identifier())
                .addElement(Biomes.OLD_GROWTH_BIRCH_FOREST.identifier())
                .addElement(Biomes.FLOWER_FOREST.identifier())
                .addElement(Biomes.WINDSWEPT_HILLS.identifier());
    }
}
