package net.sweningteam.not_enough_dragons.fabric.datagen.assets;

import com.terraformersmc.modmenu.util.mod.Mod;
import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TexturedModel;
import net.sweningteam.not_enough_dragons.common.registry.ModBlocks;
import net.sweningteam.not_enough_dragons.common.registry.ModItems;

public class ModModelProvider extends FabricModelProvider {


    public ModModelProvider(FabricPackOutput output) {
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

        blockModelGenerators.family(ModBlocks.GRONCKLE_LAVA_BLOCK_LAVA.get())
                .slab(ModBlocks.GRONCKLE_LAVA.get());
        blockModelGenerators.family(ModBlocks.MOLTEN_GRONCKLE_IRON_BLOCK.get())
                .slab(ModBlocks.MOLTEN_GRONCKLE_IRON_LAVA.get());

        blockModelGenerators.family(ModBlocks.DRAGON_PROVED_STEEL_BLOCK.get())
                .stairs(ModBlocks.DRAGON_PROVED_STEEL_STAIRS.get())
                .slab(ModBlocks.DRAGON_PROVED_STEEL_SLAB.get());

        blockModelGenerators.createCrossBlock(ModBlocks.DRAGON_NIP.get(), BlockModelGenerators.PlantType.NOT_TINTED);
        blockModelGenerators.registerSimpleFlatItemModel(ModBlocks.DRAGON_NIP.get().asItem());

        blockModelGenerators.createLeafLitter(ModBlocks.GRONCKLE_SCALES.get());

        blockModelGenerators.createTrivialBlock(ModBlocks.VIKING_TRADER_TABLE.get(), TexturedModel.CUBE_TOP);
    }

    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerators) {
        itemModelGenerators.generateFlatItem(ModItems.NIGHT_FURY.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GOLD_COIN.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.NIGHT_FURY_EGG.get(), ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.GRONCKLE.get(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GRONCKLE_EGG.get(), ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.MOLTEN_GROCKLE_IRON.get(),ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.MOLTEN_RAW_GROCKLE_IRON.get(),ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.RAW_GROCKLE_IRON.get(),ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GRONCKLE_IRON_INGOT.get(),ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GRONCKLE_IRON_NUGGET.get(),ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.MOLTEN_DRAGON_PROVED_STEEL.get(),ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.DRAGON_PROVED_STEEL_SCRAPS.get(),ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.DRAGON_PROVED_STEEL_INGOT.get(),ModelTemplates.FLAT_ITEM);

        itemModelGenerators.generateFlatItem(ModItems.GRONCKLE_IRON_SWORD.get(),ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GRONCKLE_IRON_SHOVEL.get(),ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GRONCKLE_IRON_PICKAXE.get(),ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GRONCKLE_IRON_AXE.get(),ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.GRONCKLE_IRON_HOE.get(),ModelTemplates.FLAT_HANDHELD_ITEM);

        itemModelGenerators.generateFlatItem(ModBlocks.DRAGON_MANUAL.get().asItem(), ModelTemplates.FLAT_ITEM);
    }
}
