package net.sweningteam.not_enough_dragons.common.worldgen.features;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;
import net.sweningteam.not_enough_dragons.common.registry.ModFeatures;
import net.sweningteam.not_enough_dragons.common.registry.ModPlacedFeatures;

import java.util.List;

public class ModPlacedFeatureConfiguration {
    public static void boostrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<Feature> configuredFeatures = context.lookup(Registries.FEATURE);
        Holder<Feature> dragon_nip = configuredFeatures.getOrThrow(ModFeatures.DRAGON_NIP);
        context.register(
                ModPlacedFeatures.PATCH_DRAGON_NIP,
                new PlacedFeature(dragon_nip,
                        List.of(new PlacementModifier[]{RarityFilter.onAverageOnceEvery(4),
                                InSquarePlacement.spread(), PlacementUtils.HEIGHTMAP, BiomeFilter.biome(), CountPlacement.of(24), OffsetPlacement.ofTriangle(5, 3), BlockPredicateFilter.forPredicate(BlockPredicate.ONLY_IN_AIR_PREDICATE)})
        ));
    }
}
