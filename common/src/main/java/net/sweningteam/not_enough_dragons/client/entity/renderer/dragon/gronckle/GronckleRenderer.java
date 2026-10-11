package net.sweningteam.not_enough_dragons.client.entity.renderer.dragon.gronckle;

import com.geckolib.cache.model.GeoBone;
import com.geckolib.constant.DataTickets;
import com.geckolib.renderer.base.BoneSnapshots;
import com.geckolib.renderer.base.GeoRenderState;
import com.geckolib.renderer.base.RenderPassInfo;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.util.Mth;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
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

    @Override
    public void adjustModelBonesForRender(RenderPassInfo<R> renderPassInfo, BoneSnapshots snapshots) {
        GeoBone head = renderPassInfo.model().getBone("Head").orElse(null);
        float HeadPitch = 0F;
        float HeadYaw = 0F;
        try {
            HeadPitch = renderPassInfo.getGeckolibData(DataTickets.ENTITY_PITCH);
            HeadYaw = renderPassInfo.getGeckolibData(DataTickets.ENTITY_YAW);
        }catch (NullPointerException e){
            NotEnoughDragons.LOGGER.error(e.getMessage());
        }
        if(head != null){
            snapshots.get(head).setRotY(-1*HeadYaw*Mth.DEG_TO_RAD);
            snapshots.get(head).setRotX(-1*HeadPitch*Mth.DEG_TO_RAD);
        }
        super.adjustModelBonesForRender(renderPassInfo, snapshots);
    }
}
