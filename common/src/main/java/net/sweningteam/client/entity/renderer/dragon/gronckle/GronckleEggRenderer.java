package net.sweningteam.client.entity.renderer.dragon.gronckle;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.sweningteam.client.entity.model.dragon.gronckle.GronckleEggModel;
import net.sweningteam.client.entity.model.dragon.gronckle.GronckleModel;
import net.sweningteam.common.entity.egg.GronckleEgg;
import net.sweningteam.common.entity.egg.NightFuryEgg;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class GronckleEggRenderer  <R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<GronckleEgg, R> {
    public GronckleEggRenderer(EntityRendererProvider.Context context) {
        super(context,new GronckleEggModel<>());
    }
}
