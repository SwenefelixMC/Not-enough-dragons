package net.sweningteam.neoforge;

import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;

import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.client.NotEnoughDragonsClient;
import net.sweningteam.client.entity.renderer.NightFuryRendrer;
import net.sweningteam.common.registry.ModEntityTypes;
import net.sweningteam.neoforge.datagen.data.ModDatapackProvider;
import net.sweningteam.neoforge.platform.NeoForgeNetworkHelper;
import net.sweningteam.neoforge.platform.NeoForgeRegistryHelper;
import net.sweningteam.neoforge.worldgen.NeoforgeBiomeModifications;

import java.util.concurrent.CompletableFuture;

@Mod(NotEnoughDragons.MOD_ID)
public final class NotEnoughDragonsNeoForge {
    public NotEnoughDragonsNeoForge(IEventBus eventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.register(this);
        eventBus.addListener(this::onCommonSetup);



        NotEnoughDragons.init();
        NeoForgeRegistryHelper.register(eventBus);
        eventBus.addListener((EntityAttributeCreationEvent event)-> ModEntityTypes.registerEntityAtributes(event::put));
        eventBus.addListener(NeoForgeNetworkHelper::onRegisterPayloads);
        //EVENT_BUS.addListener((EntityAttributeCreationEvent event) -> ModEntityTypes.registerEntityAtributes(event::put));
    }

    private void onCommonSetup(FMLCommonSetupEvent event){

    }
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {

    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = NotEnoughDragons.MOD_ID,value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            NotEnoughDragonsClient.registerEntityRenderers(EntityRenderers::register);
        }
    }

    @EventBusSubscriber(modid = NotEnoughDragons.MOD_ID, value = Dist.DEDICATED_SERVER)
    public static class ServerModEvents {
        @SubscribeEvent
        public static void gatherServerData(GatherDataEvent.Server event){
            DataGenerator generator = event.getGenerator();
            PackOutput packOutput = generator.getPackOutput();
            CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

            generator.addProvider(true, new ModDatapackProvider(packOutput,lookupProvider));
        }
    }
}
