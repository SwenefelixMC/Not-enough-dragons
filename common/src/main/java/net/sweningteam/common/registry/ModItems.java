package net.sweningteam.common.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.platform.Services;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ModItems {
    public static final ArrayList<Supplier<? extends Item>> ITEMS = new ArrayList<>();

    public static final Supplier<Item> NIGHT_FURY = registerItem("night_fury", ()->new SpawnEggItem(baseProperties("night_fury").spawnEgg(ModEntityTypes.NIGHT_FURY.get())));
    public static final Supplier<Item> GOLD_COIN = registerItem("gold_coin",()-> new Item(baseProperties("gold_coin")));
    public static final Supplier<Item> NIGHT_FURY_EGG = registerItem("night_fury_egg", ()->new SpawnEggItem(baseProperties("night_fury_egg").spawnEgg(ModEntityTypes.NIGHT_FURY_EGG.get())));

    public static final Supplier<Item> GRONCKLE_IRON_BARS = registerItem("gronckle_iron_bars", ()->new BlockItem(ModBlocks.GRONCKLE_IRON_BARS.get(),baseProperties("gronckle_iron_bars")));
    public static final Supplier<Item> GRONCKLE_IRON_DOOR = registerItem("gronckle_iron_door",()-> new BlockItem(ModBlocks.GRONCKLE_IRON_DOOR.get(),baseProperties("gronckle_iron_door")));
    public static final Supplier<Item> GRONCKLE_IRON_TRAP_DOOR = registerItem("gronckle_iron_trap_door",()-> new BlockItem(ModBlocks.GRONCKLE_IRON_TRAPDOOR.get(),baseProperties("gronckle_iron_trap_door")));


    public static <I extends Item> Supplier<I> registerItem(String id, Supplier<I> item){
        Supplier<I> itemSupplier = register(id, item);
        ITEMS.add(itemSupplier);
        return itemSupplier;
    }

    public static Item.Properties baseProperties(String id){
        return new Item.Properties().setId(ResourceKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, id)));
    }

    public static <I extends Item> Supplier<I> register(String id, Supplier<I> item) {
        return Services.PLATFORM.getRegisterHelper().register(BuiltInRegistries.ITEM,id,item);
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Items for {}", NotEnoughDragons.MOD_NAME);
    }
}
