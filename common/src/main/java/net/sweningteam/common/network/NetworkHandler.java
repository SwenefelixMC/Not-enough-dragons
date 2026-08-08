package net.sweningteam.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.network.packages.c2s.DragonRiderInputPackage;
import net.sweningteam.platform.NetworkHelper;
import net.sweningteam.platform.Services;

public class NetworkHandler {
    private static final NetworkHelper NETWORK = Services.PLATFORM.getNetworkHelper();
    private static boolean registered = false;

    private NetworkHandler(){
    }

    private static ResourceLocation id(String path){
        return ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, path);
    }

    public static void init(){
        if(registered){
            return;
        }
        registered = true;

        NETWORK.registerServerbound(
                DragonRiderInputPackage.class,
                id("dragon_rider_input"),
                DragonRiderInputPackage::encode,
                DragonRiderInputPackage::decode,
                DragonRiderInputPackage::handle
        );
    }

    public static void sendC2SPacket(Object packet){
        NETWORK.sendToServer(packet);
    }
}
