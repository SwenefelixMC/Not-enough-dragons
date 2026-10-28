package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponent;

public class ModTags {
    public class Items {
        public static final TagKey<Item> DRAGON_PROVOCATIVE = TagKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"dragon_provocative"));
        public static final TagKey<Item> REPAIRS_GRONCKLE_IRON = TagKey.create(
                Registries.ITEM,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"repairs_gronckle_iron"));
    }
    public class Blocks{
        public static final TagKey<Block> DRAGONS_SPAWNS_ON = TagKey.create(
                Registries.BLOCK,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"dragon_spawns_on"));
    }

    public class Biomes {
        public static final TagKey<Biome> NIGHT_FURY_SPAWNS_IN = TagKey.create(
                Registries.BIOME,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"night_fury_spawns_in"));
        public static final TagKey<Biome> GRONCKLE_SPAWN_IN = TagKey.create(
                Registries.BIOME,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"gronckle_spawn_in"));
        public static final TagKey<Biome> DRAGON_NIP_SPANS_IN = TagKey.create(
                Registries.BIOME,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"dragon_nip_spawns_in")
        );
    }

    public class EntityTypes {
        public static final TagKey<EntityType<?>> NIGHT_FURY_GETS_ANGRY_AT = TagKey.create(
                Registries.ENTITY_TYPE,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"night_fury_gets_angry_at"));
    }

    public class VillagerTrades {
        public static final TagKey<VillagerTrade> VIKING_TRADER_LEVEL_1 = TagKey.create(
                Registries.VILLAGER_TRADE,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "viking_trader_level_1")
        );
        public static final TagKey<VillagerTrade> VIKING_TRADER_LEVEL_2 = TagKey.create(
                Registries.VILLAGER_TRADE,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "viking_trader_level_2")
        );
        public static final TagKey<VillagerTrade> VIKING_TRADER_LEVEL_3 = TagKey.create(
                Registries.VILLAGER_TRADE,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "viking_trader_level_3")
        );
        public static final TagKey<VillagerTrade> VIKING_TRADER_LEVEL_4 = TagKey.create(
                Registries.VILLAGER_TRADE,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "viking_trader_level_4")
        );
        public static final TagKey<VillagerTrade> VIKING_TRADER_LEVEL_5 = TagKey.create(
                Registries.VILLAGER_TRADE,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "viking_trader_level_5")
        );
    }

    public class DragonFoodComponents {
        public static final TagKey<DragonFoodComponent> NIGHT_FURY = TagKey.create(
                ModRegistries.DRAGON_FOOD_COMPONENT,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"night_fury")
        );
    }
}
