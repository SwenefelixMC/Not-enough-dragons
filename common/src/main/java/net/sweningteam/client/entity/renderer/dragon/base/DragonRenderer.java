package net.sweningteam.client.entity.renderer.dragon.base;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.entity.EntityType;
import net.sweningteam.client.entity.model.dragon.base.DragonModel;
import net.sweningteam.client.entity.renderer.states.DragonRenderState;
import net.sweningteam.common.entity.dragons.base.Dragon;
import net.sweningteam.common.entity.dragons.strike_class.night_furry.NightFury;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public abstract class DragonRenderer <T extends Dragon & GeoAnimatable,R extends DragonRenderState & GeoRenderState > extends GeoEntityRenderer<T,R> {
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

    @Override
    @SuppressWarnings("unchecked")
    public R createRenderState(T animatable, Void relatedObject) {
        return (R) new DragonRenderState();
    }

}
