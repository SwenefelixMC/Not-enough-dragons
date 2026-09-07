package net.sweningteam.common.entity.dragons.strike_class.night_furry;

import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.dragon.food.DragonFoodComponentType;
import net.sweningteam.common.dragon.trust.TrustContainer;
import net.sweningteam.common.entity.ai.DragonFolowOwnerGoal;
import net.sweningteam.common.entity.ai.ShootProjectileGoal;
import net.sweningteam.common.entity.dragons.base.Dragon;
import net.sweningteam.common.entity.dragons.base.TamableDragon;
import net.sweningteam.common.entity.dragons.base.interfaces.*;
import net.sweningteam.common.entity.egg.NightFuryEgg;
import net.sweningteam.common.entity.egg.base.DragonEggData;
import net.sweningteam.common.entity.projectile.PlasmaBlast;
import net.sweningteam.common.entity.projectile.base.BlastProjectile;
import net.sweningteam.common.network.ModNetworkHandler;
import net.sweningteam.common.network.packages.c2s.DragonFlyingUpdatePackage;
import net.sweningteam.common.network.packages.c2s.DragonRiderInputPackage;
import net.sweningteam.common.registry.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animatable.instance.SingletonAnimatableInstanceCache;
import software.bernie.geckolib.animatable.manager.AnimatableManager;
import software.bernie.geckolib.animatable.processing.AnimationController;
import software.bernie.geckolib.animatable.processing.AnimationTest;
import software.bernie.geckolib.animation.PlayState;
import software.bernie.geckolib.animation.RawAnimation;

import java.util.*;

public class NightFury extends TamableDragon implements NeutralMob, EatingDragon, PlayerRideableJumping , DragonWithScales, FlyingDragon, ShootingDragon, SaddleAbblDragon {
    TrustContainer Trust = new TrustContainer();
    Random random = new Random();

    private static final EntityDataAccessor<Boolean> AGGRO;
    private static final EntityDataAccessor<Boolean> SNEAKING;
    private static final EntityDataAccessor<Boolean> SPRINTING;
    private static final EntityDataAccessor<Boolean> BITING;
    private static final EntityDataAccessor<Boolean> BLINKING;
    private static final EntityDataAccessor<Boolean> IS_FLYING;
    private static final EntityDataAccessor<Boolean> CHARCHING_SHOT;
    private static final EntityDataAccessor<Boolean> SADDEL;
    private static final TargetingConditions.Selector TRUST_FILTER;

    private static final EntityDataAccessor<Boolean> MOVE_FORWARD_PRESSED;
    private static final EntityDataAccessor<Boolean> SPRINT_PRESSED;
    private static final EntityDataAccessor<Boolean> MOVE_BACKWARD_PRESSED;
    private static final EntityDataAccessor<Boolean> JUMP_PRESSED;

    static final TargetingConditions FIND_ENTITY_PREDICATE;

    boolean inputForward(){return this.entityData.get(MOVE_FORWARD_PRESSED);};
    boolean inputSlow(){return this.entityData.get(MOVE_BACKWARD_PRESSED);};
    boolean inputSprint(){return this.entityData.get(SPRINT_PRESSED);};

    private int lastPassiveTrustIncrease = 0;

    int hunger = MaxHunger();

    @Override
    public Boolean isFlying() {
        return entityData.get(IS_FLYING);
    }

    @Override
    public void setFlying(Boolean bl) {
        entityData.set(IS_FLYING,bl);
        setNoGravity(bl);
        if(isFlying() != bl){
            NotEnoughDragons.LOGGER.info("Flying Broke!");
        }
    }

    @Override
    public int getHunger() {
        return hunger;
    }

    @Override
    public void setHunger(int hunger) {
        this.hunger = hunger;
    }

    @Override
    public int MaxHunger() {
        return 200;
    }

    int Saturation = MaxSaturation();

    @Override
    public int getSaturation() {
        return Saturation;
    }

    @Override
    public void setSaturation(int saturation) {
        this.Saturation = saturation;
    }

    @Override
    public int MaxSaturation() {
        return 200;
    }

    int HungerUpdateTime = MaxHungerUpdateTime();

    @Override
    public int getRemainingHungerUpdateTime() {
        return this.HungerUpdateTime;
    }

    @Override
    public void setRemainingHungerUpdateTime(int remainingHungerTimer) {
    this.HungerUpdateTime = remainingHungerTimer;
    }

    @Override
    public int MaxHungerUpdateTime() {
        return 35;
    }

    @Override
    public void updateSaturation() {
        if(this.getHealth() < this.getMaxHealth()){
            this.heal(1);
        } else if (this.getHunger() < this.MaxHunger()) {
            setHunger(getHunger() +1);
        }
    }

    @Override
    public DragonFoodComponentType dragonFoodComponentType() {
        return ModDragonFoodComponentTypes.Night_fury;
    }

    @Override
    public boolean tamingConditions(LivingEntity living) {
        return entityIsDanger(living);
    }

    private final AnimatableInstanceCache cache = new SingletonAnimatableInstanceCache(this);

    private int AngerTime = 0;
    private UUID persistentAngerTarget;

    int HungerDecreseTimer = 200;

    boolean oldSwinging = false;
    int swingTime = 0;

    int blinkTimer = 200;
    int blinkProgress = 0;

    protected float playerJumpPendingScale;
    protected boolean allowStandSliding;

    int attackChargeTimer = 0;

    int dangerTimer = 0;

    @Override
    public void setFlyingSpeed(double speed) {
        flyingSpeed = speed;
    }

    @Override
    public double flyingSpeed() {
        return flyingSpeed;
    }

    double flyingSpeed = 0;

    Player closetsPlayer = null;

    @Override
    public boolean hasSaddle(){
        return entityData.get(SADDEL);
    }
    @Override
    public void setSaddel(boolean bl){
        this.entityData.set(SADDEL, bl);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(AGGRO, false);
        builder.define(SNEAKING, false);
        builder.define(SPRINTING,false);
        builder.define(BITING, false);
        builder.define(BLINKING,false);

        builder.define(MOVE_FORWARD_PRESSED,false);
        builder.define(SPRINT_PRESSED, false);
        builder.define(MOVE_BACKWARD_PRESSED, false);
        builder.define(JUMP_PRESSED,false);

        builder.define(IS_FLYING,false);
        builder.define(CHARCHING_SHOT, false);
        builder.define(SADDEL, false);
        super.defineSynchedData(builder);
    }

    private <A extends GeoAnimatable> PlayState faceController(AnimationTest<A> event){
        event.controller().transitionLength(3);
        if(this.entityData.get(BITING)){
            event.setAndContinue(RawAnimation.begin().thenPlay("animation.nightfury.face.bite"));
        }else {
            if(entityData.get(AGGRO)) {
                event.setAndContinue(RawAnimation.begin().thenPlay("animation.nightfury.face.aggro"));
            }else{
                if(isEating()){
                    event.setAndContinue(RawAnimation.begin().thenPlay("animation.nightfury.face.eat"));
                } else {
                    if(this.entityData.get(BLINKING)){
                        event.setAndContinue(RawAnimation.begin().thenPlay("animation.nightfury.face.blink"));
                    }else {
                        if (isTamed()) {
                        event.setAndContinue(RawAnimation.begin().thenPlay("animation.nightfury.face.tamed"));
                        } else{
                        event.setAndContinue(RawAnimation.begin().thenPlay("animation.nightfury.face.idle"));
                        }
                    }
                }
            }
        }
        return PlayState.CONTINUE;
    }

    private <A extends GeoAnimatable> PlayState mainController(AnimationTest<A> event){
        event.controller().transitionLength(5);
        if(this.entityData.get(IS_FLYING)){
            if (this.entityData.get(MOVE_FORWARD_PRESSED)){
                if(this.entityData.get(SPRINT_PRESSED)){
                    event.setAndContinue(RawAnimation.begin().thenPlay("animation.nightfury.SprintFly"));
                }else{
                    event.setAndContinue(RawAnimation.begin().thenPlay("animation.nightfury.Fly"));
                }
            }else{
                event.setAndContinue(RawAnimation.begin().thenPlay("animation.nightfury.Glide"));
            }
        }else {
            if(event.isMoving()){
                if(this.entityData.get(SPRINTING)){
                    event.setAndContinue(RawAnimation.begin().thenPlay("animation.nightfury.Sprint"));
                }else {
                    if(isShiftKeyDown()) {
                        event.setAnimation(RawAnimation.begin().thenPlay("animation.nightfury.SneakWalk"));
                    }else {
                        event.setAnimation(RawAnimation.begin().thenPlay("animation.nightfury.Walk"));
                    }
                }

            }else {
                if(isShiftKeyDown()) {
                    event.setAndContinue(RawAnimation.begin().thenPlay("animation.nightfury.Sneak"));
                }else {
                    event.setAndContinue(RawAnimation.begin().thenPlay("animation.nightfury.Idle"));
                }
            }
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(
                new AnimationController<>("main",0,this::mainController),
                new AnimationController<>("eye",0,this::faceController)
        );
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(2, new ShootProjectileGoal(this));

        this.goalSelector.addGoal(3,new AvoidEntityGoal<>(this,Ravager.class,15F,1F,2F));
        this.goalSelector.addGoal(4,new MeleeAttackGoal(this,1D,false));

        this.goalSelector.addGoal(5, new DragonFolowOwnerGoal(this,1.0D, 16.0F, 4.0F));
        this.goalSelector.addGoal(6,new WaterAvoidingRandomStrollGoal(this,1.0));

        this.goalSelector.addGoal(7,new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1,new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10,true, false, this::isAngryAt));

        this.targetSelector.addGoal(5,new BreedGoal(this,1.0D));

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, true, false,(livingEntity, serverLevel) -> livingEntity.getType().is(ModTags.EntityTypes.NIGHT_FURY_GETS_ANGRY_AT)));
    }

    public static AttributeSupplier.Builder createAtributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH,140)
                .add(Attributes.MOVEMENT_SPEED,0.38)
                .add(Attributes.FLYING_SPEED,1.1)
                .add(Attributes.STEP_HEIGHT,1.1)
                .add(Attributes.ARMOR,1)
                .add(Attributes.ATTACK_DAMAGE,6.5)
                .add(Attributes.ATTACK_KNOCKBACK,1.25)
                .add(Attributes.FOLLOW_RANGE,20)
                .add(Attributes.JUMP_STRENGTH,0.8)
                .add(Attributes.SNEAKING_SPEED,0.1D)
                .add(Holder.direct(ModAttributes.DRAGON_SPRINTING_SPEED.get()),0.7D)
                .add(Holder.direct(ModAttributes.DRAGON_FLYING_SLOWDOWN_SPEED.get()),0.25)
                .add(Holder.direct(ModAttributes.DRAGON_FLYING_SPRINTING_SPEED.get()),2.2);
    }

    public NightFury(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

    }

    @Override
    public BlastProjectile createBlast(LivingEntity target, float velocity) {
        double x = target.getX() - this.getX();
        double y = target.getY() - this.getEyeY();
        double z = target.getZ() - this.getZ();
        Vec3 vec32 = new Vec3(x*velocity, y*velocity, z*velocity);
        return new PlasmaBlast(ModEntityTypes.PLASMA_BLAST.get(),this,vec32.normalize(),this.level());
    }

    @Override
    public int chargeTimer() {
        return attackChargeTimer;
    }

    public void setChargeTimer(int chargeTimer) {
        attackChargeTimer = chargeTimer;
    }

    @Override
    public int maxChargeTimer() {
        return 40;
    }

    @Override
    public boolean chargingShot() {
        return entityData.get(CHARCHING_SHOT);
    }

    @Override
    public void setChargingShot(boolean bl) {
        entityData.set(CHARCHING_SHOT,bl);
    }

    @Override
    public boolean isFlyingVehicle() {
        return true;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        Trust.resortMap();
        output.store("Trust", TrustContainer.CODEC,Trust);
        addPersistentAngerSaveData(output);
        addHungerSaveData(output);
        output.putInt("HungryDecreaseTime", HungerDecreseTimer);
        output.putInt("BlinkTime", blinkTimer);
        addFlyingData(output,this);
        output.putInt("DangerTimer", dangerTimer);
        writeSaddleData(output);
        super.addAdditionalSaveData(output);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        Trust.clear();
        Optional<TrustContainer> trustOptional = input.read("Trust",TrustContainer.CODEC);
        trustOptional.ifPresent(trustContainer -> Trust = trustContainer);
        readPersistentAngerSaveData(this.level(),input);
        readHungerSaveData(input);
        HungerDecreseTimer = input.getIntOr("HungryDecreaseTime",200);
        blinkTimer = input.getIntOr("BlinkTime",200);
        readFlyingData(input,this);
        dangerTimer = input.getIntOr("DangerTimer",0);
        readSaddleData(input);
        super.readAdditionalSaveData(input);
    }

    @Override
    public void updateTrackedData() {
        super.updateTrackedData();
    }

    @Override
    public boolean fireImmune() {
        return true;
    }

    @Override
    public boolean canJump() {
        return !isFlying() && !isInLiquid();
    }

    @Override
    public void handleStartJump(int jumpPower) {

    }

    @Override
    public void handleStopJump() {

    }

    @Override
    public void onPlayerJump(int jumpPower) {
        if(jumpPower <0){
            jumpPower = 0;
        }
        if (jumpPower >= 90){
            this.playerJumpPendingScale = 1.0F;
        }else {
            this.playerJumpPendingScale = 0.4F + 0.4F * (float)jumpPower / 90.0F;
        }
    }

    protected void executeRidersJump(float playerJumpPendingScale, Vec3 travelVector, boolean onGround) {
        if(onGround){
            double jumpingPower = this.getJumpPower(playerJumpPendingScale);
            Vec3 deltaMovement = this.getDeltaMovement();
            this.addDeltaMovement(this.getLookAngle().multiply(1.0D, 0.0D, 1.0D).normalize().scale((16 * playerJumpPendingScale * deltaMovement.horizontalDistance()) * this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (double)this.getBlockSpeedFactor()).add((double)0.0F, (double)(0.1 + 1.1 * playerJumpPendingScale)*1.1 * jumpingPower, (double)0.0F));
            this.playerJumpPendingScale = 0.0F;
        }
        if(playerJumpPendingScale >= 0.8 && !isFlying()){
            ModNetworkHandler.sendC2SPacket(new DragonFlyingUpdatePackage(true));
            this.playerJumpPendingScale = 0.0F;
        }
    }

    @Override
    public boolean handleFood(Player player, Item item, Boolean onGround) {
        if(dragonFoodComponentType().contains(item)){
            if (onGround){
                Trust.increaseTrust(player.getUUID(),dragonFoodComponentType().get(item).getTrust_gained()/2);
            }else {
                Trust.increaseTrust(player.getUUID(),dragonFoodComponentType().get(item).getTrust_gained());
            }
            dragonFoodComponentType().get(item).onEat(this);
            startHungerUpdate(this.dragonFoodComponentType().get(item).getFood_gained());
            SetEatingProgress(35);
            return true;
        }
        return false;
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
    @Override
    public void aiStep() {
        super.aiStep();
        updateSwingTime();
        if(!this.level().isClientSide()) {
            this.updatePersistentAnger((ServerLevel) this.level(),true);
        }
    }

    public void updateSwinging(){
        if(this.swingTime >0){
            this.swingTime = this.swingTime -1;
            this.entityData.set(BITING,true);
        }else {
            this.entityData.set(BITING, false);
        }
        if(this.swinging && !this.oldSwinging){
            this.swingTime = 12;
        }
        this.oldSwinging = this.swinging;
    }

    public void updateBlinking(){
        if(this.blinkTimer > 0){
            this.blinkTimer = blinkTimer -1;
        }else {
            this.blinkTimer = 100 + random.nextInt(150);
            this.blinkProgress = 11;
        }
        if(this.blinkProgress > 0){
            this.blinkProgress = this.blinkProgress -1;
            this.entityData.set(BLINKING,true);
        }else {
            this.blinkProgress = 0;
            this.entityData.set(BLINKING,false);
        }
    }

    public void BigBox(){
        boolean Should_be_sneaking = false;
        boolean Should_be_aggressive = false;
        boolean Should_be_aggro = false;
        String msg = "";
        List<Entity> list = this.level().getEntitiesOfClass(Entity.class,this.getHitbox().inflate(20)).stream().toList();
        for ( int i = 1; i < list.size(); i++){
            if (list.get(i) instanceof LivingEntity livingEntity){
                boolean canBeAgroAtEntity = canGetAngryAt(livingEntity);

                if(livingEntity instanceof Player player) {
                    if(closetsPlayer == null){
                        closetsPlayer = player;
                    }else{
                        if(this.distanceTo(closetsPlayer)>this.distanceTo(player)){
                            closetsPlayer = player;
                        }
                    }
                    canBeAgroAtEntity = !player.isCreative() && !player.isSpectator() && this.getOwner() != player;
                }
                if(canBeAgroAtEntity){
                    if(this.distanceTo(livingEntity) <= 18 ){
                        Should_be_sneaking = true;
                    }
                    if (Trust.getTrust(livingEntity.getUUID()) < 50D){
                        if(this.distanceTo(livingEntity) <= 12 ){
                            Should_be_aggro = true;
                            if(entityIsDanger(livingEntity)){
                                if(dangerTimer <= 100 && getTarget() != null){
                                    dangerTimer += 1;
                                }else {
                                    dangerTimer = 0;
                                    setTarget(livingEntity);
                                    Should_be_aggressive = true;
                                }
                            }
                        }
                        if(this.distanceTo(livingEntity) <= 6 ){
                            setTarget(livingEntity);
                            Should_be_aggressive = true;
                        }
                    }else {
                        if(this.distanceTo(livingEntity) <= 6 ){
                            Should_be_aggro = true;
                            if(entityIsDanger(livingEntity)){
                                setTarget(livingEntity);
                                Should_be_aggressive = true;
                            }
                        }
                    }
                }
            }
        }
        if(Should_be_sneaking){
            this.setShiftKeyDown(true);
        }
        if(Should_be_aggressive){
            setRemainingPersistentAngerTime(200);
        }
        entityData.set(AGGRO, Should_be_aggro);
    }

    public void AngerManager(){
    this.BigBox();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return new NightFury(ModEntityTypes.NIGHT_FURY.get(),level);
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal mate) {
        NightFuryEgg egg = new NightFuryEgg(ModEntityTypes.NIGHT_FURY_EGG.get(), level);
        NightFury nightFury = new NightFury(ModEntityTypes.NIGHT_FURY.get(),level);
        egg.setPos(this.position());
        setOffspringAttributes((Dragon) mate,nightFury);
        egg.setDragonEgg(true,0,0,new DragonEggData(
                Objects.requireNonNull(nightFury.getAttribute(Attributes.ATTACK_DAMAGE)).getBaseValue(),
                Objects.requireNonNull(nightFury.getAttribute(Attributes.ATTACK_KNOCKBACK)).getBaseValue(),
                Objects.requireNonNull(nightFury.getAttribute(Attributes.JUMP_STRENGTH)).getBaseValue(),
                Objects.requireNonNull(nightFury.getAttribute(Attributes.SNEAKING_SPEED)).getBaseValue(),
                Objects.requireNonNull(nightFury.getAttribute(Attributes.MOVEMENT_SPEED)).getBaseValue(),
                Objects.requireNonNull(nightFury.getAttribute(Attributes.FLYING_SPEED)).getBaseValue(),
                Objects.requireNonNull(nightFury.getAttribute(Holder.direct(ModAttributes.DRAGON_SPRINTING_SPEED.get()))).getBaseValue(),
                Objects.requireNonNull(nightFury.getAttribute(Holder.direct(ModAttributes.DRAGON_FLYING_SPRINTING_SPEED.get()))).getBaseValue(),
                Objects.requireNonNull(nightFury.getAttribute(Holder.direct(ModAttributes.DRAGON_SPRINTING_SPEED.get()))).getBaseValue()
        ));
        this.finalizeSpawnChildFromBreeding(level, mate, nightFury);
        level.addFreshEntity(egg);
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Items.RABBIT_STEW);
    }

    @Override
    public void tick() {
        setSpeed((float) getAttributeValue(Attributes.MOVEMENT_SPEED));
        if(!this.level().isClientSide()){
            setShiftKeyDown(false);
            setSprinting(false);
        }
        super.tick();
        if(!this.level().isClientSide()){
            this.setDeltaMovement(this.getDeltaMovement().x,this.getDeltaMovement().y-0.01,this.getDeltaMovement().z);
            updtateFlying(this);
            if (isFlying()){
                this.setDeltaMovement(this.getDeltaMovement().add(0,-0.1,0));
            }
            if (isOnFire()){
                this.clearFire();
            }
            if(this.HungerDecreseTimer <= 30){
                if(isFlapping()){
                    this.HungerDecreseTimer = this.HungerDecreseTimer +4;
                } else if (isFlying()) {
                    this.HungerDecreseTimer = this.HungerDecreseTimer +3;
                } else if (isSprinting()) {
                    this.HungerDecreseTimer = this.HungerDecreseTimer +3;
                } else if (isMoving() && !isShiftKeyDown()) {
                    this.HungerDecreseTimer = this.HungerDecreseTimer +2;
                } else if (isMoving() && isShiftKeyDown() || isAlive()) {
                    this.HungerDecreseTimer = this.HungerDecreseTimer +1;
                }
            }else {
                this.HungerDecreseTimer = 0;
                this.setHunger(this.getHunger() -1);
            }
            updateHungerTimer();
            if (lastPassiveTrustIncrease > 0){
                lastPassiveTrustIncrease = lastPassiveTrustIncrease -1;
            }
            setSpeed((float) getMovementSpeed());
            AngerManager();
            if (lastPassiveTrustIncrease == 0){
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class,this.getHitbox().inflate(4), (livingEntity)->{
                        return FIND_ENTITY_PREDICATE.test((ServerLevel) this.level(), this, livingEntity);
                });
                if(!list.isEmpty()){
                    for (LivingEntity livingEntity : list) {
                        if (Trust.hasTrust(livingEntity.getUUID())) {
                            if (Trust.getTrust(livingEntity.getUUID()) >= 20 && Trust.getTrust(livingEntity.getUUID()) <= 10) {
                                if (tamingConditions(livingEntity)) {
                                    if (this.isLookingAtMe(livingEntity, 6, true, true, this.getEyeY())) {
                                        Trust.increaseTrust(livingEntity.getUUID(), 4D);
                                    } else {
                                        Trust.increaseTrust(livingEntity.getUUID(), 2D);
                                    }
                                }
                            }
                        }
                    }
                }
            }

            List<Entity> list = this.level().getEntitiesOfClass(Entity.class,this.getHitbox().inflate(2)).stream().toList();
            for (Entity entity : list){
                if(entity instanceof ItemEntity itemEntity){
                    if (this.distanceTo(itemEntity) <= 3){
                        if (canPickupItem(itemEntity)){
                            ItemStack stack = itemEntity.getItem();
                            Item item = stack.getItem();
                            if(canEat(item)){
                                if (canIncreseSaturation(this.getHealth() < this.getMaxHealth())){
                                    if(itemEntity.getOwner() != null){
                                        if(itemEntity.getOwner() instanceof Player player){
                                            if(handleFood(player,item,true)){
                                                stack.consume(1,this);
                                                ItemEntity itemEntity1 = new ItemEntity(this.level(),itemEntity.getX(),itemEntity.getY(),itemEntity.getZ(),stack);
                                                itemEntity1.setThrower(itemEntity.getOwner());
                                                itemEntity.remove(RemovalReason.DISCARDED);
                                                this.level().addFreshEntity(itemEntity1);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            this.updateSwinging();
            this.updateBlinking();
        }
    }

    @Override
    protected void tickRidden(Player player, Vec3 travelVector) {
        super.tickRidden(player, travelVector);
        if(level().isClientSide() && getControllingPassenger() instanceof LocalPlayer){
            LocalPlayer localPlayer = (LocalPlayer) player;
            boolean isForwardPressed = localPlayer.input.keyPresses.forward();
            boolean isSprintPressed = localPlayer.input.keyPresses.sprint();
            boolean isBackwardPressed = localPlayer.input.keyPresses.backward();
            boolean isJumpPressed = localPlayer.input.keyPresses.jump();

            this.setYRot(player.getYHeadRot());

            if(this.playerJumpPendingScale > 0.0F && !this.isJumping()){
                executeRidersJump(playerJumpPendingScale, travelVector, this.onGround() || isInLiquid());
            }

            ModNetworkHandler.sendC2SPacket(new DragonRiderInputPackage(isForwardPressed,isBackwardPressed,isSprintPressed,isJumpPressed));
        }
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(Player player, Vec3 travelVector) {
        return super.getRiddenInput(player, updateMovementInput(player, travelVector));
    }

    @Override
    protected void actuallyHurt(ServerLevel level, DamageSource damageSource, float amount) {
        if(damageSource.is(DamageTypes.IN_FIRE)){
            super.actuallyHurt(level,damageSource,0);
        } else if (damageSource.is(DamageTypes.ON_FIRE)) {
            super.actuallyHurt(level,damageSource,0);
        } else if (damageSource.is(DamageTypes.LAVA)|| damageSource.is(DamageTypes.EXPLOSION)) {
            super.actuallyHurt(level,damageSource,amount * 0.5F);
        }
        super.actuallyHurt(level, damageSource, amount);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        if(damageSource.is(DamageTypes.IN_FIRE)){
            return super.hurtServer(level,damageSource,0);
        } else if (damageSource.is(DamageTypes.ON_FIRE)) {
            return super.hurtServer(level,damageSource,0);
        } else if (damageSource.is(DamageTypes.LAVA)|| damageSource.is(DamageTypes.EXPLOSION)) {
            return super.hurtServer(level,damageSource,amount * 0.5F);
        }
        Entity entity= damageSource.getEntity();
        if (entity instanceof Mob || entity instanceof Player){
            boolean canAttack = !(entity == this) && !(entity == this.getOwner());
            if (entity instanceof Player player){
                if (player.isCreative() || player.isSpectator()){
                    canAttack = false;
                }else{
                    this.Trust.increaseTrust(player.getUUID(),-2.0);
                }
            }
            if(canAttack){
                if (this.getHealth() < this.getHealth() * 0.25) {
                    setTarget((LivingEntity) entity);
                }
            }
        }
        return super.hurtServer(level,damageSource,amount);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getMainHandItem();
        Item item = stack.getItem();
        if(!this.level().isClientSide()){
            if (canEat(item)){
                if(canIncreseSaturation(this.getHealth() < this.getMaxHealth())){
                    if( handleFood(player, item,false)) {
                        stack.consume(1, player);
                        return InteractionResult.SUCCESS;
                    }
                }else {
                    player.displayClientMessage(Component.translatable("text.not_enough_dragons.dragon.dragon.not_eat"), true);
                    return InteractionResult.PASS;
                }
            }
            if(player.isShiftKeyDown() &&  stack.isEmpty() && (Trust.getTrust(player.getUUID()) >= 100||player.isCreative())){
                if(!this.isTamed()){
                    this.Tame(player);
                    return InteractionResult.SUCCESS;
                } else {
                    if(player != this.getOwner()) {
                        player.displayClientMessage(Component.translatable("text.not_enough_dragons.dragon.dragon.not_tame_no_name"), true);
                        return InteractionResult.FAIL;
                    }else {
                    return InteractionResult.SUCCESS;
                }
                }
            } else if (stack.is(Items.STICK)) {
                player.displayClientMessage((Component.translatable("text.not_enough_dragons.dragon.get_trust").append(Component.literal(String.valueOf(Trust.getTrust(player.getUUID()))))), true);
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.SADDLE) && !this.hasSaddle()) {
                setSaddel(true);
                stack.consume(1,player);
                return InteractionResult.SUCCESS;
            } else {
                if((stack.isEmpty() && !stack.is(Items.STICK) && !isBaby())){
                    player.startRiding(this,true,true);
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void setOffspringAttributes(Dragon parent, Dragon child) {
        setOffspringAttribute(parent,child,Attributes.ATTACK_DAMAGE,5,8);
        setOffspringAttribute(parent,child,Attributes.ATTACK_KNOCKBACK,0.75,1.5);
        setOffspringAttribute(parent,child,Attributes.JUMP_STRENGTH,0.75,1.5);
        setOffspringAttribute(parent,child,Attributes.SNEAKING_SPEED,0.07,0.18);
        setOffspringAttribute(parent,child,Attributes.MOVEMENT_SPEED,0.22,0.5);
        setOffspringAttribute(parent,child,Attributes.FLYING_SPEED,0.85,1.5);
        setOffspringAttribute(parent,child,Holder.direct(ModAttributes.DRAGON_SPRINTING_SPEED.get()),0.58,0.8);
        setOffspringAttribute(parent,child,Holder.direct(ModAttributes.DRAGON_FLYING_SLOWDOWN_SPEED.get()),0.21,0.3);
        setOffspringAttribute(parent,child,Holder.direct(ModAttributes.DRAGON_FLYING_SPRINTING_SPEED.get()),1.85,3);
    }

    @Override
    public int getRemainingPersistentAngerTime() {
        return AngerTime;
    }

    @Override
    public void setRemainingPersistentAngerTime(int remainingPersistentAngerTime) {
        AngerTime = remainingPersistentAngerTime;
    }

    @Override
    public @Nullable UUID getPersistentAngerTarget() {
        return persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(@Nullable UUID persistentAngerTarget) {
        this.persistentAngerTarget = persistentAngerTarget;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setRemainingPersistentAngerTime(200);
    }

    @Override
    public void setInputs(boolean forward, boolean backward, boolean sprint, boolean jump){
        this.entityData.set(MOVE_FORWARD_PRESSED, forward);
        this.entityData.set(MOVE_BACKWARD_PRESSED, backward);
        this.entityData.set(SPRINT_PRESSED, sprint);
        this.entityData.set(JUMP_PRESSED, jump);
    }

    public double getMovementSpeed(){
        if(isSprinting()){
            return 0.6;
        }
        if(isShiftKeyDown()){
            return 0.1;
        }
        return getAttributeValue(Attributes.MOVEMENT_SPEED);
    }

    public Vec3 updateMovementInput(Player rider, Vec3 movementInput){
        zza = 0;
        if(inputForward()) zza = 1;
        entityData.set(SNEAKING,false);
        entityData.set(SPRINTING, false);
        if(inputSprint()) setSprinting(true);
        if(inputSlow()) setShiftKeyDown(true);
        if (!onGround()){
            if(isFlying()){
                setXRot(rider.getXRot());

                double flyUpSpeed;

                setSpeed(0.5F);
                if (inputForward()){
                    setSpeed((float) getAttributeValue(Attributes.FLYING_SPEED));
                    if(isSprinting()){
                        setSpeed((float) getAttributeValue(Holder.direct(ModAttributes.DRAGON_FLYING_SPRINTING_SPEED.get())));
                    }
                }
                if(isShiftKeyDown()){
                    setSpeed((float) getAttributeValue(Holder.direct(ModAttributes.DRAGON_FLYING_SLOWDOWN_SPEED.get())));
                }
                if (rider.isJumping()){
                    flyUpSpeed = 1;
                }else{
                    flyUpSpeed = -0.1;
                }
                float g = 0.0F;
                float h = 0.0F;
                if (zza != 0.0F) {
                    float i = Mth.cos(rider.getXRot() * ((float)Math.PI / 180F));
                    float j = -Mth.sin(rider.getXRot() * ((float)Math.PI / 180F));
                    if (zza < 0.0F) {
                        i *= -1F;
                        j *= -1F;
                    }

                    h = j;
                    g = i;
                }

                if (rider.isJumping()) {
                    h += 0.5F;
                }
                this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
                return (new Vec3(0, (double)h* getSpeed()/2+flyUpSpeed, (double)g* getSpeed()*5));
            } else if (isInLiquid()) {
                setSpeed((float) getAttributeValue(Attributes.MOVEMENT_SPEED));
                double h = 0;
                if (rider.isJumping()) {
                    h += 1F;
                }
                return new Vec3(0,h,getSpeed()*zza);
            } else{
                return new Vec3(0,movementInput.y, 1);
            }
        }
        else {
            double landSpeed = zza * getAttributeValue(Attributes.MOVEMENT_SPEED);
            if(isSprinting()){
                entityData.set(SPRINTING, true);
                setSpeed((float) getAttributeValue(Holder.direct(ModAttributes.DRAGON_SPRINTING_SPEED.get())));
            }else if(isShiftKeyDown()){
                entityData.set(SNEAKING,true);
                setSpeed((float) getAttributeValue(Attributes.SNEAKING_SPEED));
            }
            return new Vec3(0,movementInput.y, landSpeed);
        }
    }

    @Override
    protected int calculateFallDamage(double fallDistance, float damageMultiplier) {
        return 0;
    }

    @Override
    public Item Scales() {
        return ModBlocks.NIGHT_FURY_SCALES.get().asItem();
    }

    @Override
    public void on_brush_progress_change(int progress) {

    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    protected boolean isFlapping() {
        return isFlying() && inputForward();
    }

    public boolean canBeControledBy(Player player){
        if(player == null){
            return false;
        }
        if(Trust.getTrust(player.getUUID()) > 150) {
            return true;
        }
        return player.isCreative() || player.isSpectator();
    }

    @Override
    public boolean canPickupItem(ItemEntity itemEntity) {
        ItemStack itemStack = itemEntity.getItem();
        Item item = itemStack.getItem();
        if(canEat(item)){
            return true;
        }
        return super.canPickupItem(itemEntity);
    }

    static {
        TRUST_FILTER = (entity, level) -> {
            if (entity instanceof Player){
                return !((Player) entity).isCreative();
            } else {
                return true;
            }
        };
        AGGRO = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
        SNEAKING = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
        SPRINTING = SynchedEntityData.defineId(NightFury.class,EntityDataSerializers.BOOLEAN);
        BITING = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
        BLINKING = SynchedEntityData.defineId(NightFury.class,EntityDataSerializers.BOOLEAN);
        FIND_ENTITY_PREDICATE = TargetingConditions.forNonCombat().selector(TRUST_FILTER);

        MOVE_FORWARD_PRESSED = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
        SPRINT_PRESSED = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
        MOVE_BACKWARD_PRESSED = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
        JUMP_PRESSED = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);

        IS_FLYING = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
        CHARCHING_SHOT = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
        SADDEL = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
    }
}