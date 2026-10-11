package net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.nightfury;

import com.geckolib.cache.model.GeoBone;
import com.geckolib.constant.DataTickets;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.client.entity.model.dragon.nightfury.NightFuryModel;
import net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.base.DragonRenderer;
import net.sweningteam.not_enough_dragons.client.entity.renderer.layers.SaddleLayer;
import net.sweningteam.not_enough_dragons.client.entity.renderer.states.DragonRenderState;
import net.sweningteam.not_enough_dragons.client.registry.ModDataTickets;
import net.sweningteam.not_enough_dragons.common.entity.dragons.strike_class.night_furry.NightFury;
import org.jspecify.annotations.Nullable;

import java.util.Optional;

public class NightFuryRenderer<R extends LivingEntityRenderState & GeoRenderState> extends DragonRenderer<NightFury,R > {
    public NightFuryRenderer(EntityRendererProvider.Context context) {
        super(context,new NightFuryModel<>());
        withRenderLayer(new SaddleLayer(this,"night_fury"));
    }

    @Override
    public void addRenderData(NightFury animatable, @Nullable Void relatedObject, R renderState, float partialTick) {
        renderState.addGeckolibData(ModDataTickets.HAS_SADDLE, animatable.hasSaddle());
        super.addRenderData(animatable, relatedObject, renderState, partialTick);
    }
    @Override
    public void adjustModelBonesForRender(RenderPassInfo<R> renderPassInfo, BoneSnapshots snapshots) {
        GeoBone head = renderPassInfo.model().getBone("Head").orElse(null);
        GeoBone Neck = renderPassInfo.model().getBone("Neek").orElse(null);
        GeoBone NeckBase = renderPassInfo.model().getBone("NeekBase").orElse(null);
        float HeadPitch = 0F;
        float HeadYaw = 0F;
        try {
            HeadPitch = renderPassInfo.getGeckolibData(DataTickets.ENTITY_PITCH);
            HeadYaw = renderPassInfo.getGeckolibData(DataTickets.ENTITY_YAW);
        }catch (NullPointerException e){
            NotEnoughDragons.LOGGER.error(e.getMessage());
        }

        if(head != null ){
            snapshots.get(head).setRotY(-1*Mth.clamp (HeadYaw* Mth.DEG_TO_RAD,-5.0F, 5.0F) / 2);
        }
        if( Neck != null){
            snapshots.get(Neck).setRotX(-1*Mth.clamp (HeadPitch* Mth.DEG_TO_RAD, -10.0F,10.0F) / 2);
            snapshots.get(Neck).setRotY(-1*Mth.clamp (HeadYaw* Mth.DEG_TO_RAD,-7.5F,7.5F) / 2);
        }
        if(NeckBase != null){
            snapshots.get(NeckBase).setRotX(-1*Mth.clamp(HeadPitch * Mth.DEG_TO_RAD,-12.5F,12.5F) / 2);
            snapshots.get(NeckBase).setRotY(-1*Mth.clamp (HeadYaw* Mth.DEG_TO_RAD, -15.0F,15.0F) / 2);
        }

        super.adjustModelBonesForRender(renderPassInfo, snapshots);
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
