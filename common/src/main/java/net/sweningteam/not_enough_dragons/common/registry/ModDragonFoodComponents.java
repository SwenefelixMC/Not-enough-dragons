package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponent;

public class ModDragonFoodComponents {
    public static final ResourceKey<DragonFoodComponent> NIGHT_FURY_COD = register("night_fury/cod");
    public static final ResourceKey<DragonFoodComponent> NIGHT_FURY_SALMON = register("night_fury/salmon");
    public static final ResourceKey<DragonFoodComponent> NIGHT_FURY_TROPICAL_FISH = register("night_fury/tropical_fish");
    public static final ResourceKey<DragonFoodComponent> NIGHT_FURY_PUFFER_FISH = register("night_fury/puffer_fish");
    public static final ResourceKey<DragonFoodComponent> NIGHT_FURY_COOKED_COD = register("night_fury/cooked_cod");
    public static final ResourceKey<DragonFoodComponent> NIGHT_FURY_COOKED_SALMON = register("night_fury/cooked_salmon");

    public static ResourceKey<DragonFoodComponent> register(String name){
        return ResourceKey.create(ModRegistries.DRAGON_FOOD_COMPONENT, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, name));
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Dragon Food Components for {}!", NotEnoughDragons.MOD_NAME);
    }
}
