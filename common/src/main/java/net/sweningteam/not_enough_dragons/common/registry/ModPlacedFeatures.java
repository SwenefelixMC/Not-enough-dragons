package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> PATCH_DRAGON_NIP =register("patch_dragon_nip");

    public static ResourceKey<PlacedFeature> register(String name){
        return ResourceKey.create(Registries.PLACED_FEATURE, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, name));
    }
    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Placed Features for {}!", NotEnoughDragons.MOD_NAME);
    }
}
