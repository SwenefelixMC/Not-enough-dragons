package net.sweningteam.fabric.datagen.assets;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.data.models.model.TexturedModel;
import net.minecraft.world.level.block.Blocks;
import net.sweningteam.common.registry.ModBlocks;
import net.sweningteam.common.registry.ModItems;

public class ModModelProvider extends FabricModelProvider {


    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockModelGenerators) {
        blockModelGenerators.createLeafLitter(ModBlocks.NIGHT_FURY_SCALES.get());

        blockModelGenerators.family(ModBlocks.GRONCKLE_IRON_BLOCK.get())
                .stairs(ModBlocks.GRONCKLE_IRON_STAIRS.get())
                .slab(ModBlocks.GRONCKLE_IRON_SLAB.get());

        blockModelGenerators.createDoor(ModBlocks.GRONCKLE_IRON_DOOR.get());
        blockModelGenerators.createTrapdoor(ModBlocks.GRONCKLE_IRON_TRAPDOOR.get());

        blockModelGenerators.createAxisAlignedPillarBlockCustomModel(ModBlocks.GRONCKLE_IRON_CHAIN.get(), BlockModelGenerators.plainVariant(TexturedModel.CHAIN.create(ModBlocks.GRONCKLE_IRON_CHAIN.get(), blockModelGenerators.modelOutput)));

        blockModelGenerators.createBarsAndItem(ModBlocks.GRONCKLE_IRON_BARS.get());

        blockModelGenerators.family(ModBlocks.RAW_GRONCKLE_IRON_BLOCK.get())
                .stairs(ModBlocks.RAW_GRONCKLE_IRON_STAIRS.get())
                .slab(ModBlocks.RAW_GRONCKLE_IRON_SLAB.get());

        blockModelGenerators.family(ModBlocks.GRONCKLE_LAVA_BLOCK.get())
                .slab(ModBlocks.GRONCKLE_LAVA.get());
        blockModelGenerators.family(ModBlocks.MOLTEN_GRONCKLE_IRON_BLOCK.get())
                .slab(ModBlocks.MOLTEN_GRONCKLE_IRON.get());
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.createFlatItemModel(ModItems.NIGHT_FURY.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.createFlatItemModel(ModItems.GOLD_COIN.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.createFlatItemModel(ModItems.NIGHT_FURY_EGG.get(), ModelTemplates.FLAT_ITEM);
    }
}
