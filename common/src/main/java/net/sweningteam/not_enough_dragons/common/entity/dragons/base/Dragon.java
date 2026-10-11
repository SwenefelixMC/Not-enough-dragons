package net.sweningteam.not_enough_dragons.common.entity.dragons.base;

import com.geckolib.animatable.GeoEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import net.sweningteam.not_enough_dragons.common.config.ModGamePlayConfig;
import net.sweningteam.not_enough_dragons.common.config.ModMainConfig;
import net.sweningteam.not_enough_dragons.common.network.packages.c2s.DragonRiderInputPackage;
import net.sweningteam.not_enough_dragons.common.registry.ModAttributes;
import net.sweningteam.not_enough_dragons.common.registry.ModTags;
import org.jetbrains.annotations.Nullable;

public abstract class Dragon extends Animal implements GeoEntity {

    @Override
    public LivingEntity getControllingPassenger() {
        if(getFirstPassenger() instanceof Player player){
            return player;
        }
        return super.getControllingPassenger();
    }

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

    public abstract void setOffspringAttributes(Dragon parent, Dragon child);

    public void setOffspringAttribute(Dragon parent, Dragon child, Holder<Attribute> attribute, double min, double max){
        double d = createOffspringAttribute(this.getAttributeBaseValue(attribute), parent.getAttributeBaseValue(attribute), min, max, this.random);
        child.getAttribute(attribute).setBaseValue(d);
    }

    public static double createOffspringAttribute(double value1, double value2, double min, double max, RandomSource random){
        if (max <= min) {
            throw new IllegalArgumentException("Incorrect range for an attribute");
        }else {
            value1 = Mth.clamp(value1, min, max);
            value2 = Mth.clamp(value2, min, max);
            double d = 0.15 * (max - min);
            double e = Math.abs(value1 - value2) + d * (double)2.0F;
            double f = (value1 + value2) / (double)2.0F;
            double g = (random.nextDouble() + random.nextDouble() + random.nextDouble()) / (double)3.0F - (double)0.5F;
            double h = f + e * g;
            if (h > max) {
                double i = h - max;
                return max - i;
            } else if (h < min) {
                double i = min - h;
                return min + i;
            } else {
                return h;
            }
        }
    }

    public void randomizeAttributes(RandomSource random){

    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnGroupData) {
        this.randomizeAttributes(this.random);
        return super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
    }

    public void setOwnAttributes(Holder<Attribute> attribute, double min, double max){
        double value = min + (max-min)* this.random.nextDouble();
        AttributeInstance attributeInstance = this.getAttribute(attribute);
        if(attributeInstance!= null){
            attributeInstance.setBaseValue(value);
        }
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    public<T extends LivingEntity> boolean entityIsDanger(T entity){
        if (entity instanceof Player || entity instanceof Raider || entity instanceof Dragon){
            ItemStack MainStack = entity.getItemInHand(InteractionHand.MAIN_HAND);
            ItemStack OffStack = entity.getItemInHand(InteractionHand.OFF_HAND);
            return this.dangerousItemInStack(MainStack) ||
                    this.dangerousItemInStack(OffStack) ||
                    entity.hasItemInSlot(EquipmentSlot.BODY) ||
                    entity.hasItemInSlot(EquipmentSlot.FEET) ||
                    entity.hasItemInSlot(EquipmentSlot.LEGS) ||
                    entity.hasItemInSlot(EquipmentSlot.HEAD);
        }else{
            return false;
        }

    }


    public boolean dangerousItemInStack(ItemStack stack) {
        return stack.is(ModTags.Items.DRAGON_PROVOCATIVE);
    }

    public boolean canGetAngryAt(LivingEntity entity){
        return !(entity == this) && (entity.is(ModTags.EntityTypes.NIGHT_FURY_GETS_ANGRY_AT) || entity instanceof Player);
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

    public void handlePlayerInputPackage(ServerPlayer player, DragonRiderInputPackage packet){
        setInputs(packet.forward(),packet.backward(),packet.sprint(), packet.jump(), packet.charge());
    }
    public void setInputs(boolean forward, boolean backward, boolean sprint, boolean jump, boolean charge){

    };

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

    public void notifyEggSteal(Player player){

    }

    public static boolean checkDragonSpawnRules(EntityType<? extends Dragon> entityType, LevelAccessor level, EntitySpawnReason spawnReason, BlockPos pos, RandomSource random) {
        return level.getBlockState(pos.below()).is(ModTags.Blocks.DRAGONS_SPAWNS_ON) && isBrightEnoughToSpawn(level, pos);
    }
    public boolean canPickupItem(ItemEntity itemEntity){
        return false;
    }

    public boolean canBeControledBy(Player player) {
    return false;
    }

    public boolean canAttackPillager(LivingEntity entity, ServerLevel level){
        return ModGamePlayConfig.dragonsFightRaiders;
    }

    static {
        EATING_PROGRESS = SynchedEntityData.defineId(Dragon.class, EntityDataSerializers.INT);
    }
}
