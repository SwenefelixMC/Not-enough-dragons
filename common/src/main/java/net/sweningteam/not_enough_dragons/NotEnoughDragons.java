package net.sweningteam.not_enough_dragons;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.sweningteam.not_enough_dragons.common.config.ModGamePlayConfig;
import net.sweningteam.not_enough_dragons.common.config.ModMainConfig;
import net.sweningteam.not_enough_dragons.common.network.NetworkHandler;
import net.sweningteam.not_enough_dragons.common.registry.*;
import net.sweningteam.not_enough_dragons.platform.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NotEnoughDragons {
    public static final String MOD_ID = "not_enough_dragons";
    public static final String MOD_NAME = "Not enough Dragons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    public static void initPreLaunch(){
        ModMainConfig.loadConfigs();
    }

    public static void init() {
        NotEnoughDragons.LOGGER.info("Hello from Common init on {}! we are currently in a {} environment!", Services.PLATFORM.getPlatformName(), Services.PLATFORM.getEnvironmentName());
        // Write common init code here.
        ModDataComponentTypes.init();
        ModItems.init();
        ModBlocks.init();
        ModCreativeTabs.init();
        ModAttributes.init();
        ModEntityTypes.init();
        ModPoiTypes.init();
        ModVillagers.init();
        ModEffects.init();
        ModFeatures.init();
        ModPlacedFeatures.init();

        ModStructurs.init();
        ModStructureTypes.init();
        ModStructurPieceTypes.init();

        ModRecipieSerializers.init();
        ModRecipieTypes.init();
        NetworkHandler.init();
    }

    public static boolean shouldUseCreativeBackdoor(Player player) {
        return ModGamePlayConfig.enableCreativeBackdoors && player.isCreative();
    }

    public static void onEndTick(Minecraft minecraft){

    }
}
