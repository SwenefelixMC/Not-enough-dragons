package net.sweningteam.client.entity.renderer.dragon.nightfury;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sweningteam.client.entity.model.dragon.nightfury.NightFuryModel;
import net.sweningteam.client.entity.renderer.dragon.base.DragonRenderer;
import net.sweningteam.client.entity.renderer.layers.SaddleLayer;
import net.sweningteam.client.entity.renderer.states.DragonRenderState;
import net.sweningteam.common.entity.dragons.strike_class.night_furry.NightFury;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class NightFuryRenderer<R extends DragonRenderState & GeoRenderState> extends DragonRenderer<NightFury,R > {
    public NightFuryRenderer(EntityRendererProvider.Context context) {
        super(context,new NightFuryModel<>());
        withRenderLayer(new SaddleLayer<>(this,"night_fury"));
    }

    @Override
    public void extractRenderState(NightFury entity, R entityRenderState, float partialTick) {
        super.extractRenderState(entity,entityRenderState,partialTick);
        entityRenderState.hasSaddle = entity.hasSaddle();
    }

    @Override
    public float BabyShadowradius() {
        return 0.8F;
    }

    @Override
    public float GrownUpShadowRadius() {
        return 1.2F;
    }
}
