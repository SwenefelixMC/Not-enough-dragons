package net.sweningteam.client.entity.renderer.dragon.nightfury;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.sweningteam.client.entity.model.dragon.nightfury.PlasmaBlastModel;
import net.sweningteam.common.entity.projectile.PlasmaBlast;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.layer.AutoGlowingGeoLayer;

public class PlasmaBlastRenderer <R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<PlasmaBlast, R> {
    public PlasmaBlastRenderer(EntityRendererProvider.Context context) {
        super(context, new PlasmaBlastModel<>());
        withRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
