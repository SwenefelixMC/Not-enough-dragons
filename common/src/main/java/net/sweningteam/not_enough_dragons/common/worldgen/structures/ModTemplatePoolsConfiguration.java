package net.sweningteam.not_enough_dragons.common.worldgen.structures;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;
import net.sweningteam.not_enough_dragons.common.registry.ModStructurs;
import net.sweningteam.not_enough_dragons.common.registry.ModTemplatePools;

public class ModTemplatePoolsConfiguration {
    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        context.register(
                ModTemplatePools.VIKING_TRADER_SHIP_POOL,
                new StructureTemplatePool(getEmptyPool(context), ImmutableList.of(Pair.of(StructurePoolElement.single(ModStructurs.VIKING_TRADER_SHIP.identifier().toString()),1)), StructureTemplatePool.Projection.RIGID)
        );
    }
    private static Holder.Reference<StructureTemplatePool> getEmptyPool(BootstrapContext<StructureTemplatePool> context) {
        return getPool(context, Pools.EMPTY);
    }
    private static Holder.Reference<StructureTemplatePool> getPool(BootstrapContext<StructureTemplatePool> context, ResourceKey<StructureTemplatePool> poolResourceKey) {
        return context.lookup(Registries.TEMPLATE_POOL).getOrThrow(poolResourceKey);
    }
    private static Holder.Reference<StructureProcessorList> getProcessor(BootstrapContext<StructureTemplatePool> context, ResourceKey<StructureProcessorList> processorList) {
        return context.lookup(Registries.PROCESSOR_LIST).getOrThrow(processorList);
    }
}
