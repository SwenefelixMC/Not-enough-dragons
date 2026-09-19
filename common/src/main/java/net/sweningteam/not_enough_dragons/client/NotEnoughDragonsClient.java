package net.sweningteam.not_enough_dragons.client;

import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.gronckle.GronckleEggRenderer;
import net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.gronckle.GronckleRenderer;
import net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.nightfury.NightFuryEggRenderer;
import net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.nightfury.NightFuryRenderer;
import net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.nightfury.PlasmaBlastRenderer;
import net.sweningteam.not_enough_dragons.client.registry.ModDataTickets;
import net.sweningteam.not_enough_dragons.common.registry.ModBlocks;
import net.sweningteam.not_enough_dragons.common.registry.ModEntityTypes;

import java.util.function.BiConsumer;

public class NotEnoughDragonsClient {
    public static void init(){
        ModDataTickets.init();
    }

    public static void registerBlockRenderTypes(BiConsumer<Block, ChunkSectionLayer> consumer){
        consumer.accept(ModBlocks.NIGHT_FURY_SCALES.get(),ChunkSectionLayer.TRANSLUCENT);
        consumer.accept(ModBlocks.GRONCKLE_IRON_DOOR.get(),ChunkSectionLayer.TRANSLUCENT);
        consumer.accept(ModBlocks.GRONCKLE_IRON_TRAPDOOR.get(), ChunkSectionLayer.TRANSLUCENT);
        consumer.accept(ModBlocks.GRONCKLE_IRON_CHAIN.get(),ChunkSectionLayer.TRANSLUCENT);
        consumer.accept(ModBlocks.GRONCKLE_IRON_BARS.get(),ChunkSectionLayer.TRANSLUCENT);
        consumer.accept(ModBlocks.DRAGON_NIP.get(),ChunkSectionLayer.CUTOUT);
        consumer.accept(ModBlocks.GRONCKLE_SCALES.get(),ChunkSectionLayer.TRANSLUCENT);
    }
    public static void registerEntityRenderers(BiConsumer<EntityType< ?extends Entity>, EntityRendererProvider> consumer){
        consumer.accept(ModEntityTypes.NIGHT_FURY.get() , NightFuryRenderer::new);
        consumer.accept(ModEntityTypes.PLASMA_BLAST.get(), PlasmaBlastRenderer::new);
        consumer.accept(ModEntityTypes.NIGHT_FURY_EGG.get(), NightFuryEggRenderer::new);

        consumer.accept(ModEntityTypes.GRONCKLE.get() , GronckleRenderer::new);
        consumer.accept(ModEntityTypes.GRONCKLE_EGG.get(), GronckleEggRenderer::new);
    }
}
