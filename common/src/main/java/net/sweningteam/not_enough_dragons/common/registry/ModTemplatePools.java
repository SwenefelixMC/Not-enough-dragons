package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;

public class ModTemplatePools {
    public static final ResourceKey<StructureTemplatePool> VIKING_TRADER_SHIP_POOL = createPool("viking_trader_ship_pool");

    public static ResourceKey<StructureTemplatePool> createPool(String name){
        return ResourceKey.create(Registries.TEMPLATE_POOL, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, name));
    }
}
