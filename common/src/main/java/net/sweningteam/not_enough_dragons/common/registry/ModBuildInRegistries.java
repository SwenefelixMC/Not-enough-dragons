package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponentType;
import net.sweningteam.not_enough_dragons.platform.Services;

public class ModBuildInRegistries {

    //public static final Registry<NewDragonFoodComponent> DRAGON_FOOD_COMPONENT = register(ModRegistries.DRAGON_FOOD_COMPONENT);
    public static final Registry<DragonFoodComponentType<?>> DRAGON_FOOD_COMPONENT_TYPE = register(ModRegistries.DRAGON_FOOD_COMPONENT_TYPE);

    public static <T> Registry<T> register(ResourceKey<Registry<T>> name) {
        return Services.PLATFORM.getRegisterHelper().registerRegistry(name);
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Build in Registers for {}!", NotEnoughDragons.MOD_NAME);
    }
}
