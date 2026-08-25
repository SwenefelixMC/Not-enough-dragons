package net.sweningteam.neoforge.worldgen;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.sweningteam.common.worldgen.ModEntitySpawns;

public class ModSpawns {

    public static class SpawnModifier implements BiomeModifier{

        public static final MapCodec<SpawnModifier> CODEC = MapCodec.unit(SpawnModifier::new);

        @Override
        public void modify(Holder<Biome> arg, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD){
                for (ModEntitySpawns.DragonSpawnEntry spawnEntry: ModEntitySpawns.spawnEntries()){
                    if(arg.is(spawnEntry.biomeTag())){
                        builder.getMobSpawnSettings().addSpawn(
                            spawnEntry.category(),
                            spawnEntry.weight(),
                            new MobSpawnSettings.SpawnerData(
                                spawnEntry.entityType().get(),
                                spawnEntry.minGroupSize(),
                                spawnEntry.maxGroupSize()
                            )
                        );
                    }
                }
            }
        }

        @Override
        public MapCodec<? extends BiomeModifier> codec() {
            return null;
        }
    }
}
