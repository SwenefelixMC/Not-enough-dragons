package net.sweningteam.not_enough_dragons.common.network;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.network.packages.c2s.DragonFlyingUpdatePackage;
import net.sweningteam.not_enough_dragons.common.network.packages.c2s.DragonRiderInputPackage;
import net.sweningteam.not_enough_dragons.platform.NetworkHelper;
import net.sweningteam.not_enough_dragons.platform.Services;

public class NetworkHandler {
    private static final NetworkHelper NETWORK = Services.PLATFORM.getNetworkHelper();
    private static boolean registered = false;

    private NetworkHandler(){
    }

    private static Identifier id(String path){
        return Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, path);
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
        NETWORK.registerServerbound(
                DragonFlyingUpdatePackage.class,
                id("dragon_flying_update"),
                DragonFlyingUpdatePackage::encode,
                DragonFlyingUpdatePackage::decode,
                DragonFlyingUpdatePackage::handle
        );
    }

    public static void sendC2SPacket(Object packet){
        NETWORK.sendToServer(packet);
    }
    public static void sendC2TPacket(Entity entity, Object packet){
        NETWORK.sendToTracking(entity,packet);
    }
}
