package net.sweningteam.common.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.block.AccseableBarsBlock;
import net.sweningteam.common.block.AccseableStairsBlock;
import net.sweningteam.common.block.AccsesableDoorBlock;
import net.sweningteam.common.block.AccsesableTrapDoorBlock;
import net.sweningteam.platform.Services;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ModBlocks {

    public static final ArrayList<Supplier<? extends Block>> BLOCKS = new ArrayList<>();

    public static final Supplier<Block> NIGHT_FURY_SCALES = registerBlockWithItem("night_fury_scales", ()->new LeafLitterBlock(baseProperties("night_fury_scales").sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_BLACK).pushReaction(PushReaction.DESTROY).noCollision()));
    public static final Supplier<Block> GRONCKLE_IRON_BLOCK = registerBlockWithItem("gronckle_iron_block", ()-> new Block(baseProperties("gronckle_iron_block").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_IRON_STAIRS = registerBlockWithItem("gronckle_iron_stairs", ()-> new AccseableStairsBlock(GRONCKLE_IRON_BLOCK.get().defaultBlockState(), baseProperties("gronckle_iron_stairs").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_IRON_SLAB = registerBlockWithItem("gronckle_iron_slab", ()-> new SlabBlock(baseProperties("gronckle_iron_slab").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_IRON_BARS = registerBlockWithItem("gronckle_iron_bars", ()-> new AccseableBarsBlock(baseProperties("gronckle_iron_bars").noOcclusion().sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_IRON_DOOR = registerBlockWithItem("gronckle_iron_door", ()-> new AccsesableDoorBlock(BlockSetType.IRON,baseProperties("gronckle_iron_slab").noOcclusion().sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_IRON_TRAPDOOR = registerBlockWithItem("gronckle_iron_trapdoor",()-> new AccsesableTrapDoorBlock(BlockSetType.IRON,baseProperties("gronckle_iron_trapdoor").noOcclusion().sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_IRON_CHAIN = registerBlockWithItem("gronckle_iron_chain", ()-> new ChainBlock(baseProperties("gronckle_iron_chain").noOcclusion().noOcclusion().sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_IRON_CHAIN_2 = registerBlockWithItem("gronckle_iron_chain_2", ()-> new ChainBlock(baseProperties("gronckle_iron_chain_2").noOcclusion().sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> RAW_GRONCKLE_IRON_BLOCK = registerBlockWithItem("raw_gronckle_iron_block", ()-> new Block(baseProperties("raw_gronckle_iron_block").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> RAW_GRONCKLE_IRON_STAIRS = registerBlockWithItem("raw_gronckle_iron_stairs", ()-> new AccseableStairsBlock(RAW_GRONCKLE_IRON_BLOCK.get().defaultBlockState(), baseProperties("raw_gronckle_iron_stairs").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> RAW_GRONCKLE_IRON_SLAB = registerBlockWithItem("raw_gronckle_iron_slab", ()-> new SlabBlock(baseProperties("raw_gronckle_iron_slab").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    //This is only here inorder to make Datagen Easier
    public static final Supplier<Block> MOLTEN_GRONCKLE_IRON_BLOCK = registerBlock("molten_gronckle_iron_block",()->new Block(baseProperties("molten_gronckle_iron_block")));
    public static final Supplier<Block> GRONCKLE_LAVA_BLOCK = registerBlock("gronckle_lava_block",()->new Block(baseProperties("gronckle_lava_block")));

    public static final Supplier<Block> MOLTEN_GRONCKLE_IRON = registerBlockWithItem("molten_gronckle_iron", ()-> new SlabBlock(baseProperties("molten_gronckle_iron").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_LAVA = registerBlockWithItem("gronckle_lava",()-> new SlabBlock(baseProperties("gronckle_lava").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));

    public static BlockBehaviour.Properties baseProperties(String id){
        return BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, id)));
    }

    public static <B extends Block> Supplier<B> registerBlockWithItem(String id, Supplier<B> block){
        Supplier<B> blockSupplier = registerBlock(id,block);
        Supplier<Item> item = ModItems.registerItem(id, ()-> new BlockItem(blockSupplier.get(), ModItems.baseProperties(id)));
        return blockSupplier;
    }

    public static <B extends Block> Supplier<B> registerBlock(String id, Supplier<B> block){
        Supplier<B> blockSupplier = register(id,block);
        BLOCKS.add(blockSupplier);
        return blockSupplier;
    }

    public static <B extends Block> Supplier<B> register(String id, Supplier<B> block){
        return Services.PLATFORM.getRegisterHelper().register(BuiltInRegistries.BLOCK, id, block);
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Blocks for {}", NotEnoughDragons.MOD_NAME);
    }
}

