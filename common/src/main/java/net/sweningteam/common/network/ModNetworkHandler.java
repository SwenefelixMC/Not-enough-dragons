package net.sweningteam.common.network;

import net.minecraft.world.entity.Entity;

public class ModNetworkHandler {
    private ModNetworkHandler(){

    }

    public static void sendC2SPacket(Object packet){
        NetworkHandler.sendC2SPacket(packet);
    }
    public static void sendC2TPacket(Entity entity, Object packet){
        NetworkHandler.sendC2TPacket(entity,packet);
    }

}
