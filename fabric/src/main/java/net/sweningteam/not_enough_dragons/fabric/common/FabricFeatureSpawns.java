package net.sweningteam.not_enough_dragons.fabric.common;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.sweningteam.not_enough_dragons.common.worldgen.features.ModFeatureSpawns;

public class FabricFeatureSpawns {
    public static void register() {
        for(ModFeatureSpawns.FeatureSpawnEntry entry : ModFeatureSpawns.spawns()){
            BiomeModifications.addFeature(
                    BiomeSelectors.tag(entry.biomeTag()),
                    entry.step(),
                    entry.feature()
            );
        }
    }
}
