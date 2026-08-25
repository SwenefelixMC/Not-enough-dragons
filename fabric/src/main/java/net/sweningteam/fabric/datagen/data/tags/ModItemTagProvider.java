package net.sweningteam.fabric.datagen.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.sweningteam.common.registry.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    private ResourceLocation getKey(Item item){
        return BuiltInRegistries.ITEM.getKey(item);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateRawBuilder(ModTags.Items.DRAGON_PROVOCATIVE)
                .addOptionalTag(ItemTags.SWORDS.location())
                .addOptionalTag(ItemTags.AXES.location())
                .addOptionalTag(ItemTags.ARROWS.location())
                .addElement(getKey(Items.BOW))
                .addElement(getKey(Items.CROSSBOW))
                .addElement(getKey(Items.LAVA_BUCKET))
                .addElement(getKey(Items.TOTEM_OF_UNDYING))
                .addElement(getKey(Items.FLINT_AND_STEEL))
                .addElement(getKey(Items.TNT));
    }
}
