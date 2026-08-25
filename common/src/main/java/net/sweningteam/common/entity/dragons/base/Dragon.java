package net.sweningteam.common.entity.dragons.base;

import net.minecraft.core.BlockPos;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.ProjectileWeaponItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.dragon.food.DragonFoodComponentType;
import net.sweningteam.common.entity.dragons.base.interfaces.TrustingDragon;
import net.sweningteam.common.network.packages.c2s.DragonRiderInputPackage;
import net.sweningteam.common.registry.ModTags;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoEntity;

public abstract class Dragon extends Animal implements GeoEntity, TrustingDragon {

    @Override
    public LivingEntity getControllingPassenger() {
        if(getFirstPassenger() instanceof Player player){
            return player;
        }
        return super.getControllingPassenger();
    }

    public abstract DragonFoodComponentType dragonFoodComponentType();

    public abstract boolean tamingConditions(LivingEntity living);

    private static final EntityDataAccessor<Integer> EATING_PROGRESS;

    public void SetEatingProgress(int value){
        this.entityData.set(EATING_PROGRESS,value);
    }

    public boolean isEating(){
        return this.entityData.get(EATING_PROGRESS)> 0;
    }

    protected Dragon(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public boolean isMoving() {return getDeltaMovement().z() != 0 || getDeltaMovement().x() != 0;}

    double lastWallSpeed= 0;
    double wallSpeed = 0;

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(EATING_PROGRESS,0);
        super.defineSynchedData(builder);
    }



    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    public boolean entityIsDanger(LivingEntity entity){
        ItemStack MainStack = entity.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack OffStack = entity.getItemInHand(InteractionHand.OFF_HAND);
        return  !entity.isShiftKeyDown() ||
                this.dangerousItemInStack(MainStack) ||
                this.dangerousItemInStack(OffStack) ||
                entity.hasItemInSlot(EquipmentSlot.BODY) ||
                entity.hasItemInSlot(EquipmentSlot.FEET) ||
                entity.hasItemInSlot(EquipmentSlot.LEGS) ||
                entity.hasItemInSlot(EquipmentSlot.HEAD);
    }


    public boolean dangerousItemInStack(ItemStack stack) {
        return stack.is(ModTags.Items.DRAGON_PROVOCATIVE);
    }

    public boolean canGetAngryAt(LivingEntity entity){
        return !(entity == this) && (entity.getType().is(ModTags.EntityTypes.NIGHT_FURY_GETS_ANGRY_AT) || entity instanceof Player);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putDouble("LastWallSpeed",lastWallSpeed);
        output.putDouble("WallSpeed", wallSpeed);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        lastWallSpeed = input.getDoubleOr("LastWallSpeed", getDeltaMovement().horizontalDistance());
        wallSpeed = input.getDoubleOr("WallSpeed", getDeltaMovement().horizontalDistance());
    }

    @Override
    public void tick() {
        if(!this.level().isClientSide()) {
            if (this.entityData.get(EATING_PROGRESS) > 0) {
                this.entityData.set(EATING_PROGRESS,this.entityData.get(EATING_PROGRESS)- 1);
            }
            updateTrackedData();
        }
        handelImpacktDamage();
        super.tick();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    public boolean canBeControledBy(Player player){
        if(player == null){
            return false;
        }
        if(trust.getTrust(player.getUUID()) > 150) {
            return true;
        }
        return player.isCreative() || player.isSpectator();
    }

    public void handlePlayerInputPackage(ServerPlayer player, DragonRiderInputPackage packet){
        setInputs(packet.forward(),packet.backward(),packet.sprint(), packet.jump());
    }
    protected abstract void setInputs(boolean forward, boolean backward, boolean sprint, boolean jump);

    public void handelImpacktDamage(){


    }
    public void updateTrackedData(){
        if(!this.level().isClientSide()){
            ServerLevel serverLevel = (ServerLevel) this.level();
            wallSpeed = getDeltaMovement().horizontalDistance();
            if (this.horizontalCollision) {
                double d = lastWallSpeed - wallSpeed;
                float f = (float)(d * (double)10.0F - (double)8.0F);
                if (f > 0.0F) {
                    this.hurtServer(serverLevel,this.damageSources().flyIntoWall(), f);
                }
                lastWallSpeed = wallSpeed;
            }
        }
    }

    public static boolean checkDragonSpawnRules(EntityType<? extends Dragon> entityType, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(ModTags.Blocks.DRAGONS_SPAWNS_ON) && isBrightEnoughToSpawn(level, pos);
    }


    static {
        EATING_PROGRESS = SynchedEntityData.defineId(Dragon.class, EntityDataSerializers.INT);
    }
}
