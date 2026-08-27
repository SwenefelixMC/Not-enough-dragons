package net.sweningteam.client.entity.renderer.dragon.nightfury;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.sweningteam.client.entity.model.dragon.nightfury.NightFuryEggModel;
import net.sweningteam.common.entity.egg.NightFuryEgg;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class NightFuryEggRenderer <R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<NightFuryEgg, R> {
    public NightFuryEggRenderer(EntityRendererProvider.Context context) {
        super(context, new NightFuryEggModel<>());
    }
}
