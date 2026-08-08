package net.sweningteam.client.entity.renderer.layers;

import net.minecraft.client.renderer.entity.state.EntityRenderState;
import net.minecraft.resources.ResourceLocation;
import net.sweningteam.NotEnoughDragons;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.renderer.base.GeoRenderState;
import software.bernie.geckolib.renderer.base.GeoRenderer;
import software.bernie.geckolib.renderer.layer.TextureLayerGeoLayer;

public class SaddleLayer <T extends GeoAnimatable, R extends EntityRenderState & GeoRenderState, O> extends TextureLayerGeoLayer<T,O,R> {
    String id;

    public SaddleLayer(GeoRenderer<T,O, R> renderer, String id) {
        super(renderer,ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"textures/entity/equipment/dragon/"+id+".png"));
        this.id = id;
    }
}
