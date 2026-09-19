package net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.nightfury;

import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.sweningteam.not_enough_dragons.client.entity.model.dragon.nightfury.NightFuryEggModel;
import net.sweningteam.not_enough_dragons.common.entity.egg.NightFuryEgg;

public class NightFuryEggRenderer <R extends EntityRenderState & GeoRenderState> extends GeoEntityRenderer<NightFuryEgg, R> {
    public NightFuryEggRenderer(EntityRendererProvider.Context context) {
        super(context, new NightFuryEggModel<>());
    }
}
