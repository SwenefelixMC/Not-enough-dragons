package net.sweningteam.not_enough_dragons.common.entity.dragons.base.interfaces;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;
import net.sweningteam.not_enough_dragons.common.entity.projectile.base.BlastProjectile;
import net.sweningteam.not_enough_dragons.common.registry.ModAttributes;

public interface ShootingDragon {
    BlastProjectile createBlast(LivingEntity target, float velocity);

    BlastProjectile createBlast();

    int chargeTimer();

    int maxChargeTimer();

    void setChargeTimer(int chargeTimer);

    boolean chargingShot();

    void setChargingShot(boolean bl);

    default boolean canShoot(){
        return this.BlastsLeft() > 0;
    }

    int BlastsLeft();

    void SetBlastsLeft(int BlastsLeft);

    int getBlastRestorTimer();

    void setBlastRestorTimer(int blastRestorTimer);

    default void updateBlastRestoreTimer(Dragon dragon){
        if(getBlastRestorTimer() > 0){
            setBlastRestorTimer(getBlastRestorTimer()-1);
        }if(BlastsLeft() < dragon.getAttributeValue(ModAttributes.DRAGON_SHOT_LIMIT)){
            SetBlastsLeft(BlastsLeft()+1);
            setBlastRestorTimer(1000);
        }
    }

    default void onSooth(Dragon dragon){

    }

    default void performRangedAttack(LivingEntity target, float velocity, Dragon dragon) {
        Level level = dragon.level();
        BlastProjectile Blast = createBlast(target,velocity);
        Blast.setPos(new Vec3(dragon.getX(),dragon.getEyeY(),dragon.getZ()));
        Blast.setYRot(dragon.getYHeadRot());
        this.SetBlastsLeft(this.BlastsLeft()-1);
        this.setBlastRestorTimer(10000);
        if(this.BlastsLeft() <= 0){
            this.setChargeTimer(-20000);
        }
        this.setChargeTimer(0);
        if (level instanceof ServerLevel serverLevel) {
            serverLevel.addFreshEntity(Blast);
        }

        dragon.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F / (dragon.getRandom().nextFloat() * 0.4F + 0.8F));
    }

    default void performRangedAttackFromRider(float velocity, Dragon dragon) {
        Level level = dragon.level();
        BlastProjectile Blast = createBlast();
        Blast.shootFromRotation(dragon,dragon.getXRot(),dragon.getControllingPassenger().getYHeadRot(),0.0f,10,0);
        Blast.setPos(new Vec3(dragon.getX()+dragon.getLookAngle().x*2,dragon.getEyeY()+dragon.getLookAngle().y*2,dragon.getZ()+dragon.getLookAngle().z*2));
        Blast.setYRot(dragon.getControllingPassenger().yHeadRot);
        this.SetBlastsLeft(this.BlastsLeft()-1);
        this.setBlastRestorTimer(10000);
        if(this.BlastsLeft() <= 0){
            this.setChargeTimer(-20000);
        }
        this.setChargeTimer(0);
        if(level instanceof ServerLevel serverLevel) {
            serverLevel.addFreshEntity(Blast);
        }
        dragon.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F / (dragon.getRandom().nextFloat() * 0.4F + 0.8F));
    }
}
