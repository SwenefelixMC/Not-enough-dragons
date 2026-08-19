package net.sweningteam.common.entity.projectile.base;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractHurtingProjectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.sweningteam.common.entity.projectile.PlasmaBlast;

public abstract class BlastProjectile extends AbstractHurtingProjectile {
    protected BlastProjectile(EntityType<? extends AbstractHurtingProjectile> entityType, Level level) {
        super(entityType, level);
    }
    public BlastProjectile(EntityType<? extends PlasmaBlast> entityType, LivingEntity owner, Vec3 movement, Level level){
        super(entityType,owner,movement,level);
    }
    @Override
    protected boolean shouldBurn() {
        return false;
    }
}
