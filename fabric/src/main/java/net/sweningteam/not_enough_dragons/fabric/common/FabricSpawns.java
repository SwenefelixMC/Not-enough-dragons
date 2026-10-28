package net.sweningteam.not_enough_dragons.fabric.common;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacements;
import net.sweningteam.not_enough_dragons.common.worldgen.entity.ModEntitySpawns;

public class FabricSpawns {
    public static void register(){
        for (ModEntitySpawns.DragonSpawnEntry entry : ModEntitySpawns.spawnEntries()){
            BiomeModifications.addSpawn(
                    BiomeSelectors.tag(entry.biomeTag()),
                    entry.category(),
                    entry.entityType().get(),
                    entry.weight(),
                    entry.minGroupSize(),
                    entry.maxGroupSize());

        }
        for (ModEntitySpawns.DragonSpawnRestrictionEntry entry : ModEntitySpawns.spawnRestrictionEntries()){
            SpawnPlacements.register(
                    (EntityType<? extends Mob>) entry.entityType().get(),
                    entry.types(),
                    entry.heightMap(),
                    entry.predicate()

            );
        }
    }
}