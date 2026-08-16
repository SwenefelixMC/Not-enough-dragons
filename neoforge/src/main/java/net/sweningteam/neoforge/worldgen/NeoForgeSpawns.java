package net.sweningteam.neoforge.worldgen;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.sweningteam.common.worldgen.ModEntitySpawns;

public class NeoForgeSpawns {

    @SubscribeEvent
    public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event){
        for (ModEntitySpawns.DragonSpawnRestrictionEntry entry : ModEntitySpawns.spawnRestrictionEntries()){
            event.register(
                    (EntityType<? extends Entity>) entry.entityType().get(),
                    entry.types(),
                    entry.heightMap(),
                    entry.predicate(),
                    RegisterSpawnPlacementsEvent.Operation.AND
            );
        }
    }
}
