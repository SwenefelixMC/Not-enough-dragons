package net.sweningteam.not_enough_dragons.common.registry;

import com.mojang.serialization.MapCodec;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponent;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponentType;
import net.sweningteam.not_enough_dragons.common.dragon.food.custom.EffectDragonFoodComponent;
import net.sweningteam.not_enough_dragons.common.dragon.food.custom.StandardDragonFoodComponent;
import net.sweningteam.not_enough_dragons.platform.Services;

import java.util.function.Supplier;

public class ModDragonFoodComponentTypes {
    public static final Supplier<DragonFoodComponentType<?>> BASE_FOOD_COMPONENT = register("base_component", StandardDragonFoodComponent.codec());

    public static final Supplier<DragonFoodComponentType<?>> EFFECT_FOOD_COMPONENT = register("effect_component", EffectDragonFoodComponent.codec());

    public static<T extends DragonFoodComponent> Supplier<DragonFoodComponentType<?>> register(String name, final MapCodec<T> codec) {
        return Services.PLATFORM.getRegisterHelper().register(ModBuildInRegistries.DRAGON_FOOD_COMPONENT_TYPE,name,()->new DragonFoodComponentType<>(codec));
    }

    public static void init() {
        NotEnoughDragons.LOGGER.info("Registering DragonFoodComponentTypes for {}!",NotEnoughDragons.MOD_NAME);
    }
}
