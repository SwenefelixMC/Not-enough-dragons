package net.sweningteam.common.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.platform.Services;

import java.util.function.Supplier;

public class ModPoiTypes {
    public static final ResourceKey<PoiType> VIKING_POI_KEY = registerPoiKey("viking_poi");

    public static final Supplier<PoiType> VIKING_POI = registerPOIType("viking_poi", ModBlocks.BOOK_OF_DRAGONS, 1,1);

    public static ResourceKey<PoiType> registerPoiKey(String name){
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,name));
    }

    public static Supplier<PoiType> registerPOIType(String name, Supplier<Block> block, int tickets, int range){
        return Services.PLATFORM.getRegisterHelper().registerPOIType(name, block, tickets, range);
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering poi types for {}",NotEnoughDragons.MOD_NAME);
    }
}
