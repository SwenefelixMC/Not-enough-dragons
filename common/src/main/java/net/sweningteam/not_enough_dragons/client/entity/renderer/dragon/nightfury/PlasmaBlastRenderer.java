package net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.nightfury;

import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.layer.builtin.AutoGlowingGeoLayer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.sweningteam.not_enough_dragons.client.entity.model.dragon.nightfury.PlasmaBlastModel;
import net.sweningteam.not_enough_dragons.common.entity.projectile.PlasmaBlast;

public class PlasmaBlastRenderer <R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<PlasmaBlast, R> {
    public PlasmaBlastRenderer(EntityRendererProvider.Context context) {
        super(context, new PlasmaBlastModel<>());
        withRenderLayer(new AutoGlowingGeoLayer<>(this));
    }
}
