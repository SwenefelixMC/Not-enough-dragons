package net.sweningteam.not_enough_dragons.common.registry;


import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.platform.Services;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ModCreativeTabs {
    private static ArrayList<Supplier<? extends Item>> NotEnoughDragonsDragonsContent(){
        ArrayList<Supplier<? extends Item>> NotEnoughDragonsDragonsContent = new ArrayList<>();
        NotEnoughDragonsDragonsContent.add(ModItems.NIGHT_FURY);
        NotEnoughDragonsDragonsContent.add(ModItems.NIGHT_FURY_EGG);
        NotEnoughDragonsDragonsContent.add(()-> ModBlocks.NIGHT_FURY_SCALES.get().asItem());
        NotEnoughDragonsDragonsContent.add(ModItems.GRONCKLE);
        NotEnoughDragonsDragonsContent.add(ModItems.GRONCKLE_EGG);
        NotEnoughDragonsDragonsContent.add(()-> ModBlocks.GRONCKLE_SCALES.get().asItem());
        return NotEnoughDragonsDragonsContent;
    }
    private static ArrayList<Supplier<? extends Item>> NotEnoughDragonsMiscItemsContent(){
        ArrayList<Supplier<? extends Item>> NotEnoughDragonsMiscItemsContent = new ArrayList<>();
        NotEnoughDragonsMiscItemsContent.add(()->ModBlocks.DRAGON_MANUAL.get().asItem());
        NotEnoughDragonsMiscItemsContent.add(ModItems.DRAGON_NIP);
        NotEnoughDragonsMiscItemsContent.add(ModItems.GOLD_COIN);
        NotEnoughDragonsMiscItemsContent.add(ModItems.MOLTEN_GROCKLE_IRON);
        NotEnoughDragonsMiscItemsContent.add(ModItems.MOLTEN_RAW_GROCKLE_IRON);
        NotEnoughDragonsMiscItemsContent.add(ModItems.RAW_GROCKLE_IRON);
        NotEnoughDragonsMiscItemsContent.add(ModItems.GRONCKLE_IRON_INGOT);
        NotEnoughDragonsMiscItemsContent.add(ModItems.GRONCKLE_IRON_NUGGET);
        NotEnoughDragonsMiscItemsContent.add(ModItems.MOLTEN_DRAGON_PROVED_STEEL);
        NotEnoughDragonsMiscItemsContent.add(ModItems.DRAGON_PROVED_STEEL_SCRAPS);
        NotEnoughDragonsMiscItemsContent.add(ModItems.DRAGON_PROVED_STEEL_INGOT);
        NotEnoughDragonsMiscItemsContent.add(ModItems.GRONCKLE_IRON_SWORD);
        NotEnoughDragonsMiscItemsContent.add(ModItems.GRONCKLE_IRON_SHOVEL);
        NotEnoughDragonsMiscItemsContent.add(ModItems.GRONCKLE_IRON_PICKAXE);
        NotEnoughDragonsMiscItemsContent.add(ModItems.GRONCKLE_IRON_AXE);
        NotEnoughDragonsMiscItemsContent.add(ModItems.GRONCKLE_IRON_HOE);
        return NotEnoughDragonsMiscItemsContent;
    }

    private static ArrayList<Supplier<? extends Item>> NotEnoughDragonsBlocksContent(){
        ArrayList<Supplier<? extends Item>> NotEnoughDragonsBlocksContent = new ArrayList<>();
        NotEnoughDragonsBlocksContent.add(()->ModBlocks.GRONCKLE_LAVA.get().asItem());
        NotEnoughDragonsBlocksContent.add(()->ModBlocks.GRONCKLE_LAVA_BLOCK_LAVA.get().asItem());

        NotEnoughDragonsBlocksContent.add(()->ModBlocks.MOLTEN_GRONCKLE_IRON_LAVA.get().asItem());
        NotEnoughDragonsBlocksContent.add(()->ModBlocks.MOLTEN_GRONCKLE_IRON_BLOCK.get().asItem());

        NotEnoughDragonsBlocksContent.add(()->ModBlocks.GRONCKLE_IRON_BLOCK.get().asItem());
        NotEnoughDragonsBlocksContent.add(()->ModBlocks.GRONCKLE_IRON_STAIRS.get().asItem());
        NotEnoughDragonsBlocksContent.add(()->ModBlocks.GRONCKLE_IRON_SLAB.get().asItem());
        NotEnoughDragonsBlocksContent.add(()->ModBlocks.GRONCKLE_IRON_BARS.get().asItem());
        NotEnoughDragonsBlocksContent.add(()->ModBlocks.GRONCKLE_IRON_DOOR.get().asItem());
        NotEnoughDragonsBlocksContent.add(()->ModBlocks.GRONCKLE_IRON_TRAPDOOR.get().asItem());
        NotEnoughDragonsBlocksContent.add(()->ModBlocks.GRONCKLE_IRON_CHAIN.get().asItem());

        NotEnoughDragonsBlocksContent.add(()->ModBlocks.RAW_GRONCKLE_IRON_BLOCK.get().asItem());
        NotEnoughDragonsBlocksContent.add(()->ModBlocks.RAW_GRONCKLE_IRON_STAIRS.get().asItem());
        NotEnoughDragonsBlocksContent.add(()->ModBlocks.RAW_GRONCKLE_IRON_SLAB.get().asItem());

        NotEnoughDragonsBlocksContent.add(()->ModBlocks.DRAGON_PROVED_STEEL_BLOCK.get().asItem());
        NotEnoughDragonsBlocksContent.add(()->ModBlocks.DRAGON_PROVED_STEEL_STAIRS.get().asItem());
        NotEnoughDragonsBlocksContent.add(()->ModBlocks.DRAGON_PROVED_STEEL_SLAB.get().asItem());
        return NotEnoughDragonsBlocksContent;
    }

    public static final ResourceKey<CreativeModeTab> NED_DRAGONS_TAB = createCreativeTab("ned_dragons",()-> ModItems.NIGHT_FURY.get().getDefaultInstance(),NotEnoughDragonsDragonsContent());
    public static final ResourceKey<CreativeModeTab> NED_MISC_ITEMS_TAB = createCreativeTab("ned_misc_itms",()-> ModItems.GOLD_COIN.get().getDefaultInstance(),NotEnoughDragonsMiscItemsContent());
    public static final ResourceKey<CreativeModeTab> NED_BLOCKS_TAB = createCreativeTab("ned_blocks",()->ModBlocks.GRONCKLE_IRON_BLOCK.get().asItem().getDefaultInstance(),NotEnoughDragonsBlocksContent());

    private static ResourceKey<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items) {
        Services.PLATFORM.getRegisterHelper().createCreativeTab(name,icon,items);
        return ResourceKey.create(Registries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,name));
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Creative Mode Taps for {}", NotEnoughDragons.MOD_ID);
    }
}
