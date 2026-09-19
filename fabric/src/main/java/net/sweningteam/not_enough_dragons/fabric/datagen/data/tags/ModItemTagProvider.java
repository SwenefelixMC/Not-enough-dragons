package net.sweningteam.not_enough_dragons.fabric.datagen.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.sweningteam.not_enough_dragons.common.registry.ModItems;
import net.sweningteam.not_enough_dragons.common.registry.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagsProvider.ItemTagsProvider {
    public ModItemTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    private Identifier getKey(Item item){
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

        getOrCreateRawBuilder(ModTags.Items.REPAIRS_GRONCKLE_IRON)
                .addElement(getKey(ModItems.GRONCKLE_IRON_INGOT.get()));

        getOrCreateRawBuilder(ItemTags.SWORDS)
                .addElement(getKey(ModItems.GRONCKLE_IRON_SWORD.get()));

        getOrCreateRawBuilder(ItemTags.SHOVELS)
                .addElement(getKey(ModItems.GRONCKLE_IRON_SHOVEL.get()));

        getOrCreateRawBuilder(ItemTags.PICKAXES)
                .addElement(getKey(ModItems.GRONCKLE_IRON_PICKAXE.get()));

        getOrCreateRawBuilder(ItemTags.AXES)
                .addElement(getKey(ModItems.GRONCKLE_IRON_AXE.get()));

        getOrCreateRawBuilder(ItemTags.HOES)
                .addElement(getKey(ModItems.GRONCKLE_IRON_HOE.get()));
    }
}
