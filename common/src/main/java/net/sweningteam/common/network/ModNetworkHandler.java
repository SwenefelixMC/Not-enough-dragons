package net.sweningteam.common.network;

public class ModNetworkHandler {
    private ModNetworkHandler(){

    }

    public static void sendC2SPacket(Object packet){
        NetworkHandler.sendC2SPacket(packet);
    }
}
