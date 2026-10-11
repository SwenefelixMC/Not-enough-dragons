package net.sweningteam.not_enough_dragons.common.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.resources.Identifier;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;

public class ModServerConfig {
    public static ConfigClassHandler<ModServerConfig> CONFIG = ConfigClassHandler.createBuilder(ModServerConfig.class)
            .id(Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "server_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve(NotEnoughDragons.MOD_ID+"/server_config.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry(comment = "displays a simple message when a player joins reminding them that his is a Alpha Version")
    public static boolean enableAlphaWarning = true;

    @SerialEntry(comment = "displays a simple message thanking a player for downloading this mod When they join a server")
    public static boolean enableThanksForDownload = true;

    public static void init(){
        CONFIG.load();
    }
}
