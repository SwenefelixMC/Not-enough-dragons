package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;

public class ModFeatures {
    public static final ResourceKey<Feature> DRAGON_NIP = register("dragon_nip");
    public static final ResourceKey<Feature> VIKING_TRADER_SHIP = register("viking_trader_ship");

    public static ResourceKey<Feature> register(String name){
        return ResourceKey.create(Registries.FEATURE, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, name));
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Features for {}!", NotEnoughDragons.MOD_NAME);
    }
}
