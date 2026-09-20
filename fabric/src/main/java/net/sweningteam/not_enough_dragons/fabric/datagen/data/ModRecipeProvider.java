package net.sweningteam.not_enough_dragons.fabric.datagen.data;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CookingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.sweningteam.not_enough_dragons.common.registry.ModBlocks;
import net.sweningteam.not_enough_dragons.common.registry.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider provider, BootstrapContext<Recipe<?>> bootstrapContext, BootstrapContext<Advancement> bootstrapContext1) {
        return new RecipeProvider(bootstrapContext,bootstrapContext1) {
            @Override
            public void buildRecipes() {
                oreBlasting(
                        List.of(ModItems.MOLTEN_GROCKLE_IRON.get()),
                        RecipeCategory.MISC,
                        CookingBookCategory.MISC,
                        ModItems.MOLTEN_RAW_GROCKLE_IRON.get(),
                        1,
                        400,
                        "gronckle_iron"
                );
                shapeless(RecipeCategory.MISC, ModItems.RAW_GROCKLE_IRON.get())
                        .requires(ModItems.MOLTEN_RAW_GROCKLE_IRON.get())
                        .requires(Items.WATER_BUCKET)
                        .unlockedBy(getHasName(ModItems.MOLTEN_RAW_GROCKLE_IRON.get()), has(ModItems.MOLTEN_RAW_GROCKLE_IRON.get()))
                        .save(output);
                oreBlasting(
                        List.of(ModItems.RAW_GROCKLE_IRON.get()),
                        RecipeCategory.MISC,
                        CookingBookCategory.MISC,
                        ModItems.GRONCKLE_IRON_INGOT.get(),
                        1,
                        400,
                        "gronckle_iron"
                );
                nineBlockStorageRecipes(RecipeCategory.MISC,ModItems.GRONCKLE_IRON_NUGGET.get(),RecipeCategory.MISC,ModItems.GRONCKLE_IRON_INGOT.get());
                nineBlockStorageRecipes(RecipeCategory.MISC,ModItems.GRONCKLE_IRON_INGOT.get(),RecipeCategory.BUILDING_BLOCKS,ModBlocks.GRONCKLE_IRON_BLOCK.get(),"gronckle_iron_block_from_gronckle_iron","gronckle_iron","gronckle_iron","gronckle_iron_from_gronckle_iron_block");
                oreBlasting(
                        List.of(ModItems.MOLTEN_DRAGON_PROVED_STEAL.get()),
                        RecipeCategory.MISC,
                        CookingBookCategory.MISC,

                        ModItems.DRAGON_PROVED_STEAL_SCRAPS.get(),
                        1,
                        400,
                        "dragon_proved_steal"
                );
                stairBuilder(ModBlocks.GRONCKLE_IRON_STAIRS.get(), Ingredient.of(ModBlocks.GRONCKLE_IRON_BLOCK.get()));
                slab(RecipeCategory.BUILDING_BLOCKS,ModBlocks.GRONCKLE_IRON_SLAB.get(),ModBlocks.GRONCKLE_IRON_BLOCK.get());
                doorBuilder(ModBlocks.GRONCKLE_IRON_DOOR.get(),Ingredient.of(ModBlocks.GRONCKLE_IRON_BLOCK.get()));
                trapdoorBuilder(ModBlocks.GRONCKLE_IRON_TRAPDOOR.get(),Ingredient.of(ModBlocks.GRONCKLE_IRON_BLOCK.get()));
                trapdoorBuilder(ModBlocks.GRONCKLE_IRON_BARS.get(),Ingredient.of(ModItems.GRONCKLE_IRON_INGOT.get()));
                stairBuilder(ModBlocks.GRONCKLE_IRON_STAIRS.get(), Ingredient.of(ModBlocks.GRONCKLE_IRON_BLOCK.get()));
                stairBuilder(ModBlocks.GRONCKLE_IRON_STAIRS.get(), Ingredient.of(ModBlocks.GRONCKLE_IRON_BLOCK.get()));
                shaped(RecipeCategory.MISC, ModItems.DRAGON_PROVED_STEAL_INGOT.get())
                        .pattern("SSS")
                        .pattern("S I")
                        .pattern("III")
                        .define('S',ModItems.DRAGON_PROVED_STEAL_SCRAPS.get())
                        .define('I',Items.IRON_INGOT)
                        .unlockedBy(getHasName(ModItems.DRAGON_PROVED_STEAL_SCRAPS.get()), has(ModItems.DRAGON_PROVED_STEAL_SCRAPS.get()))
                        .save(output);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModBlocks.GRONCKLE_IRON_CHAIN.get())
                        .pattern("N")
                        .pattern("I")
                        .pattern("N")
                        .define('N',ModItems.GRONCKLE_IRON_NUGGET.get())
                        .define('I',ModItems.GRONCKLE_IRON_INGOT.get())
                        .unlockedBy(getHasName(ModItems.GRONCKLE_IRON_INGOT.get()), has(ModItems.GRONCKLE_IRON_INGOT.get()))
                        .save(output);

                stairBuilder(ModBlocks.DRAGON_PROVED_STEAL_STAIRS.get(), Ingredient.of(ModBlocks.DRAGON_PROVED_STEAL_BLOCK.get()));
                slab(RecipeCategory.BUILDING_BLOCKS,ModBlocks.DRAGON_PROVED_STEAL_SLAB.get(),ModBlocks.DRAGON_PROVED_STEAL_BLOCK.get());

                shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.GRONCKLE_IRON_SWORD.get())
                        .pattern("G")
                        .pattern("G")
                        .pattern("/")
                        .define('G',ModItems.GRONCKLE_IRON_INGOT.get())
                        .define('/',Items.STICK)
                        .unlockedBy(getHasName(ModItems.GRONCKLE_IRON_INGOT.get()), has(ModItems.GRONCKLE_IRON_INGOT.get()))
                        .save(output);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.GRONCKLE_IRON_SHOVEL.get())
                        .pattern("G")
                        .pattern("/")
                        .pattern("/")
                        .define('G',ModItems.GRONCKLE_IRON_INGOT.get())
                        .define('/',Items.STICK)
                        .unlockedBy(getHasName(ModItems.GRONCKLE_IRON_INGOT.get()), has(ModItems.GRONCKLE_IRON_INGOT.get()))
                        .save(output);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.GRONCKLE_IRON_PICKAXE.get())
                        .pattern("GGG")
                        .pattern(" / ")
                        .pattern(" / ")
                        .define('G',ModItems.GRONCKLE_IRON_INGOT.get())
                        .define('/',Items.STICK)
                        .unlockedBy(getHasName(ModItems.GRONCKLE_IRON_INGOT.get()), has(ModItems.GRONCKLE_IRON_INGOT.get()))
                        .save(output);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.GRONCKLE_IRON_AXE.get())
                        .pattern("GG")
                        .pattern("G/")
                        .pattern(" /")
                        .define('G',ModItems.GRONCKLE_IRON_INGOT.get())
                        .define('/',Items.STICK)
                        .unlockedBy(getHasName(ModItems.GRONCKLE_IRON_INGOT.get()), has(ModItems.GRONCKLE_IRON_INGOT.get()))
                        .save(output);

                shaped(RecipeCategory.BUILDING_BLOCKS, ModItems.GRONCKLE_IRON_HOE.get())
                        .pattern("GG")
                        .pattern(" /")
                        .pattern(" /")
                        .define('G',ModItems.GRONCKLE_IRON_INGOT.get())
                        .define('/',Items.STICK)
                        .unlockedBy(getHasName(ModItems.GRONCKLE_IRON_INGOT.get()), has(ModItems.GRONCKLE_IRON_INGOT.get()))
                        .save(output);
            }
        };
    }

    @Override
    public String getName() {
        return "";
    }
}
