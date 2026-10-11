package net.sweningteam.not_enough_dragons.common.worldgen.structures;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.BiomeTags;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.sweningteam.not_enough_dragons.common.registry.ModStructurs;
import net.sweningteam.not_enough_dragons.common.structure.viking_trader_ship.VikingTraderShipConfig;
import net.sweningteam.not_enough_dragons.common.structure.viking_trader_ship.VikingTraderShipStructure;

public class ModStructuresConfiguration {
    public static final void boostrap(BootstrapContext<Structure> context) {
        HolderGetter<Biome> biomes = context.lookup(Registries.BIOME);
        HolderGetter<StructureTemplatePool> templates = context.lookup(Registries.TEMPLATE_POOL);
        context.register(
                ModStructurs.VIKING_TRADER_SHIP,
                new VikingTraderShipStructure(new Structure.StructureSettings(biomes.getOrThrow(BiomeTags.IS_OCEAN)), new VikingTraderShipConfig(0.5f))
        );
    }
}
