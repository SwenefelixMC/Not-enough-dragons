package net.sweningteam.not_enough_dragons.common.worldgen.structures.placements;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGeneratorStructureState;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.sweningteam.not_enough_dragons.common.registry.ModStructurs;
import net.sweningteam.not_enough_dragons.common.registry.ModStrucutreSets;

public class ModStrucutreSetsConfiguration {
    public static void bootstrap(BootstrapContext<StructureSet> context) {
        HolderGetter<Structure> structures = context.lookup(Registries.STRUCTURE);
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        context.register(
                ModStrucutreSets.VIKING_TRADER_SHIP_SET,
                new StructureSet(structures.getOrThrow(ModStructurs.VIKING_TRADER_SHIP), new RandomSpreadStructurePlacement(
                        40, 15, RandomSpreadType.LINEAR, 255233456
                        )
                )
        );
    }
}
