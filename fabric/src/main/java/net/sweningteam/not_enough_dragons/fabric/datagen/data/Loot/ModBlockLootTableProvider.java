package net.sweningteam.not_enough_dragons.fabric.datagen.data.Loot;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootSubProvider;
import net.minecraft.core.HolderLookup;
import net.sweningteam.not_enough_dragons.common.registry.ModBlocks;
import net.sweningteam.not_enough_dragons.common.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootSubProvider {

    public ModBlockLootTableProvider(FabricPackOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        add(ModBlocks.NIGHT_FURY_SCALES.get(),createSegmentedBlockDrops(ModBlocks.NIGHT_FURY_SCALES.get()));
        dropSelf(ModBlocks.GRONCKLE_IRON_BLOCK.get());
        dropOther(ModBlocks.MOLTEN_GRONCKLE_IRON_BLOCK.get(), ModItems.MOLTEN_GROCKLE_IRON.get());
        //createOtherDroppingSlabDrop(ModBlocks.MOLTEN_GRONCKLE_IRON_LAVA.get(),ModItems.MOLTEN_GROCKLE_IRON.get() );
        dropSelf(ModBlocks.GRONCKLE_IRON_STAIRS.get());
        dropSelf(ModBlocks.DRAGON_MANUAL.get());
        dropSelf(ModBlocks.GRONCKLE_IRON_TRAPDOOR.get());
        dropSelf(ModBlocks.GRONCKLE_IRON_CHAIN.get());
        createSlabItemTable(ModBlocks.GRONCKLE_IRON_SLAB.get());
        dropSelf(ModBlocks.GRONCKLE_IRON_BARS.get());
        createDoorTable(ModBlocks.GRONCKLE_IRON_DOOR.get());

        dropSelf(ModBlocks.DRAGON_PROVED_STEAL_BLOCK.get());
        dropSelf(ModBlocks.DRAGON_PROVED_STEAL_STAIRS.get());
        createSlabItemTable(ModBlocks.DRAGON_PROVED_STEAL_SLAB.get());

        add(ModBlocks.GRONCKLE_SCALES.get(),createSegmentedBlockDrops(ModBlocks.GRONCKLE_SCALES.get()));
    }
}
