package net.sweningteam.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.sweningteam.NotEnoughDragons;

public class ModTags {
    public class Items {
        public static final TagKey<Item> DRAGON_PROVOCATIVE = TagKey.create(
                Registries.ITEM,
                ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"dragon_provocative"));
    }
    public class Blocks{
        public static final TagKey<Block> DRAGONS_SPAWNS_ON = TagKey.create(
                Registries.BLOCK,
                ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"dragon_spawns_on"));
    }

    public class Biomes {
        public static final TagKey<Biome> NIGHT_FURY_SPAWNS_IN = TagKey.create(
                Registries.BIOME,
                ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"night_fury_spawns_in"));
    }

    public class EntityTypes {
        public static final TagKey<EntityType<?>> NIGHT_FURY_GETS_ANGRY_AT = TagKey.create(
                Registries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"night_fury_gets_angry_at"));
    }

}
