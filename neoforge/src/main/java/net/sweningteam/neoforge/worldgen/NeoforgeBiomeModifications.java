package net.sweningteam.neoforge.worldgen;

import net.minecraft.core.HolderSet;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.BiomeModifiers;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.registry.ModTags;
import net.sweningteam.common.worldgen.ModEntitySpawns;

import java.util.List;

public class NeoforgeBiomeModifications {

    public static void bootstrap(BootstrapContext<BiomeModifier> context){
        var placedFeatures = context.lookup(Registries.PLACED_FEATURE);
        var biome = context.lookup(Registries.BIOME);
        for (ModEntitySpawns.DragonSpawnEntry entry : ModEntitySpawns.spawnEntries()){
            context.register(registry(
                    entry.name()),
                    new BiomeModifiers.AddSpawnsBiomeModifier(
                    biome.getOrThrow(
                            entry.biomeTag()),
                            WeightedList.of(new MobSpawnSettings.SpawnerData(
                                    (EntityType<?>) entry.entityType().get(),
                                    entry.minGroupSize(),
                                    entry.maxGroupSize()
                            ))));

        }
    }

    private static ResourceKey<BiomeModifier> registry(String name){
        return ResourceKey.create(NeoForgeRegistries.Keys.BIOME_MODIFIERS, ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,name));
    }
}
