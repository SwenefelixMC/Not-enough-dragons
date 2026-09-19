package net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.nightfury;

import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.client.entity.model.dragon.nightfury.NightFuryModel;
import net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.base.DragonRenderer;
import net.sweningteam.not_enough_dragons.client.entity.renderer.layers.SaddleLayer;
import net.sweningteam.not_enough_dragons.client.entity.renderer.states.DragonRenderState;
import net.sweningteam.not_enough_dragons.client.registry.ModDataTickets;
import net.sweningteam.not_enough_dragons.common.entity.dragons.strike_class.night_furry.NightFury;
import org.jspecify.annotations.Nullable;

public class NightFuryRenderer<R extends LivingEntityRenderState & GeoRenderState> extends DragonRenderer<NightFury,R > {
    public NightFuryRenderer(EntityRendererProvider.Context context) {
        super(context,new NightFuryModel<>());
        //withRenderLayer(new SaddleLayer(this,"night_fury"));
    }

    @Override
    public void addRenderData(NightFury animatable, @Nullable Void relatedObject, R renderState, float partialTick) {
        renderState.addGeckolibData(ModDataTickets.HAS_SADDLE, animatable.hasSaddle());
        super.addRenderData(animatable, relatedObject, renderState, partialTick);
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
