package net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.base;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.model.GeoModel;
import com.geckolib.renderer.GeoEntityRenderer;
import com.geckolib.renderer.base.GeoRenderState;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.sweningteam.not_enough_dragons.client.entity.renderer.states.DragonRenderState;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;

public abstract class DragonRenderer <T extends Dragon & GeoAnimatable,R extends LivingEntityRenderState & GeoRenderState> extends GeoEntityRenderer<T,R> {
    public DragonRenderer(EntityRendererProvider.Context context , GeoModel<T> model) {
        super(context, model);
    }

    @Override
    public void submit(R renderState, PoseStack poseStack, SubmitNodeCollector renderTasks, CameraRenderState cameraState) {
        if(renderState.isBaby) {
            poseStack.scale(0.5f, 0.5f, 0.5f);
            shadowRadius = BabyShadowradius();
        } else {
            poseStack.scale(1f, 1f, 1f);
            shadowRadius = GrownUpShadowRadius();
        }
        super.submit(renderState, poseStack, renderTasks, cameraState);
    }

    public abstract float BabyShadowradius();

    public abstract float GrownUpShadowRadius();
}
