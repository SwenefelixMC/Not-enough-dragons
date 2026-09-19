package net.sweningteam.not_enough_dragons.common.entity.ai;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.pathfinder.PathType;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.TamableDragon;
import org.jetbrains.annotations.Nullable;

public class DragonFolowOwnerGoal extends Goal {
    private final TamableDragon dragon;
    @Nullable
    private LivingEntity owner;
    private final double speedModifier;
    private final PathNavigation navigation;
    private int timeToRecalcPath;
    private final float startDistance;
    private final float stopDistance;
    private float oldWaterCost;

    public DragonFolowOwnerGoal(TamableDragon dragon, double speedModifier, float startDistance, float stopDistance){
        this.dragon = dragon;
        this.owner = dragon.getOwner();
        this.speedModifier = speedModifier;
        this.navigation = dragon.getNavigation();
        this.startDistance = startDistance;
        this.stopDistance = stopDistance;
        if (!(dragon.getNavigation() instanceof GroundPathNavigation) && !(dragon.getNavigation() instanceof FlyingPathNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowOwnerGoal");
        }
    }

    @Override
    public boolean canUse() {
        LivingEntity livingentity = this.dragon.getOwner();
        if (livingentity == null) {
            return false;
        } else if (this.dragon.unableToMoveToOwner()) {
            return false;
        } else if (this.dragon.distanceToSqr(livingentity) < (double) (this.startDistance * this.startDistance)) {
            return false;
        } else {
            this.owner = livingentity;
            return true;
        }
    }
    public boolean canContinueToUse() {
        if (this.navigation.isDone()) {
            return false;
        } else {
            return !this.dragon.unableToMoveToOwner() && !(this.dragon.distanceToSqr(this.owner) <= (double) (this.stopDistance * this.stopDistance));
        }
    }

    public void start() {
        this.timeToRecalcPath = 0;
        this.oldWaterCost = this.dragon.getPathfindingMalus(PathType.WATER);
        this.dragon.setPathfindingMalus(PathType.WATER, 0.0F);
    }

    public void stop() {
        this.owner = null;
        this.navigation.stop();
        this.dragon.setPathfindingMalus(PathType.WATER, this.oldWaterCost);
    }

    public void tick() {
        this.dragon.getLookControl().setLookAt(this.owner, 10.0F, (float)this.dragon.getMaxHeadXRot());

        if (--this.timeToRecalcPath <= 0) {
            this.timeToRecalcPath = this.adjustedTickDelay(10);
            this.navigation.moveTo(this.owner, this.speedModifier);
        }
    }
}
