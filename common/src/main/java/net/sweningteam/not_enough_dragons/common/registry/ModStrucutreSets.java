package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;

public class ModStrucutreSets {
    public static final ResourceKey<StructureSet> VIKING_TRADER_SHIP_SET = register("viking_trader_ship");

    public static ResourceKey<StructureSet> register(String name){
        return ResourceKey.create(Registries.STRUCTURE_SET, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, name));
    }
}
