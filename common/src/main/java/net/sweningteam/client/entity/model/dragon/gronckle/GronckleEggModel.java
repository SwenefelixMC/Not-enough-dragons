package net.sweningteam.client.entity.model.dragon.gronckle;

import net.minecraft.resources.ResourceLocation;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.entity.egg.GronckleEgg;
import net.sweningteam.common.entity.egg.NightFuryEgg;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class GronckleEggModel <E extends GronckleEgg> extends GeoModel<E> {
    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"entity/egg/medium_egg.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"textures/entity/egg/gronckle.png");
    }

    @Override
    public ResourceLocation getAnimationResource(E animatable) {
        return null;
    }
}
