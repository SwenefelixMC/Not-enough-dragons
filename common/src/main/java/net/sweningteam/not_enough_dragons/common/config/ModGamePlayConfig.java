package net.sweningteam.not_enough_dragons.common.config;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;
import net.minecraft.resources.Identifier;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;

public class ModGamePlayConfig {
    public static ConfigClassHandler<ModGamePlayConfig> CONFIG = ConfigClassHandler.createBuilder(ModGamePlayConfig.class)
            .id(Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "game_play_config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve(NotEnoughDragons.MOD_ID+"/game_play_config.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @SerialEntry(comment = "Enables Dragon / pillager fights")
    public static Boolean dragonsFightRaiders = true;
    @SerialEntry(comment = "Enables that dragons can Damage you when you feed them")
    public static Boolean dragonDamageWhenFeed = true;
    @SerialEntry(comment = "Enables some Creative mode Backdoors like instant taming")
    public static Boolean enableCreativeBackdoors = false;

    public static void init() {
        CONFIG.load();
    }
}
