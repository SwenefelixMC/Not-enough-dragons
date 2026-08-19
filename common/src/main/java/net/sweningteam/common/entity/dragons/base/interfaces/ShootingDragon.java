package net.sweningteam.common.entity.dragons.base.interfaces;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sweningteam.common.entity.dragons.base.Dragon;
import net.sweningteam.common.entity.projectile.base.BlastProjectile;

public interface ShootingDragon {
    BlastProjectile createBlast(LivingEntity target, float velocity);

    int chargeTimer();

    int maxChargeTimer();

    void setChargeTimer(int chargeTimer);

    boolean chargingShot();

    void setChargingShot(boolean bl);

    default boolean canShoot(){
        return true;
    }

    default void performRangedAttack(LivingEntity target, float velocity, Dragon dragon) {
        Level level = dragon.level();
        BlastProjectile Blast = createBlast(target,velocity);
        Blast.setPos(new Vec3(dragon.getX(),dragon.getEyeY(),dragon.getZ()));
        Blast.setYRot(dragon.getYHeadRot());
        if (level instanceof ServerLevel serverLevel) {
            dragon.level().addFreshEntity(Blast);
        }

        dragon.playSound(SoundEvents.BLAZE_SHOOT, 1.0F, 1.0F / (dragon.getRandom().nextFloat() * 0.4F + 0.8F));
    }
}
