package net.sweningteam.not_enough_dragons.common.config;

import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.TickBoxControllerBuilder;
import net.minecraft.network.chat.Component;

public class ModMainConfig {
    public static Boolean DragonsFightRaiders = true;
    public YetAnotherConfigLib CONFIG = YetAnotherConfigLib.createBuilder()
        .title(Component.literal("Not Enough Dragons"))
            .category(ConfigCategory.createBuilder()
                .name(Component.literal("Gameplay"))
                .tooltip(Component.literal("These are the Options for Gameplay \n These Options Also participate in the Main Story"))
                 .group(OptionGroup.createBuilder()
                         .name(Component.literal("Entity"))
                         .description(OptionDescription.of(Component.literal("Settings for Entities")))
                         .option(Option.<Boolean>createBuilder()
                                .name(Component.literal("Boolean Option"))
                                 .description(OptionDescription.of(Component.literal("This allows Dragons to fight Pillagers")))
                                 .binding(true, () -> DragonsFightRaiders, newVal -> DragonsFightRaiders = newVal)
                                 .controller(TickBoxControllerBuilder::create)
                                .build())
                         .build())
                    .group(OptionGroup.createBuilder()
                            .name(Component.literal("Name of the group1"))
                            .description(OptionDescription.of(Component.literal("This text will appear when you hover over the name or focus on the collapse button with Tab.")))
                            .option(Option.<Boolean>createBuilder()
                                    .name(Component.literal("Boolean Option"))
                                    .description(OptionDescription.of(Component.literal("This text will appear as a tooltip when you hover over the option.")))
                                    .binding(true, () -> DragonsFightRaiders, newVal -> DragonsFightRaiders = newVal)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build())
                            .build())
                    .build())
            .category(ConfigCategory.createBuilder()
                    .name(Component.literal("Client"))
                    .tooltip(Component.literal("Test test"))
                    .group(OptionGroup.createBuilder()
                            .name(Component.literal("Group1"))
                            .description(OptionDescription.of(Component.literal("This text will appear when you hover over the name or focus on the collapse button with Tab.")))
                            .option(Option.<Boolean>createBuilder()
                                    .name(Component.literal("Boolean Option"))
                                    .description(OptionDescription.of(Component.literal("This text will appear as a tooltip when you hover over the option.")))
                                    .binding(true, () -> DragonsFightRaiders, newVal -> DragonsFightRaiders = newVal)
                                    .controller(TickBoxControllerBuilder::create)
                                    .build())
                            .build())
                    .build())
            .build();

}
