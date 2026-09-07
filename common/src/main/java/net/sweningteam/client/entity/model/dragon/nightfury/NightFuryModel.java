package net.sweningteam.client.entity.model.dragon.nightfury;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.entity.dragons.base.Dragon;
import software.bernie.geckolib.animatable.processing.AnimationState;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.constant.DataTickets;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.base.GeoRenderState;

public class NightFuryModel<E extends Dragon> extends GeoModel<E> {
    @Override
    public ResourceLocation getModelResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,"entity/dragon/night_fury.geo.json");
    }

    @Override
    public ResourceLocation getTextureResource(GeoRenderState renderState) {
        return ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "textures/entity/dragon/night_fury/night_fury.png");
    }

    @Override
    public ResourceLocation getAnimationResource(E animatable) {
        return ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "entity/dragon/night_fury.animation.json");
    }
    @Override
    public void setCustomAnimations(AnimationState<E> animationState) {
        super.setCustomAnimations(animationState);
        GeoBone head = getAnimationProcessor().getBone("HeadRoot");
        GeoBone Neck = getAnimationProcessor().getBone("NeckRoot");
        GeoBone NeckBase = getAnimationProcessor().getBone("NeckBaseRoot");
        float HeadPitch = 0F;
        float HeadYaw = 0F;
        try {
            HeadPitch = animationState.getData(DataTickets.ENTITY_PITCH);
            HeadYaw = animationState.getData(DataTickets.ENTITY_YAW);
        }catch (NullPointerException e){
            NotEnoughDragons.LOGGER.error(e.getMessage());
        }

        if(head != null ){
            head.setRotY(Mth.clamp (HeadYaw* Mth.DEG_TO_RAD,-5.0F, 5.0F));
        }
        if( Neck != null){
            Neck.setRotX(Mth.clamp (HeadPitch* Mth.DEG_TO_RAD, -10.0F,10.0F));
            Neck.setRotY(Mth.clamp (HeadYaw* Mth.DEG_TO_RAD,-7.5F,7.5F));
        }
        if(NeckBase != null){
            NeckBase.setRotX(Mth.clamp(HeadPitch * Mth.DEG_TO_RAD,-12.5F,12.5F));
            NeckBase.setRotY(Mth.clamp (HeadYaw* Mth.DEG_TO_RAD, -15.0F,15.0F));
        }
    }

}
