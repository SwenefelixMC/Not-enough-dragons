package net.sweningteam.not_enough_dragons.client.entity.renderer.layers;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.GeoRenderer;
import com.geckolib.renderer.base.RenderPassInfo;
import com.geckolib.renderer.layer.builtin.TextureLayerGeoLayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.resources.Identifier;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.client.entity.renderer.states.DragonRenderState;
import net.sweningteam.not_enough_dragons.client.registry.ModDataTickets;

public class SaddleLayer <T extends GeoAnimatable, R extends DragonRenderState & GeoRenderState, O> extends TextureLayerGeoLayer<T,O,R> {
    String id;

    @Override
    public void submitRenderTask(RenderPassInfo<R> renderPassInfo, SubmitNodeCollector renderTasks) {
        if(Boolean.FALSE.equals(renderPassInfo.getGeckolibData(ModDataTickets.HAS_SADDLE))){
            return;
        }
        super.submitRenderTask(renderPassInfo, renderTasks);
    }

    public SaddleLayer(GeoRenderer<T,O, R> renderer, String id) {
        super(renderer, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"textures/entity/equipment/dragon/"+id+".png"));
        this.id = id;
    }
}
