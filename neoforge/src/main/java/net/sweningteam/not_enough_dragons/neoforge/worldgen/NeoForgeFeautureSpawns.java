package net.sweningteam.not_enough_dragons.neoforge.worldgen;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sweningteam.not_enough_dragons.common.worldgen.features.ModFeatureSpawns;
import net.sweningteam.not_enough_dragons.neoforge.platform.NeoForgeRegistryHelper;

import java.awt.*;
import java.util.Objects;

public record NeoForgeFeautureSpawns(HolderSet<PlacedFeature> features) implements BiomeModifier{
        public static final MapCodec<NeoForgeFeautureSpawns> CODEC = RecordCodecBuilder.mapCodec((instance) -> instance.group(
                PlacedFeature.LIST_CODEC.fieldOf("features").forGetter(m -> m.features)
        ).apply(instance, NeoForgeFeautureSpawns::new));

        @Override
        public void modify(Holder<Biome> arg, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
            if (phase == Phase.ADD){
                for (ModFeatureSpawns.FeatureSpawnEntry spawnEntry: ModFeatureSpawns.spawns()){
                    if(arg.is(spawnEntry.biomeTag())){
                        Holder<PlacedFeature> featureHolder = getFeature(spawnEntry.feature());
                        if(featureHolder != null){
                            builder.getGenerationSettings().addFeature(
                                    spawnEntry.step(),
                                    featureHolder
                            );
                        }
                    }
                }
            }
        }

        private Holder<PlacedFeature> getFeature(ResourceKey<PlacedFeature> feature) {
            for(Holder<PlacedFeature> test : features){
                if(test.is(feature)){
                    return test;
                }
            }
            return null;
        }

        @Override
        public MapCodec<? extends BiomeModifier> codec() {
            return CODEC;
        }
}