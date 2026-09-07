package net.sweningteam.fabric.datagen.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagEntry;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.sweningteam.common.registry.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBiomeTagProvider extends FabricTagProvider<Biome> {

    public ModBiomeTagProvider(FabricDataOutput output, CompletableFuture registriesFuture) {
        super(output, Registries.BIOME, registriesFuture);
    }


    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateRawBuilder(ModTags.Biomes.NIGHT_FURY_SPAWNS_IN)
                .addElement(Biomes.FROZEN_PEAKS.location())
                .addElement(Biomes.JAGGED_PEAKS.location())
                .addElement(Biomes.STONY_PEAKS.location())
                .addElement(Biomes.SNOWY_SLOPES.location())
                .addElement(Biomes.WINDSWEPT_GRAVELLY_HILLS.location())
                .addElement(Biomes.WINDSWEPT_HILLS.location());

        getOrCreateRawBuilder(ModTags.Biomes.GRONCKLE_SPAWN_IN)
                .addElement(Biomes.SNOWY_PLAINS.location())
                .addElement(Biomes.SWAMP.location())
                .addElement(Biomes.MEADOW.location())
                .addElement(Biomes.OLD_GROWTH_BIRCH_FOREST.location())
                .addElement(Biomes.SAVANNA.location())
                .addElement(Biomes.SAVANNA_PLATEAU.location())
                .addElement(Biomes.WINDSWEPT_SAVANNA.location())
                .addElement(Biomes.PLAINS.location())
                .addElement(Biomes.GROVE.location())
                .addElement(Biomes.SUNFLOWER_PLAINS.location())
                .addElement(Biomes.STONY_SHORE.location())
                .addElement(Biomes.CHERRY_GROVE.location());
    }
}
