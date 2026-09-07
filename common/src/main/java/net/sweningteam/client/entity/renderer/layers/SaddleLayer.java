package net.sweningteam.client.entity.renderer.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.resources.ResourceLocation;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.client.entity.renderer.states.DragonRenderState;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.BakedGeoModel;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.layer.TextureLayerGeoLayer;

import java.util.function.Predicate;

public class SaddleLayer <T extends GeoAnimatable, R extends DragonRenderState & GeoRenderState, O> extends TextureLayerGeoLayer<T,O,R> {
    String id;

    @Override
    public void submitRenderTask(R renderState, PoseStack poseStack, BakedGeoModel bakedModel, SubmitNodeCollector renderTasks, CameraRenderState cameraState, int packedLight, int packedOverlay, int renderColor, boolean didRenderModel) {
        if(!renderState.hasSaddle){
            return;
        }
        super.submitRenderTask(renderState, poseStack, bakedModel, renderTasks, cameraState, packedLight, packedOverlay, renderColor, didRenderModel);
    }

    public SaddleLayer(GeoRenderer<T,O, R> renderer, String id) {
        super(renderer,ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"textures/entity/equipment/dragon/"+id+".png"));
        this.id = id;
    }
}
