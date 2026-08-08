package net.sweningteam.client.entity.model;

import net.minecraft.resources.ResourceLocation;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.entity.dragons.base.Dragon;
import net.sweningteam.common.entity.projectile.PlasmaBlast;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class PlasmaBlastModel <E extends PlasmaBlast> extends GeoModel<E> {
    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"entity/projectile/plasma_blast.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "textures/projectiles/plasma_blast.png");
    }

    @Override
    public ResourceLocation getAnimationResource(E animatable) {
        return null;
    }
}
