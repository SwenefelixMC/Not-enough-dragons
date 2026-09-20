package net.sweningteam.not_enough_dragons.common.entity.villager;

import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.TradeCost;
import net.minecraft.world.item.trading.VillagerTrade;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.registry.ModBlocks;
import net.sweningteam.not_enough_dragons.common.registry.ModItems;

public class ModTrades {
    public static final ResourceKey<VillagerTrade> WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_AXE = createKey("weaponsmith/3/emerald_gronckle_iron_axe");
    public static final ResourceKey<VillagerTrade> WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_SWORD = createKey("weaponsmith/3/emerald_gronckle_iron_sword");
    public static final ResourceKey<VillagerTrade> WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_SHOVEL = createKey("weaponsmith/3/emerald_gronckle_iron_shovel");
    public static final ResourceKey<VillagerTrade> WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_PICKAXE = createKey("weaponsmith/3/emerald_gronckle_iron_pickaxe");
    public static final ResourceKey<VillagerTrade> WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_HOE = createKey("weaponsmith/3/emerald_gronckle_iron_hoe");

    public static final ResourceKey<VillagerTrade> VIKING_1_DIAMOND_GOLD_COIN = createKey("viking/1/diamond_gold_coin");
    public static final ResourceKey<VillagerTrade> VIKING_1_IRON_INGOT_GOLD_COIN = createKey("viking/1/iron_ingot_gold_coin");
    public static final ResourceKey<VillagerTrade> VIKING_1_GRONCKLE_IRON_INGOT_GOLD_COIN = createKey("viking/1/gronckle_iron_ingot_gold_coin");
    public static final ResourceKey<VillagerTrade> VIKING_1_DRAGON_PROVED_STEAL_INGOT_GOLD_COIN = createKey("viking/1/dragon_proved_steal_ingot_gold_coin");
    public static final ResourceKey<VillagerTrade> VIKING_1_GOLD_COIN_SADDLE = createKey("viking/1/gold_coin_saddle");
    public static final ResourceKey<VillagerTrade> VIKING_1_GOLD_COIN_NAME_TAG = createKey("viking/1/gold_coin_name_tag");

    public static final ResourceKey<VillagerTrade> VIKING_2_GOLD_COIN_MAP= createKey("viking/2/gold_coin_map");
    public static final ResourceKey<VillagerTrade> VIKING_2_GOLD_COIN_PAPER= createKey("viking/2/gold_coin_paper");
    public static final ResourceKey<VillagerTrade> VIKING_2_GOLD_COIN_COMPASS= createKey("viking/2/gold_coin_compass");
    public static final ResourceKey<VillagerTrade> VIKING_2_GOLD_COIN_NIGHT_FURY_SCALES= createKey("viking/2/gold_coin_night_fury_scales");
    public static final ResourceKey<VillagerTrade> VIKING_2_GOLD_COIN_GRONCKLE_SCALES= createKey("viking/2/gold_coin_gronckle_scales");
    //Strick Class Explorer Map
    //Tracker Class Explorer Map
    //Boulder Class Explorer Map
    //Tidal Class Explorer Map
    //Sharp Class Explorer Map
    //Stocker Class Explorer Map
    //Mystory Class Explorer Map

    public static final ResourceKey<VillagerTrade> VIKING_3_GOLD_COIN_DRAGON_MANUAL= createKey("viking/3/gold_coin_dragon_manual");
    public static final ResourceKey<VillagerTrade> VIKING_3_GOLD_COIN_DRAGON_NIP= createKey("viking/3/gold_coin_dragon_nip");
    public static final ResourceKey<VillagerTrade> VIKING_3_GOLD_COIN_GRONCKLE_IRON_BLOCK= createKey("viking/3/gold_coin_gronckle_iron_block");
    public static final ResourceKey<VillagerTrade> VIKING_3_GOLD_COIN_DRAGON_PROVED_STEAL_BLOCK = createKey("viking/3/gold_coin_dragon_proved_steal_block");

    public static final ResourceKey<VillagerTrade> VIKING_4_GOLD_COIN_COD= createKey("viking/4/gold_coin_cod");
    public static final ResourceKey<VillagerTrade> VIKING_4_GOLD_COIN_SALMON= createKey("viking/4/gold_coin_salmon");
    public static final ResourceKey<VillagerTrade> VIKING_4_GOLD_COIN_RABBIT_STEW= createKey("viking/4/gold_coin_rabbit_stew");
    public static final ResourceKey<VillagerTrade> VIKING_4_GOLD_COIN_GRANITE= createKey("viking/4/gold_coin_granite");

    public static final ResourceKey<VillagerTrade> VIKING_5_GOLD_COIN_GRONCKLE_EGG= createKey("viking/5/gold_coin_gronckle_egg");
    public static final ResourceKey<VillagerTrade> VIKING_5_GOLD_COIN_NIGHT_FURY_EGG= createKey("viking/5/gold_coin_night_fury_egg");

    public static void bootstrap(BootstrapContext<VillagerTrade> context){
        context.register(
                WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_AXE,
                VillagerTrade.builder(
                        new TradeCost(Items.EMERALD,4),
                        new ItemStackTemplate(ModItems.GRONCKLE_IRON_AXE.get()),
                        6,
                        20,
                        0.2F
                ).build()
        );
        context.register(
                WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_SWORD,
                 VillagerTrade.builder(
                        new TradeCost(Items.EMERALD,4),
                        new ItemStackTemplate(ModItems.GRONCKLE_IRON_SWORD.get()),
                        6,
                        20,
                        0.2F
                 ).build()
        );
        context.register(
                WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_SHOVEL,
                 VillagerTrade.builder(
                        new TradeCost(Items.EMERALD,4),
                        new ItemStackTemplate(ModItems.GRONCKLE_IRON_SHOVEL.get()),
                        6,
                        20,
                        0.2F).build()
        );
        context.register(
                WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_PICKAXE,
                VillagerTrade.builder(
                        new TradeCost(Items.EMERALD,4),
                        new ItemStackTemplate(ModItems.GRONCKLE_IRON_PICKAXE.get()),
                        6,
                        20,
                        0.2F).build()
        );
        context.register(
                WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_HOE,
                VillagerTrade.builder(
                        new TradeCost(Items.EMERALD,4),
                        new ItemStackTemplate(ModItems.GRONCKLE_IRON_HOE.get()),
                        6,
                        20,
                        0.2F).build()
        );

        context.register(
                VIKING_1_DIAMOND_GOLD_COIN,
                VillagerTrade.builder(
                        new TradeCost(Items.DIAMOND,1),
                        new ItemStackTemplate(ModItems.GOLD_COIN.get(),2),
                        6,
                        3,
                        0.2F).build()
        );
        context.register(
                VIKING_1_IRON_INGOT_GOLD_COIN,
                 VillagerTrade.builder(
                        new TradeCost(Items.IRON_INGOT,1),
                        new ItemStackTemplate(ModItems.GOLD_COIN.get(),1),
                        6,
                        3,
                        0.2F).build()
        );
        context.register(
                VIKING_1_GRONCKLE_IRON_INGOT_GOLD_COIN,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GRONCKLE_IRON_INGOT.get(), 2),
                        new ItemStackTemplate(ModItems.GOLD_COIN.get(),3),
                        6,
                        3,
                        0.2F).build()
        );
        context.register(
                VIKING_1_DRAGON_PROVED_STEAL_INGOT_GOLD_COIN,
                VillagerTrade.builder(
                        new TradeCost(ModItems.DRAGON_PROVED_STEEL_INGOT.get(), 2),
                        new ItemStackTemplate(ModItems.GOLD_COIN.get(),4),
                        6,
                        3,
                        0.2F).build()
        );
        context.register(
                VIKING_1_GOLD_COIN_SADDLE,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(), 3),
                        new ItemStackTemplate(Items.SADDLE,1),
                        2,
                        5,
                        0.2F).build()
        );
        context.register(
                VIKING_1_GOLD_COIN_NAME_TAG,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(), 2),
                        new ItemStackTemplate(Items.NAME_TAG,4),
                        10,
                        3,
                        0.2F).build()
        );


        context.register(
                VIKING_2_GOLD_COIN_MAP,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(), 3),
                        new ItemStackTemplate(Items.MAP,1),
                        6,
                        10,
                        0.2F).build()
        );
        context.register(
                VIKING_2_GOLD_COIN_PAPER,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(),2),
                        new ItemStackTemplate(Items.PAPER,1),
                        6,
                        10,
                        0.2F).build()
        );
        context.register(
                VIKING_2_GOLD_COIN_COMPASS,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(), 3),
                        new ItemStackTemplate(Items.COMPASS,1),
                        6,
                        10,
                        0.2F).build()
        );
        context.register(
                VIKING_2_GOLD_COIN_GRONCKLE_SCALES,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(), 1),
                        new ItemStackTemplate(ModBlocks.GRONCKLE_SCALES.get().asItem(),4),
                        10,
                        10,
                        0.2F).build()
        );
        context.register(
                VIKING_2_GOLD_COIN_NIGHT_FURY_SCALES,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(), 1),
                        new ItemStackTemplate(ModBlocks.NIGHT_FURY_SCALES.get().asItem(),4),
                        10,
                        10,
                        0.2F).build()
        );

        context.register(
                VIKING_3_GOLD_COIN_DRAGON_MANUAL,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(),20),
                        new ItemStackTemplate(ModBlocks.DRAGON_MANUAL.get().asItem(),1),
                        1,
                        40,
                        0.2F).build()
        );
        context.register(
                VIKING_3_GOLD_COIN_DRAGON_NIP,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(), 1),
                        new ItemStackTemplate(ModBlocks.DRAGON_NIP.get().asItem(),2),
                        6,
                        20,
                        0.2F).build()
        );
        context.register(
                VIKING_3_GOLD_COIN_DRAGON_PROVED_STEAL_BLOCK,
               VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(), 19),
                        new ItemStackTemplate(ModBlocks.DRAGON_PROVED_STEEL_BLOCK.get().asItem(),1),
                        2,
                        20,
                        0.2F).build()
        );
        context.register(
                VIKING_3_GOLD_COIN_GRONCKLE_IRON_BLOCK,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(), 15),
                        new ItemStackTemplate(ModBlocks.GRONCKLE_IRON_BLOCK.get().asItem(),1),
                        4,
                        20,
                        0.2F).build()
        );

        context.register(
                VIKING_4_GOLD_COIN_COD,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(),1),
                        new ItemStackTemplate(Items.COD,2),
                        10,
                        30,
                        0.2F).build()
        );
        context.register(
                VIKING_4_GOLD_COIN_SALMON,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(),1),
                        new ItemStackTemplate(Items.SALMON,2),
                        10,
                        30,
                        0.2F).build()
        );
        context.register(
                VIKING_4_GOLD_COIN_RABBIT_STEW,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(),2),
                        new ItemStackTemplate(Items.RABBIT_STEW,1),
                        10,
                        30,
                        0.2F).build()
        );
        context.register(
                VIKING_4_GOLD_COIN_GRANITE,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(),2),
                        new ItemStackTemplate(Items.GRANITE,1),
                        10,
                        30,
                        0.2F).build()
        );

        context.register(
                VIKING_5_GOLD_COIN_GRONCKLE_EGG,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(),20),
                        new ItemStackTemplate(ModItems.GRONCKLE_EGG.get(),1),
                        1,
                        40,
                        0.2F).build()
        );
        context.register(
                VIKING_5_GOLD_COIN_NIGHT_FURY_EGG,
                VillagerTrade.builder(
                        new TradeCost(ModItems.GOLD_COIN.get(),20),
                        new ItemStackTemplate(ModItems.NIGHT_FURY_EGG.get(),1),
                        1,
                        40,
                        0.2F).build()
        );
    }


    public static ResourceKey<VillagerTrade> createKey(String name){
        return ResourceKey.create(Registries.VILLAGER_TRADE, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, name));
    }
}
