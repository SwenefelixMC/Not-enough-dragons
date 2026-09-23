package net.sweningteam.not_enough_dragons.common.worldgen.features;

import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.sweningteam.not_enough_dragons.common.registry.ModPlacedFeatures;
import net.sweningteam.not_enough_dragons.common.registry.ModTags;

import java.util.ArrayList;
import java.util.List;

public class ModFeatureSpawns {
    public static List<FeatureSpawnEntry> spawns(){
        ArrayList<FeatureSpawnEntry> spawnEntries = new ArrayList<>();
        spawnEntries.add(new FeatureSpawnEntry(
                ModTags.Biomes.DRAGON_NIP_SPANS_IN,
                GenerationStep.Decoration.VEGETAL_DECORATION,
                ModPlacedFeatures.PATCH_DRAGON_NIP
        ));
        return spawnEntries;
    }

    public static void init(){

    }

    public record FeatureSpawnEntry(
            TagKey<Biome> biomeTag,
            GenerationStep.Decoration step,
            ResourceKey<PlacedFeature> feature
    ){

    }
}
