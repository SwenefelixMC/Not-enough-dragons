package net.sweningteam.client.entity.renderer.dragon.gronckle;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.sweningteam.client.entity.model.dragon.gronckle.GronckleModel;
import net.sweningteam.client.entity.model.dragon.nightfury.NightFuryModel;
import net.sweningteam.client.entity.renderer.dragon.base.DragonRenderer;
import net.sweningteam.client.entity.renderer.layers.SaddleLayer;
import net.sweningteam.client.entity.renderer.states.DragonRenderState;
import net.sweningteam.common.entity.dragons.bolder_class.gronckle.Gronckle;
import net.sweningteam.common.entity.dragons.strike_class.night_furry.NightFury;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class GronckleRenderer <R extends DragonRenderState & GeoRenderState> extends DragonRenderer<Gronckle,R > {
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
