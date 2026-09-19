package net.sweningteam.not_enough_dragons.client.entity.model.dragon.nightfury;


import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.entity.projectile.PlasmaBlast;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

public class PlasmaBlastModel <E extends PlasmaBlast> extends GeoModel<E> {
    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"entity/projectile/plasma_blast");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "textures/projectiles/plasma_blast.png");
    }

    @Override
    public Identifier getAnimationResource(E animatable) {
        return null;
    }
}
