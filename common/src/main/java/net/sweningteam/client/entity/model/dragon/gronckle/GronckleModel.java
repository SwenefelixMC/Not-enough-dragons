package net.sweningteam.client.entity.model.dragon.gronckle;

import net.minecraft.resources.ResourceLocation;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.client.entity.model.dragon.base.DragonModel;
import net.sweningteam.common.entity.dragons.base.Dragon;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class GronckleModel <E extends Dragon> extends GeoModel<E> {
    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"entity/dragon/gronckle.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"textures/entity/dragon/gronckle/gronckle.png");
    }

    @Override
    public ResourceLocation getAnimationResource(E animatable) {
        return null;
    }
}
