package net.sweningteam.not_enough_dragons.common.worldgen;

import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.Heightmap;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;
import net.sweningteam.not_enough_dragons.common.registry.ModEntityTypes;
import net.sweningteam.not_enough_dragons.common.registry.ModTags;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModEntitySpawns {
    public static List<DragonSpawnEntry> spawnEntries(){
        List<DragonSpawnEntry> spawnEntries = new ArrayList<>();
        spawnEntries.add(new DragonSpawnEntry(
                ModTags.Biomes.NIGHT_FURY_SPAWNS_IN,
                MobCategory.CREATURE,
                ModEntityTypes.NIGHT_FURY,
                3,
                1,
                3,
                "night_fury"));
        spawnEntries.add(new DragonSpawnEntry(
                ModTags.Biomes.GRONCKLE_SPAWN_IN,
                MobCategory.CREATURE,
                ModEntityTypes.GRONCKLE,
                12,
                2,
                4,
                "gronckle"
        ));
        return spawnEntries;
    }
    public static List<DragonSpawnRestrictionEntry> spawnRestrictionEntries(){
        List<DragonSpawnRestrictionEntry> spawnRestrictionEntries = new ArrayList<>();
        spawnRestrictionEntries.add(new DragonSpawnRestrictionEntry(
                ModEntityTypes.NIGHT_FURY,
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Dragon::checkDragonSpawnRules
        ));
        return spawnRestrictionEntries;
    }

    public record DragonSpawnEntry(
            TagKey<Biome> biomeTag,
            MobCategory category,
            Supplier<? extends EntityType<?>> entityType,
            int weight,
            int minGroupSize,
            int maxGroupSize,
            String name
    ){
    }
    public record DragonSpawnRestrictionEntry(
            Supplier<? extends EntityType<?>> entityType,
            SpawnPlacementType types,
            Heightmap.Types heightMap,
            SpawnPlacements.SpawnPredicate predicate
    ){
    }
}
