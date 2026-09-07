package net.sweningteam.fabric.datagen.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.BlastingRecipe;
import net.sweningteam.common.registry.ModBlocks;
import net.sweningteam.common.registry.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected @NotNull RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup,exporter) {
            @Override
            public void buildRecipes() {
                oreBlasting(
                        List.of(ModItems.MOLTEN_GROCKLE_IRON.get()),
                        RecipeCategory.MISC,
                        ModItems.MOLTEN_RAW_GROCKLE_IRON.get(),
                        1,
                        400,
                        "gronckle_iron"
                );
                shapeless(RecipeCategory.MISC, ModItems.RAW_GROCKLE_IRON.get())
                        .requires(ModItems.MOLTEN_RAW_GROCKLE_IRON.get())
                        .requires(Items.WATER_BUCKET)
                        .unlockedBy(getHasName(ModItems.MOLTEN_RAW_GROCKLE_IRON.get()), has(ModItems.MOLTEN_RAW_GROCKLE_IRON.get()))
                        .save(exporter);
                oreBlasting(
                        List.of(ModItems.RAW_GROCKLE_IRON.get()),
                        RecipeCategory.MISC,
                        ModItems.GROCKLE_IRON_INGOT.get(),
                        1,
                        400,
                        "gronckle_iron"
                );
                nineBlockStorageRecipes(RecipeCategory.MISC,ModItems.GROCKLE_IRON_NUGGET.get(),RecipeCategory.MISC,ModItems.GROCKLE_IRON_INGOT.get());
            }
        };
    }

    @Override
    public String getName() {
        return "";
    }
}
