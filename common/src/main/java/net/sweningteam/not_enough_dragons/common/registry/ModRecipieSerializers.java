package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.recipie.Gronckle.GroncklRecipie;
import net.sweningteam.not_enough_dragons.platform.Services;

import java.util.function.Supplier;

public class ModRecipieSerializers {
    public static final Supplier<RecipeSerializer<GroncklRecipie>> GRONCKLE_RECIPIE = register("gronckle_smelting", ()-> new RecipeSerializer<>(GroncklRecipie.CODEC, GroncklRecipie.STREAM_CODEC));

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering recipie serializers for {}!", NotEnoughDragons.MOD_NAME);
    }

    public static <T extends Recipe<?>> Supplier<RecipeSerializer<T>> register(String id, Supplier<RecipeSerializer<T>> recipeSerializer){
        return Services.PLATFORM.getRegisterHelper().register(BuiltInRegistries.RECIPE_SERIALIZER, id,recipeSerializer);
    }
}
