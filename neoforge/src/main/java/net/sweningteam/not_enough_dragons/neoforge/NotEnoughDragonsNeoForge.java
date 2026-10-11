package net.sweningteam.not_enough_dragons.neoforge;

import com.mojang.serialization.MapCodec;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.client.NotEnoughDragonsClient;
import net.sweningteam.not_enough_dragons.common.config.ModMainConfig;
import net.sweningteam.not_enough_dragons.common.registry.ModEntityTypes;
import net.sweningteam.not_enough_dragons.common.worldgen.entity.ModEntitySpawns;
import net.sweningteam.not_enough_dragons.neoforge.platform.NeoForgeNetworkHelper;
import net.sweningteam.not_enough_dragons.neoforge.platform.NeoForgeRegistryHelper;
import net.sweningteam.not_enough_dragons.neoforge.worldgen.ModSpawns;
import net.sweningteam.not_enough_dragons.neoforge.worldgen.NeoForgeFeautureSpawns;

@Mod(NotEnoughDragons.MOD_ID)
public final class NotEnoughDragonsNeoForge {

    public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
            DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, NotEnoughDragons.MOD_ID);

    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<ModSpawns.SpawnModifier>> DRAGON_SPAWNS =
            BIOME_MODIFIER_SERIALIZERS.register("dragon_spawns", () -> ModSpawns.SpawnModifier.CODEC);

//    public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<NeoForgeFeautureSpawns>> FEATURE_SPAWNS =
//            BIOME_MODIFIER_SERIALIZERS.register("feature_spawns", ()-> NeoForgeFeautureSpawns.CODEC);

    public NotEnoughDragonsNeoForge(IEventBus eventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerExtensionPoint(
                IConfigScreenFactory.class,
                ()-> (client, parent) -> new ModMainConfig().CONFIG.generateScreen(parent)
        );

        eventBus.addListener(this::onCommonSetup);

        NotEnoughDragons.init();
        NeoForgeRegistryHelper.register(eventBus);
        eventBus.addListener((EntityAttributeCreationEvent event)-> ModEntityTypes.registerEntityAtributes(event::put));
        eventBus.addListener(NeoForgeNetworkHelper::onRegisterPayloads);
        BIOME_MODIFIER_SERIALIZERS.register(eventBus);
        //EVENT_BUS.addListener((EntityAttributeCreationEvent event) -> ModEntityTypes.registerEntityAtributes(event::put));
    }

    private void onCommonSetup(FMLCommonSetupEvent event){
        NotEnoughDragons.initPreLaunch();
    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = NotEnoughDragons.MOD_ID,value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            NotEnoughDragonsClient.init();
            NotEnoughDragonsClient.registerEntityRenderers(EntityRenderers::register);
        }
    }

    @EventBusSubscriber(modid = NotEnoughDragons.MOD_ID, value = Dist.DEDICATED_SERVER)
    public static class ServerModEvents {
        @SubscribeEvent
        public static void registerSpawnPlacements(RegisterSpawnPlacementsEvent event){
            for(ModEntitySpawns.DragonSpawnRestrictionEntry entry : ModEntitySpawns.spawnRestrictionEntries()){
                event.register(
                        entry.entityType().get(),
                        entry.types(),
                        entry.heightMap(),
                        entry.predicate(),
                        RegisterSpawnPlacementsEvent.Operation.REPLACE
                );
            }
        }
    }
}
