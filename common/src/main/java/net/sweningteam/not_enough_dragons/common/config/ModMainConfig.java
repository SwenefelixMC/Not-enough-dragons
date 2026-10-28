package net.sweningteam.not_enough_dragons.common.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.network.chat.Component;

public class ModMainConfig {
    public YetAnotherConfigLib CONFIG = YetAnotherConfigLib.createBuilder()
            .save(this::saveConfigs)
        .title(Component.literal("Not Enough Dragons"))
            .category(ConfigCategory.createBuilder()
                .name(Component.literal("Server"))
                .tooltip(Component.literal("These Options Control The Behavior of The Server"))
                 .group(OptionGroup.createBuilder()
                         .name(Component.literal("Messages"))
                         .description(OptionDescription.of(Component.literal("Settings for Messages")))
                         .option(Option.<Boolean>createBuilder()
                                .name(Component.literal("Alpha Warning Message"))
                                 .description(OptionDescription.of(Component.literal("This Controls a Message Warning the Player about the Alpha State of this mod")))
                                 .binding(true, () -> ModServerConfig.enableAlphaWarning, newVal -> ModServerConfig.enableAlphaWarning = newVal)
                                 .controller(TickBoxControllerBuilder::create)
                                .build())
                         .option(Option.<Boolean>createBuilder()
                                 .name(Component.literal("Thanks for Download Message"))
                                 .description(OptionDescription.of(Component.literal("This Controls a Message thanking the Player for Downloading the mod")))
                                 .binding(true, ()->ModServerConfig.enableThanksForDownload, newVal -> ModServerConfig.enableThanksForDownload = newVal)
                                 .controller(TickBoxControllerBuilder::create)
                                 .build())
                         .build())
                    .build())
            .category(ConfigCategory.createBuilder()
                    .name(Component.literal("Game Play"))
                    .tooltip(Component.literal("These Options Control the Gameplay and Story of the Mod"))
                    .group(OptionGroup.createBuilder()
                            .name(Component.literal("EntityBehavior"))
                            .description(OptionDescription.of(Component.literal("This Controles The Behavior of Entities")))
                            .option(Option.<Boolean>createBuilder()
                                    .name(Component.literal("Dragons and Pillager Fight"))
                                    .description(OptionDescription.of(Component.literal("This Controles if Dragons and Pillager fight")))
                                    .binding(true, ()-> ModGamePlayConfig.dragonsFightRaiders, newVal -> ModGamePlayConfig.dragonsFightRaiders = newVal)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build())
                            .option(Option.<Boolean>createBuilder()
                                    .name(Component.literal("Dragon Damage on Feed"))
                                    .description(OptionDescription.of(Component.literal("This Controles if a Dragon will damage you when you feed them")))
                                    .binding(true, ()->ModGamePlayConfig.dragonDamageWhenFeed, newVal -> ModGamePlayConfig.dragonDamageWhenFeed = newVal)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build())
                            .build())
                    .group(OptionGroup.createBuilder()
                            .name(Component.literal("Creative Mode"))
                            .description(OptionDescription.of(Component.literal("This Controles Mod Behavior in Creative mode")))
                            .option(Option.<Boolean>createBuilder()
                                    .name(Component.literal("Enable Creative Mode Backdoor"))
                                    .description(OptionDescription.of(Component.literal("This setting allows you: \nto Tame Dragons Instant, \nto forse feed Dragons")))
                                    .binding(false, ()->ModGamePlayConfig.enableCreativeBackdoors, newVal -> ModGamePlayConfig.enableCreativeBackdoors = newVal)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build())
                            .build())
                    .build())
            .build();

    public void saveConfigs(){
        ModServerConfig.CONFIG.save();
        ModGamePlayConfig.CONFIG.save();
    }

    public static void  loadConfigs(){
        ModServerConfig.CONFIG.load();
        ModGamePlayConfig.CONFIG.load();
    }

}
