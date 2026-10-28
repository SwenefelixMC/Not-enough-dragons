package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.recipie.Gronckle.GroncklRecipie;
import net.sweningteam.not_enough_dragons.platform.Services;

import java.util.function.Supplier;

public class ModRecipieTypes {
    public static final Supplier<RecipeType<GroncklRecipie>> GRONCKLE_SMELTING= register("gronckle_smelting", ()->new RecipeType<>(){
        @Override
        public String toString() {
            return "gronckle_smelting";
        }
    });

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering recipie types for {}!", NotEnoughDragons.MOD_NAME);
    }

    public static <T extends Recipe<?>> Supplier<RecipeType<T>> register(String id, Supplier<RecipeType<T>> recipeSerializer){
        return Services.PLATFORM.getRegisterHelper().register(BuiltInRegistries.RECIPE_TYPE, id,recipeSerializer);
    }
}
