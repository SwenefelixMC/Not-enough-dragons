package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.block.DragonNipBlock;
import net.sweningteam.not_enough_dragons.common.block.accses.AccesableIronBarsBlock;
import net.sweningteam.not_enough_dragons.common.block.accses.AccesableStairsBlock;
import net.sweningteam.not_enough_dragons.common.block.accses.AccesableTrapdoorBlock;
import net.sweningteam.not_enough_dragons.common.block.accses.AccsesibleDoorBlock;
import net.sweningteam.not_enough_dragons.platform.Services;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ModBlocks {

    public static final ArrayList<Supplier<? extends Block>> BLOCKS = new ArrayList<>();

    public static final Supplier<Block> NIGHT_FURY_SCALES = registerBlockWithFireResistantItem("night_fury_scales", ()->new LeafLitterBlock(baseProperties("night_fury_scales").sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_BLACK).pushReaction(PushReaction.DESTROY).noCollision()));
    public static final Supplier<Block> GRONCKLE_IRON_BLOCK = registerBlockWithFireResistantItem("gronckle_iron_block", ()-> new Block(baseProperties("gronckle_iron_block").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_IRON_STAIRS = registerBlockWithFireResistantItem("gronckle_iron_stairs", ()-> new AccesableStairsBlock(GRONCKLE_IRON_BLOCK.get().defaultBlockState(), baseProperties("gronckle_iron_stairs").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_IRON_SLAB = registerBlockWithFireResistantItem("gronckle_iron_slab", ()-> new SlabBlock(baseProperties("gronckle_iron_slab").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_IRON_BARS = registerBlockWithFireResistantItem("gronckle_iron_bars", ()-> new AccesableIronBarsBlock(baseProperties("gronckle_iron_bars").noOcclusion().sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_IRON_DOOR = registerBlockWithFireResistantItem("gronckle_iron_door", ()-> new AccsesibleDoorBlock(BlockSetType.IRON,baseProperties("gronckle_iron_slab").noOcclusion().sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_IRON_TRAPDOOR = registerBlockWithFireResistantItem("gronckle_iron_trapdoor",()-> new AccesableTrapdoorBlock(BlockSetType.IRON,baseProperties("gronckle_iron_trapdoor").noOcclusion().sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_IRON_CHAIN = registerBlockWithFireResistantItem("gronckle_iron_chain", ()-> new ChainBlock(baseProperties("gronckle_iron_chain").noOcclusion().noOcclusion().sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> RAW_GRONCKLE_IRON_BLOCK = registerBlockWithFireResistantItem("raw_gronckle_iron_block", ()-> new Block(baseProperties("raw_gronckle_iron_block").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> RAW_GRONCKLE_IRON_STAIRS = registerBlockWithFireResistantItem("raw_gronckle_iron_stairs", ()-> new AccesableStairsBlock(RAW_GRONCKLE_IRON_BLOCK.get().defaultBlockState(), baseProperties("raw_gronckle_iron_stairs").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> RAW_GRONCKLE_IRON_SLAB = registerBlockWithFireResistantItem("raw_gronckle_iron_slab", ()-> new SlabBlock(baseProperties("raw_gronckle_iron_slab").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));

    public static final Supplier<Block> MOLTEN_GRONCKLE_IRON_BLOCK = registerBlockWithFireResistantItem("molten_gronckle_iron_block",()->new Block(baseProperties("molten_gronckle_iron_block")));
    public static final Supplier<Block> GRONCKLE_LAVA_BLOCK_LAVA = registerBlockWithFireResistantItem("gronckle_lava_block_lava",()->new Block(baseProperties("gronckle_lava_block_lava")));

    public static final Supplier<Block> MOLTEN_GRONCKLE_IRON_LAVA = registerBlockWithFireResistantItem("molten_gronckle_iron_lava", ()-> new SlabBlock(baseProperties("molten_gronckle_iron_lava").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> GRONCKLE_LAVA = registerBlockWithFireResistantItem("gronckle_lava",()-> new SlabBlock(baseProperties("gronckle_lava").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));

    public static final Supplier<Block> DRAGON_PROVED_STEAL_BLOCK = registerBlockWithFireResistantItem("dragon_proved_steal_block", ()-> new Block(baseProperties("dragon_proved_steal_block").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> DRAGON_PROVED_STEAL_STAIRS = registerBlockWithFireResistantItem("dragon_proved_steal_stairs", ()-> new AccesableStairsBlock(GRONCKLE_IRON_BLOCK.get().defaultBlockState(), baseProperties("dragon_proved_steal_stairs").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));
    public static final Supplier<Block> DRAGON_PROVED_STEAL_SLAB = registerBlockWithFireResistantItem("dragon_proved_steal_slab", ()-> new SlabBlock(baseProperties("dragon_proved_steal_slab").sound(SoundType.COPPER).requiresCorrectToolForDrops().mapColor(MapColor.METAL).strength(50.0F, 1200.0F)));

    public static final Supplier<Block> BOOK_OF_DRAGONS = registerBlockWithItem("book_of_dragons",()-> new Block(baseProperties("book_of_dragons").sound(SoundType.WOOL).mapColor(MapColor.COLOR_BROWN)));

    public static final Supplier<Block> GRONCKLE_SCALES = registerBlockWithFireResistantItem("gronckle_scales", ()->new LeafLitterBlock(baseProperties("gronckle_scales").sound(SoundType.DEEPSLATE).mapColor(MapColor.COLOR_BROWN).pushReaction(PushReaction.DESTROY).noCollision()));

    public static final Supplier<Block> DRAGON_NIP = registerBlockWithItem("dragon_nip_block",()->new DragonNipBlock(baseProperties("dragon_nip_block").noCollision().noOcclusion().sound(SoundType.GRASS)));

    public static BlockBehaviour.Properties baseProperties(String id){
        return BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, id)));
    }

    public static <B extends Block> Supplier<B> registerBlockWithItem(String id, Supplier<B> block){
        Supplier<B> blockSupplier = registerBlock(id,block);
        Supplier<Item> item = ModItems.registerItem(id, ()-> new BlockItem(blockSupplier.get(), ModItems.baseProperties(id)));
        return blockSupplier;
    }

    public static <B extends Block> Supplier<B> registerBlockWithFireResistantItem(String id, Supplier<B> block){
        Supplier<B> blockSupplier = registerBlock(id,block);
        Supplier<Item> item = ModItems.registerItem(id, ()-> new BlockItem(blockSupplier.get(), ModItems.baseProperties(id).fireResistant()));
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

