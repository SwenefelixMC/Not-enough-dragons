package net.sweningteam.not_enough_dragons.common.datagen.recipies;

import net.minecraft.advancements.triggers.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeUnlockAdvancementBuilder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.level.ItemLike;
import net.sweningteam.not_enough_dragons.common.recipie.Gronckle.GroncklRecipie;
import org.jspecify.annotations.Nullable;

import java.util.List;

public class GronckleRecipieBuilder implements RecipeBuilder {
    //This is in common for addons because not enough dragons uses Fabric datagen,
    //but I don't want to force developers to use Fabric datagen as well
    //so the Mod only uses it on Fabric, but you can use it on NeoForge too
    private final RecipeCategory category;
    private final ItemStackTemplate result;
    private final List<Ingredient> Ingrediant;
    private final RecipeUnlockAdvancementBuilder advancementBuilder = new RecipeUnlockAdvancementBuilder();
    private @Nullable String group;

    public GronckleRecipieBuilder(RecipeCategory category, List<Ingredient> ingrediant, ItemStackTemplate result) {
        this.category = category;
        this.result = result;
        this.Ingrediant = ingrediant;
    }

    public static GronckleRecipieBuilder gronckleRecipie(RecipeCategory category, List<Ingredient> ingrediant, ItemLike item, int count) {
        return new GronckleRecipieBuilder(category, ingrediant, new ItemStackTemplate(item.asItem(),count));
    }

    public static GronckleRecipieBuilder gronckleRecipie(RecipeCategory category, List<Ingredient> ingrediant, ItemLike item) {
        return new GronckleRecipieBuilder(category, ingrediant, new ItemStackTemplate(item.asItem()));
    }

    @Override
    public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
        advancementBuilder.unlockedBy(name, criterion);
        return this;
    }

    @Override
    public RecipeBuilder group(@Nullable String group) {
        this.group = group;
        return this;
    }

    @Override
    public @Nullable ResourceKey<Recipe<?>> defaultId() {
        return RecipeBuilder.getDefaultRecipeId(this.result);
    }

    @Override
    public void save(RecipeOutput output, ResourceKey<Recipe<?>> location) {
        GroncklRecipie recipie = new GroncklRecipie(this.Ingrediant,this.result);
        output.accept(location, recipie, this.advancementBuilder.build(output, location, this.category));
    }
}
