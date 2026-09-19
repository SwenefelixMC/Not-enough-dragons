package net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.gronckle;

import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.sweningteam.not_enough_dragons.client.entity.model.dragon.gronckle.GronckleEggModel;
import net.sweningteam.not_enough_dragons.common.entity.egg.GronckleEgg;

public class GronckleEggRenderer  <R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<GronckleEgg, R> {
    public GronckleEggRenderer(EntityRendererProvider.Context context) {
        super(context,new GronckleEggModel<>());
    }
}
