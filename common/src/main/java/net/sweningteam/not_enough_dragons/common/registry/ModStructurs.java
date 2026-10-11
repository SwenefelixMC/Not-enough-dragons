package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;

public class ModStructurs {
    public static final ResourceKey<Structure> VIKING_TRADER_SHIP = createStructure("viking_trader_ship");

    public static final ResourceKey<Structure> createStructure(String name){
        return ResourceKey.create(Registries.STRUCTURE, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,name));
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Structures for {}!",NotEnoughDragons.MOD_NAME);
    }
}
