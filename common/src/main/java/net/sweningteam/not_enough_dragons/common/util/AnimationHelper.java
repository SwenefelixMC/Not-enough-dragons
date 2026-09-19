package net.sweningteam.not_enough_dragons.common.util;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;

public class AnimationHelper implements GeoEntity {
    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return null;
    }
    @SuppressWarnings("SameReturnValue")
    public static <A extends GeoEntity> PlayState loopAnim(String Anim, AnimationTest<A> event){
        event.controller().setAnimation(RawAnimation.begin().thenLoop(Anim));
        return PlayState.CONTINUE;
    }
}
