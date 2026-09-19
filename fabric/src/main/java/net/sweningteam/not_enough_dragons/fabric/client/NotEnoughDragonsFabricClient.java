package net.sweningteam.not_enough_dragons.fabric.client;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.entity.EntityRenderers;
import net.sweningteam.not_enough_dragons.client.NotEnoughDragonsClient;

public final class NotEnoughDragonsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        NotEnoughDragonsClient.init();
        //NotEnoughDragonsClient.registerBlockRenderTypes(RenderLayer::putBlock);
        NotEnoughDragonsClient.registerEntityRenderers(EntityRenderers::register);
    }
}
