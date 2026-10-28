package net.sweningteam.not_enough_dragons.common.network.packages.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;

public record DragonRiderInputPackage(
        boolean forward,
        boolean backward,
        boolean sprint,
        boolean jump,
        boolean charge
) {
    public static void encode(DragonRiderInputPackage pack, FriendlyByteBuf buf){
        buf.writeBoolean(pack.forward());
        buf.writeBoolean(pack.backward());
        buf.writeBoolean(pack.sprint());
        buf.writeBoolean(pack.jump());
        buf.writeBoolean(pack.charge());
    }
    public static DragonRiderInputPackage decode(FriendlyByteBuf buf){
        boolean forward = buf.readBoolean();
        boolean backward = buf.readBoolean();
        boolean sprint = buf.readBoolean();
        boolean jump = buf.readBoolean();
        boolean charge = buf.readBoolean();
        return new DragonRiderInputPackage(forward,backward,sprint,jump,charge);
    }

    public static void handle(DragonRiderInputPackage pack, ServerPlayer player){
        if(player == null){
            return;
        }
        Entity vehicle = player.getVehicle();
        if(vehicle instanceof Dragon dragon && dragon.canBeControledBy(player)){
            dragon.handlePlayerInputPackage(player,pack);
        }
    }
    public boolean isEmpty(){
        return !this.forward && !this.backward && !this.sprint && !this.jump;
    }
}
