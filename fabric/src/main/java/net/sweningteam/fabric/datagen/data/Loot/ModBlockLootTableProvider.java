package net.sweningteam.fabric.datagen.data.Loot;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.SlabType;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.entries.LootPoolEntryContainer;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.sweningteam.common.registry.ModBlocks;
import net.sweningteam.common.registry.ModItems;

import java.util.concurrent.CompletableFuture;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {

    public ModBlockLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        add(ModBlocks.NIGHT_FURY_SCALES.get(),createSegmentedBlockDrops(ModBlocks.NIGHT_FURY_SCALES.get()));
        dropSelf(ModBlocks.GRONCKLE_IRON_BLOCK.get());
        dropOther(ModBlocks.MOLTEN_GRONCKLE_IRON_BLOCK.get(), ModItems.MOLTEN_GROCKLE_IRON.get());
        createOtherDroppingSlabDrop(ModBlocks.MOLTEN_GRONCKLE_IRON_LAVA.get(),ModItems.MOLTEN_GROCKLE_IRON.get() );
        dropSelf(ModBlocks.GRONCKLE_IRON_STAIRS.get());
        dropSelf(ModBlocks.BOOK_OF_DRAGONS.get());
        dropSelf(ModBlocks.GRONCKLE_IRON_TRAPDOOR.get());
        dropSelf(ModBlocks.GRONCKLE_IRON_CHAIN.get());
        createSlabItemTable(ModBlocks.GRONCKLE_IRON_SLAB.get());
        dropSelf(ModBlocks.GRONCKLE_IRON_BARS.get());
        createDoorTable(ModBlocks.GRONCKLE_IRON_DOOR.get());
    }

    public LootTable.Builder createOtherDroppingSlabDrop(Block block, ItemLike output) {
        return LootTable.lootTable()
                .withPool(LootPool.lootPool()
                        .setRolls(ConstantValue.exactly(1.0F))
                        .add((LootPoolEntryContainer.Builder)this.applyExplosionDecay(block, LootItem.lootTableItem(output).apply(SetItemCountFunction.setCount(ConstantValue.exactly(2.0F)).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block).setProperties(net.minecraft.advancements.critereon.StatePropertiesPredicate.Builder.properties().hasProperty(SlabBlock.TYPE, SlabType.DOUBLE)))))));
    }
}
