package net.sweningteam.not_enough_dragons.client.entity.model.dragon.gronckle;

import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;

public class GronckleModel <E extends Dragon> extends GeoModel<E> {
    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"entity/dragon/gronckle");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"textures/entity/dragon/gronckle/gronckle.png");
    }

    @Override
    public Identifier getAnimationResource(E animatable) {
        return null;
    }
}
