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
import net.sweningteam.not_enough_dragons.client.registry.ModKeyMappings;
import net.sweningteam.not_enough_dragons.common.registry.ModBlocks;
import net.sweningteam.not_enough_dragons.common.registry.ModEntityTypes;

import java.util.function.BiConsumer;

public class NotEnoughDragonsClient {
    public static void init(){
        ModDataTickets.init();
        ModKeyMappings.init();
    }

    public static boolean isCharging = false;

    public static void registerEntityRenderers(BiConsumer<EntityType< ?extends Entity>, EntityRendererProvider> consumer){
        consumer.accept(ModEntityTypes.NIGHT_FURY.get() , NightFuryRenderer::new);
        consumer.accept(ModEntityTypes.PLASMA_BLAST.get(), PlasmaBlastRenderer::new);
        consumer.accept(ModEntityTypes.NIGHT_FURY_EGG.get(), NightFuryEggRenderer::new);

        consumer.accept(ModEntityTypes.GRONCKLE.get() , GronckleRenderer::new);
        consumer.accept(ModEntityTypes.GRONCKLE_EGG.get(), GronckleEggRenderer::new);
    }

    public static void onClientTick(){
        isCharging = false;
        while (ModKeyMappings.DRAGON_CHARGE_MAIN_ATTACK_KEY.consumeClick()){
            isCharging = true;
        }
    }
}
