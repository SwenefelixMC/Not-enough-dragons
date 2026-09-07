package net.sweningteam.client.entity.renderer;

import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.state.VillagerRenderState;
import net.minecraft.resources.ResourceLocation;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.entity.misc.VikingTrader;

public class VikingTraderRenderer extends MobRenderer<VikingTrader, VillagerRenderState, VillagerModel> {
    private static final ResourceLocation VILLAGER_BASE_SKIN = ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"textures/entity/viking.png");

    private static ModelLayerLocation register(String path) {
        return new ModelLayerLocation(ResourceLocation.withDefaultNamespace(path), "main");
    }

    public VikingTraderRenderer(EntityRendererProvider.Context context) {
        super(context, new VillagerModel(context.bakeLayer(ModelLayers.WANDERING_TRADER)),0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(VillagerRenderState renderState) {
        return VILLAGER_BASE_SKIN;
    }

    @Override
    public VillagerRenderState createRenderState() {
        return new VillagerRenderState();
    }
}
