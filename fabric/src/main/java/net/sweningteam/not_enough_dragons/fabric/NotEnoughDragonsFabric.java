package net.sweningteam.not_enough_dragons.fabric;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.registry.ModEntityTypes;
import net.sweningteam.not_enough_dragons.fabric.common.FabricFeatureSpawns;
import net.sweningteam.not_enough_dragons.fabric.common.FabricSpawns;

public final class NotEnoughDragonsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run our common setup.
        NotEnoughDragons.init();
        ModEntityTypes.registerEntityAtributes(FabricDefaultAttributeRegistry::register);
        FabricSpawns.register();
        FabricFeatureSpawns.register();
    }
}
