package net.sweningteam.not_enough_dragons.fabric.datagen.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponent;
import net.sweningteam.not_enough_dragons.common.registry.ModRegistries;
import net.sweningteam.not_enough_dragons.common.registry.ModTags;
import net.sweningteam.not_enough_dragons.common.registry.ModDragonFoodComponents;

import java.util.concurrent.CompletableFuture;

public class ModDragonFoodComponentTagProvider extends FabricTagsProvider<DragonFoodComponent> {
    public ModDragonFoodComponentTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, ModRegistries.DRAGON_FOOD_COMPONENT, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateRawBuilder(ModTags.DragonFoodComponents.NIGHT_FURY)
                .addElement(ModDragonFoodComponents.NIGHT_FURY_COD.identifier())
                .addElement(ModDragonFoodComponents.NIGHT_FURY_SALMON.identifier())
                .addElement(ModDragonFoodComponents.NIGHT_FURY_TROPICAL_FISH.identifier())
                .addElement(ModDragonFoodComponents.NIGHT_FURY_PUFFER_FISH.identifier())
                .addElement(ModDragonFoodComponents.NIGHT_FURY_COOKED_COD.identifier())
                .addElement(ModDragonFoodComponents.NIGHT_FURY_COOKED_SALMON.identifier());
    }
}
