package net.sweningteam.client.entity.renderer.dragon.nightfury;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.sweningteam.client.entity.model.dragon.nightfury.NightfuryModel;
import net.sweningteam.client.entity.renderer.dragon.base.DragonRenderer;
import net.sweningteam.client.entity.renderer.layers.SaddleLayer;
import net.sweningteam.client.entity.renderer.states.DragonRenderState;
import net.sweningteam.common.entity.dragons.strike_class.night_furry.NightFury;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class NightFuryRenderer<R extends DragonRenderState & GeoRenderState> extends DragonRenderer<NightFury,R > {
    public NightFuryRenderer(EntityRendererProvider.Context context) {
        super(context,new NightfuryModel<>());
        shadowRadius = 1;
        withRenderLayer(new SaddleLayer<>(this,"night_fury"));
    }

    @Override
    public void extractRenderState(NightFury entity, R entityRenderState, float partialTick) {
        super.extractRenderState(entity,entityRenderState,partialTick);
        entityRenderState.hasSaddle = entity.hasSaddle();
    }


    @Override
    public void renderFinal(R renderState, PoseStack poseStack, BakedGeoModel model, SubmitNodeCollector renderTasks, CameraRenderState cameraState, int packedLight, int packedOverlay, int renderColor) {
        super.renderFinal(renderState, poseStack, model, renderTasks, cameraState, packedLight, packedOverlay, renderColor);
    }

    @Override
    public void adjustRenderPose(R renderState, PoseStack poseStack, BakedGeoModel model, CameraRenderState cameraState) {
        super.adjustRenderPose(renderState, poseStack, model, cameraState);
        if(renderState.isBaby){
            poseStack.scale(0.5F,0.5F,0.5F);
        }
    }
}
