package net.sweningteam.common.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.item.egg.DragonEggItem;
import net.sweningteam.platform.Services;

import java.util.ArrayList;
import java.util.function.Supplier;

public class ModItems {
    public static final ArrayList<Supplier<? extends Item>> ITEMS = new ArrayList<>();

    public static final Supplier<Item> NIGHT_FURY = registerItem("night_fury", ()->new SpawnEggItem(baseProperties("night_fury").spawnEgg(ModEntityTypes.NIGHT_FURY.get())));
    public static final Supplier<Item> GRONCKLE = registerItem("gronckle", ()->new SpawnEggItem(baseProperties("gronckle").spawnEgg(ModEntityTypes.GRONCKLE.get())));
    public static final Supplier<Item> GOLD_COIN = registerItem("gold_coin",()-> new Item(baseProperties("gold_coin")));
    public static final Supplier<Item> NIGHT_FURY_EGG = registerItem("night_fury_egg", ()->new DragonEggItem(ModEntityTypes.NIGHT_FURY_EGG.get(), baseProperties("night_fury_egg").stacksTo(1)));
    public static final Supplier<Item> GRONCKLE_EGG = registerItem("gronckle_egg", ()->new DragonEggItem(ModEntityTypes.GRONCKLE_EGG.get(), baseProperties("gronckle_egg")));

    public static final Supplier<Item> MOLTEN_GROCKLE_IRON = registerItem("molten_gronckle_iron", ()-> new Item(baseProperties("molten_gronckle_iron")));
    public static final Supplier<Item> MOLTEN_RAW_GROCKLE_IRON = registerItem("molten_raw_gronckle_iron", ()-> new Item(baseProperties("molten_raw_gronckle_iron")));
    public static final Supplier<Item> RAW_GROCKLE_IRON = registerItem("raw_gronckle_iron", ()-> new Item(baseProperties("raw_gronckle_iron")));
    public static final Supplier<Item> GROCKLE_IRON_INGOT = registerItem("gronckle_iron_ingot", ()-> new Item(baseProperties("gronckle_iron_ingot")));
    public static final Supplier<Item> GROCKLE_IRON_NUGGET = registerItem("gronckle_iron_nugget", ()-> new Item(baseProperties("gronckle_iron_nugget")));

    public static final Supplier<Item> MOLTEN_DRAGON_PROVED_STEAL = registerItem("molten_dragon_proved_steal", ()-> new Item(baseProperties("molten_dragon_proved_steal")));
    public static final Supplier<Item> DRAGON_PROVED_STEAL_SCRAPS = registerItem("dragon_proved_steal_scraps", ()-> new Item(baseProperties("dragon_proved_steal_scraps")));
    public static final Supplier<Item> DRAGON_PROVED_STEAL_INGOT = registerItem("dragon_proved_steal_ingot", ()-> new Item(baseProperties("dragon_proved_steal_ingot")));


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
