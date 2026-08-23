package net.sweningteam.common.entity.egg;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.Level;
import net.sweningteam.common.entity.dragons.base.Dragon;
import net.sweningteam.common.entity.dragons.strike_class.night_furry.NightFury;
import net.sweningteam.common.entity.egg.base.DragonEgg;
import net.sweningteam.common.registry.ModEntityTypes;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animatable.processing.AnimationTest;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

public class NightFuryEgg extends DragonEgg implements GeoEntity {
    public NightFuryEgg(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
        this.HatchAge = 3;
    }

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    @Override
    public Dragon getDragon(Level level) {
        return new NightFury(ModEntityTypes.NIGHT_FURY.get(), level);
    }

    @Override
    public HumanoidArm getMainArm() {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>("main",0,this::mainController));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    private <A extends GeoAnimatable> PlayState mainController(AnimationTest<A> event){
        event.setAndContinue(RawAnimation.begin().thenPlay("animations.egg.Idel"));
        return PlayState.CONTINUE;
    }

    public static AttributeSupplier.Builder createAtributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH,20)
                .add(Attributes.ARMOR,2)
                .add(Attributes.FOLLOW_RANGE,20);
    }
}
