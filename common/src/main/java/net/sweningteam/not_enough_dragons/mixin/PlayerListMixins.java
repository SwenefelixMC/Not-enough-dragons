package net.sweningteam.not_enough_dragons.mixin;

import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.CommonListenerCookie;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.player.Player;
import net.sweningteam.not_enough_dragons.common.config.ModServerConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerList.class)
public class PlayerListMixins {
    @Inject(method = "placeNewPlayer", at = @At("TAIL"))
    public void placeNewPlayer(Connection connection, ServerPlayer player, CommonListenerCookie cookie, CallbackInfo ci) {
        if(ModServerConfig.enableAlphaWarning){
            player.sendSystemMessage(Component.literal("Warning, This is A early Alpha version! \nExpect Bugs and Crashes").withColor(TextColor.RED));
        }
        if(ModServerConfig.enableThanksForDownload){
            player.sendSystemMessage(Component.literal("Thanks for Downloading Not Enough Dragon \nSwenefelixMC (MainDev)"));
        }
    }
}
