package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.platform.Services;

import java.util.function.Supplier;

public class ModPoiTypes {
    public static final ResourceKey<PoiType> VIKING_POI = register("viking_poi",ModBlocks.DRAGON_MANUAL,1,1);

    public static ResourceKey<PoiType> register(String name, Supplier<Block> block, int tickets, int range){
        Services.PLATFORM.getRegisterHelper().registerPOIType(name, block, tickets, range);
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,name));
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering poi types for {}",NotEnoughDragons.MOD_NAME);
    }
}
