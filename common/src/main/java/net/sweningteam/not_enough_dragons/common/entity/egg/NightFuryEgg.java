package net.sweningteam.not_enough_dragons.common.entity.egg;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;
import net.sweningteam.not_enough_dragons.common.entity.dragons.strike_class.night_furry.NightFury;
import net.sweningteam.not_enough_dragons.common.entity.egg.base.DragonEgg;
import net.sweningteam.not_enough_dragons.common.registry.ModEntityTypes;
import net.sweningteam.not_enough_dragons.common.registry.ModItems;


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

    @Override
    public Item EggItem() {
        return ModItems.NIGHT_FURY_EGG.get();
    }

    public static AttributeSupplier.Builder createAtributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH,20)
                .add(Attributes.ARMOR,2)
                .add(Attributes.FOLLOW_RANGE,20);
    }
}
