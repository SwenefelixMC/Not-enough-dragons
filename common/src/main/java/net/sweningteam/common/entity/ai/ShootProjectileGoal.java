package net.sweningteam.common.entity.ai;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.monster.Ghast;
import net.minecraft.world.phys.Vec3;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.entity.dragons.base.Dragon;
import net.sweningteam.common.entity.dragons.base.interfaces.ShootingDragon;

public class ShootProjectileGoal extends Goal {
    private final Dragon dragon;
    private final ShootingDragon shootingDragon;

    public ShootProjectileGoal(Dragon dragon) {
        this.dragon = dragon;
        if (!(dragon instanceof ShootingDragon)){
            NotEnoughDragons.LOGGER.error("Dragon must implement ShootingDragon, Please notify the mod Authors About this");
            shootingDragon = null;
        }else {
            shootingDragon = (ShootingDragon) dragon;
        }
    }

    @Override
    public boolean canUse() {
        return shootingDragon != null && dragon.getTarget() != null;
    }

    @Override
    public void start() {
        shootingDragon.setChargingShot(true);
    }

    @Override
    public void stop() {
        shootingDragon.setChargingShot(false);
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = dragon.getTarget();
        if( target != null){
            if(target.distanceToSqr(this.dragon) <  4096.0D && this.dragon.hasLineOfSight(target)){
                shootingDragon.setChargeTimer(shootingDragon.chargeTimer()+1);
                if (shootingDragon.chargeTimer() >= shootingDragon.maxChargeTimer()){
                    if (target.distanceToSqr(this.dragon) > 8D && shootingDragon.canShoot()){
                        shootingDragon.performRangedAttack(target,(float) dragon.getDeltaMovement().distanceTo(new Vec3(0,0,0)),dragon);
                        shootingDragon.setChargeTimer(-40);
                    }
                }
            } else if (shootingDragon.chargeTimer()> 0) {
                shootingDragon.setChargeTimer(shootingDragon.chargeTimer()-1);
            }
        }
    }
}
