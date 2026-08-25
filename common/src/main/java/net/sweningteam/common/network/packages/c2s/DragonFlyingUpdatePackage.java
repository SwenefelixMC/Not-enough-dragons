package net.sweningteam.common.network.packages.c2s;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.sweningteam.common.entity.dragons.base.Dragon;
import net.sweningteam.common.entity.dragons.base.interfaces.FlyingDragon;

public record DragonFlyingUpdatePackage(
        boolean flying
) {
    public static void encode(DragonFlyingUpdatePackage pack, FriendlyByteBuf buf){
        buf.writeBoolean(pack.flying());
    }
    public static DragonFlyingUpdatePackage decode(FriendlyByteBuf buf){
        boolean flying = buf.readBoolean();
        return new DragonFlyingUpdatePackage(flying);
    }
    public static void handle(DragonFlyingUpdatePackage pack, ServerPlayer player){
        if(player == null){
            return;
        }
        Entity vehicle = player.getVehicle();
        if (vehicle instanceof Dragon dragon){
            if (dragon instanceof FlyingDragon flyingDragon){
                flyingDragon.setFlying(pack.flying());
            }else{
                return;
            }
        }else{
            return;
        }
    }
}
