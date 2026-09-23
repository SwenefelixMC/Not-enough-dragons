package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.structure.viking_trader_ship.VikingTraderShipPiece;
import net.sweningteam.not_enough_dragons.platform.Services;

import java.util.function.Supplier;

public class ModStructurPieceTypes {
    public static final Supplier<StructurePieceType> VIKING_TRADER_SHIP_PICE = register("viking_trader_ship_pice", ()-> VikingTraderShipPiece::new);

    public static Supplier<StructurePieceType> register(String name, Supplier<StructurePieceType> supplier) {
        return Services.PLATFORM.getRegisterHelper().register(BuiltInRegistries.STRUCTURE_PIECE, name , supplier);
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Structure Pice Types for {}!", NotEnoughDragons.MOD_NAME);
    }
}
