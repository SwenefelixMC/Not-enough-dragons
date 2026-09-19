package net.sweningteam.not_enough_dragons.client.entity.model.dragon.nightfury;

import com.geckolib.model.GeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import net.minecraft.resources.Identifier;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;

public class NightFuryModel<E extends Dragon> extends GeoModel<E> {
    @Override
    public Identifier getModelResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"entity/dragon/night_fury");
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "textures/entity/dragon/night_fury/night_fury.png");
    }

    @Override
    public Identifier getAnimationResource(E animatable) {
        //return null;
        return Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "entity/dragon/night_fury");
    }
//    @Override
//    public void setCustomAnimations(AnimationState<E> animationState) {
//        super.setCustomAnimations(animationState);
//        GeoBone head = getAnimationProcessor().getBone("HeadRoot");
//        GeoBone Neck = getAnimationProcessor().getBone("NeckRoot");
//        GeoBone NeckBase = getAnimationProcessor().getBone("NeckBaseRoot");
//        float HeadPitch = 0F;
//        float HeadYaw = 0F;
//        try {
//            HeadPitch = animationState.getData(DataTickets.ENTITY_PITCH);
//            HeadYaw = animationState.getData(DataTickets.ENTITY_YAW);
//        }catch (NullPointerException e){
//            NotEnoughDragons.LOGGER.error(e.getMessage());
//        }
//
//        if(head != null ){
//            head.setRotY(Mth.clamp (HeadYaw* Mth.DEG_TO_RAD,-5.0F, 5.0F));
//        }
//        if( Neck != null){
//            Neck.setRotX(Mth.clamp (HeadPitch* Mth.DEG_TO_RAD, -10.0F,10.0F));
//            Neck.setRotY(Mth.clamp (HeadYaw* Mth.DEG_TO_RAD,-7.5F,7.5F));
//        }
//        if(NeckBase != null){
//            NeckBase.setRotX(Mth.clamp(HeadPitch * Mth.DEG_TO_RAD,-12.5F,12.5F));
//            NeckBase.setRotY(Mth.clamp (HeadYaw* Mth.DEG_TO_RAD, -15.0F,15.0F));
//        }
//    }
}
