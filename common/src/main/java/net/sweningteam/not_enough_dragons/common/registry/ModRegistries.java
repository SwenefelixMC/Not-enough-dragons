package net.sweningteam.not_enough_dragons.common.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponent;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponentType;
import net.sweningteam.not_enough_dragons.platform.Services;

public class ModRegistries {
    public static final ResourceKey<Registry<DragonFoodComponent>> DRAGON_FOOD_COMPONENT = createRegistryKey("dragon_food_component");
    public static final ResourceKey<Registry<DragonFoodComponentType<?>>> DRAGON_FOOD_COMPONENT_TYPE = createRegistryKey("dragon_food_component_type");

    private static <T> ResourceKey<Registry<T>> createRegistryKey(final String name) {
        return ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,name));
    }

    public static <T> void sync(ResourceKey<Registry<T>> registry, Codec<T> codec) {
        Services.PLATFORM.getRegisterHelper().syncRegistryToCodec(registry, codec);
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Registries for {}!", NotEnoughDragons.MOD_NAME);
        sync(DRAGON_FOOD_COMPONENT, DragonFoodComponent.DIRECT_CODEC);
    }
}
