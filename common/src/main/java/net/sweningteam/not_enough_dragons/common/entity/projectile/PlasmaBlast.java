package net.sweningteam.not_enough_dragons.common.entity.projectile;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import net.sweningteam.not_enough_dragons.common.entity.projectile.base.BlastProjectile;

public class PlasmaBlast extends BlastProjectile implements GeoEntity {
    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    public PlasmaBlast(EntityType<? extends PlasmaBlast> entityType, Level level) {
        super(entityType, level);
    }

    public PlasmaBlast(EntityType<? extends PlasmaBlast> entityType, LivingEntity owner, Vec3 movement, Level level){
        super(entityType,owner,movement,level);
    }

    int Time = 400;

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("ExplodeTime",Time);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        Time = input.getIntOr("ExplodeTime",400);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {

    }

    @Override
    public void tick() {
        if (!level().isClientSide()){
            if (Time > 0){
                Time -= 1;
            }
            if (Time == 0){
                kill((ServerLevel) this.level());
            }
        }
        super.tick();
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (!level().isClientSide()){
            Entity entity = result.getEntity();
            if (entity != this){
                if (!entity.fireImmune()){
                    entity.setSharedFlagOnFire(true);
                    entity.setRemainingFireTicks(20);
                }
            }
            if(Time > 20){
                this.level().explode(this,getX(),getY(),getZ(),2,true, Level.ExplosionInteraction.TNT);
            }
            kill((ServerLevel) this.level());

        }
        super.onHitEntity(result);
    }

    @Override
    protected void onHitBlock(BlockHitResult result) {
        if (!level().isClientSide()){
            level().setBlock(result.getBlockPos().offset(0,1,0), Blocks.FIRE.defaultBlockState(),0);
            if(Time > 20){
                this.level().explode(this,getX(),getY(),getZ(),2,true, Level.ExplosionInteraction.TNT);
            }
            kill((ServerLevel) this.level());
        }
        super.onHitBlock(result);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    protected void onDeflection(boolean deflectedByPlayer) {
        if(Time > 20){
            this.level().explode(this,getX(),getY(),getZ(),2,true, Level.ExplosionInteraction.TNT);
        }
        super.onDeflection(deflectedByPlayer);
    }
}
