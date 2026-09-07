package net.sweningteam.common.entity.egg;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.sweningteam.common.entity.dragons.base.Dragon;
import net.sweningteam.common.entity.dragons.bolder_class.gronckle.Gronckle;
import net.sweningteam.common.entity.egg.base.DragonEgg;
import net.sweningteam.common.registry.ModEntityTypes;
import net.sweningteam.common.registry.ModItems;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;

public class GronckleEgg extends DragonEgg implements GeoEntity {
    public GronckleEgg(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public Dragon getDragon(Level level) {
        return new Gronckle(ModEntityTypes.GRONCKLE.get(), level);
    }

    @Override
    protected void actuallyHurt(ServerLevel level, DamageSource damageSource, float amount) {
        if(damageSource.is(DamageTypes.EXPLOSION)){
            super.actuallyHurt(level, damageSource, 0);
        }
        super.actuallyHurt(level, damageSource, amount);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        if(damageSource.is(DamageTypes.EXPLOSION)){
            return false;
        }
        return super.hurtServer(level, damageSource, amount);
    }

    @Override
    public void onHatch(Dragon dragon) {
        this.level().explode(this,getX(),getY(),getZ(),2,true,Level.ExplosionInteraction.TNT);
        super.onHatch(dragon);
    }

    public static AttributeSupplier.Builder createAtributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH,20)
                .add(Attributes.ARMOR,8)
                .add(Attributes.FOLLOW_RANGE,20);
    }

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public Item EggItem() {
        return ModItems.GRONCKLE_EGG.get();
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }
}
