package net.sweningteam;

import net.sweningteam.common.network.NetworkHandler;
import net.sweningteam.common.registry.*;
import net.sweningteam.platform.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class NotEnoughDragons {
    public static final String MOD_ID = "not_enough_dragons";
    public static final String MOD_NAME = "Not enough Dragons";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

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
//        ModVillagerTrades.init();

        NetworkHandler.init();
    }
}
