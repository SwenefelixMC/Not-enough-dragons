package net.sweningteam.not_enough_dragons.common.worldgen.features;

import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.sweningteam.not_enough_dragons.common.registry.ModBlocks;
import net.sweningteam.not_enough_dragons.common.registry.ModFeatures;

public class ModFeatureConfiguration {
    public static void boostrap(BootstrapContext<Feature> context){
        context.register(
                ModFeatures.DRAGON_NIP,
                new SimpleBlockFeature(BlockStateProvider.of(ModBlocks.DRAGON_NIP.get()))
        );
    }
}
