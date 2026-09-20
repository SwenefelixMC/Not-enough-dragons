package net.sweningteam.not_enough_dragons.fabric.datagen.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.sweningteam.not_enough_dragons.common.registry.ModBlocks;
import net.sweningteam.not_enough_dragons.common.registry.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagsProvider.BlockTagsProvider {
    public ModBlockTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    private Identifier getKey(Block block){
        return BuiltInRegistries.BLOCK.getKey(block);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateRawBuilder(BlockTags.NEEDS_IRON_TOOL)
                .addElement(getKey(ModBlocks.GRONCKLE_IRON_BLOCK.get()))
                .addElement(getKey(ModBlocks.GRONCKLE_IRON_STAIRS.get()))
                .addElement(getKey(ModBlocks.DRAGON_MANUAL.get()))
                .addElement(getKey(ModBlocks.GRONCKLE_IRON_TRAPDOOR.get()))
                .addElement(getKey(ModBlocks.GRONCKLE_IRON_CHAIN.get()))
                .addElement(getKey(ModBlocks.GRONCKLE_IRON_SLAB.get()))
                .addElement(getKey(ModBlocks.GRONCKLE_IRON_BARS.get()))
                .addElement(getKey(ModBlocks.GRONCKLE_IRON_DOOR.get()));

        getOrCreateRawBuilder(ModTags.Blocks.DRAGONS_SPAWNS_ON)
                .addOptionalTag(BlockTags.ANIMALS_SPAWNABLE_ON.location())
                .addElement(getKey(Blocks.STONE))
                .addElement(getKey(Blocks.MOSS_BLOCK))
                .addElement(getKey(Blocks.MOSS_CARPET))
                .addElement(getKey(Blocks.SNOW))
                .addElement(getKey(Blocks.SNOW_BLOCK))
                .addElement(getKey(Blocks.PACKED_ICE))
                .addElement(getKey(Blocks.GRAVEL))
                .addElement(getKey(Blocks.CALCITE));


    }
}
