package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.structure.viking_trader_ship.VikingTraderShipStructure;
import net.sweningteam.not_enough_dragons.platform.Services;

import java.util.function.Supplier;

public class ModStructureTypes {
    public static final Supplier<StructureType<VikingTraderShipStructure>> VIKING_TRADER_SHIP_TYPE = createStrucutureType("viking_trader_ship", () -> () ->  VikingTraderShipStructure.CODEC);

    public static <S extends Structure> Supplier<StructureType<S>> createStrucutureType(String name, Supplier<StructureType<S>> supplier){
        return Services.PLATFORM.getRegisterHelper().register(BuiltInRegistries.STRUCTURE_TYPE, name, supplier);
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Structure Types for {}!", NotEnoughDragons.MOD_NAME);
    }
}
