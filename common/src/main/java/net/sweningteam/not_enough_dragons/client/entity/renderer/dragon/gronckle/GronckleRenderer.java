package net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.gronckle;

import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.sweningteam.not_enough_dragons.client.entity.model.dragon.gronckle.GronckleModel;
import net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.base.DragonRenderer;
import net.sweningteam.not_enough_dragons.client.entity.renderer.states.DragonRenderState;
import net.sweningteam.not_enough_dragons.common.entity.dragons.bolder_class.gronckle.Gronckle;

public class GronckleRenderer <R extends LivingEntityRenderState & GeoRenderState> extends DragonRenderer<Gronckle,R > {
    public GronckleRenderer(EntityRendererProvider.Context context) {
        super(context,new GronckleModel<>());
    }
    @Override
    public float BabyShadowradius() {
        return 1.5F;
    }

    @Override
    public float GrownUpShadowRadius() {
        return 1.5F;
    }
}
