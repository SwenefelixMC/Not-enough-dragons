package net.sweningteam.not_enough_dragons.common.entity.dragons.strike_class.night_furry;

import com.geckolib.animatable.GeoAnimatable;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.animation.state.AnimationTest;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
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

import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponentType;
import net.sweningteam.not_enough_dragons.common.dragon.trust.TrustContainer;
import net.sweningteam.not_enough_dragons.common.entity.ai.DragonFolowOwnerGoal;
import net.sweningteam.not_enough_dragons.common.entity.ai.ShootProjectileGoal;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.TamableDragon;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.interfaces.*;
import net.sweningteam.not_enough_dragons.common.entity.egg.NightFuryEgg;
import net.sweningteam.not_enough_dragons.common.entity.egg.base.DragonEggData;
import net.sweningteam.not_enough_dragons.common.entity.projectile.PlasmaBlast;
import net.sweningteam.not_enough_dragons.common.entity.projectile.base.BlastProjectile;
import net.sweningteam.not_enough_dragons.common.network.ModNetworkHandler;
import net.sweningteam.not_enough_dragons.common.network.packages.c2s.DragonFlyingUpdatePackage;
import net.sweningteam.not_enough_dragons.common.network.packages.c2s.DragonRiderInputPackage;
import net.sweningteam.not_enough_dragons.common.registry.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.util.*;

public class NightFury extends TamableDragon implements NeutralMob, EatingDragon, PlayerRideableJumping , DragonWithScales, FlyingDragon, ShootingDragon, SaddleAbblDragon, GenderedDragon {
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

    int shotsLeft;

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

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);

    private Long AngerTime = 0L;
    private EntityReference<LivingEntity> persistentAngerTarget;

    int HungerDecreaseTimer = 200;

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

    genders Gender;
    @Override
    public boolean isMale() {
        return Gender.equals(genders.MALE);
    }

    @Override
    public void SetGender(genders gender) {
        Gender = gender;
    }

    @Override
    public genders getGender() {
        return Gender;
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
        event.controller().setTransitionTicks(3);
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
        event.controller().setTransitionTicks(5);
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

        this.goalSelector.addGoal(1,new AvoidEntityGoal<>(this,Ravager.class,15F,1F,2F));

        this.goalSelector.addGoal(2, new ShootProjectileGoal(this));

        this.goalSelector.addGoal(3,new MeleeAttackGoal(this,1D,false));

        this.goalSelector.addGoal(4, new BreedGoal(this,1));
        this.goalSelector.addGoal(5, new DragonFolowOwnerGoal(this,1.0D, 16.0F, 4.0F));
        this.goalSelector.addGoal(6,new WaterAvoidingRandomStrollGoal(this,1.0));

        this.goalSelector.addGoal(7,new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1,new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10,true, false, this::isAngryAt));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Raider.class, 5, true, false,this::canAttackPillager));
    }

    public static AttributeSupplier.Builder createAttributes() {
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
                .add(ModAttributes.DRAGON_SPRINTING_SPEED,0.7D)
                .add(ModAttributes.DRAGON_FLYING_SLOWDOWN_SPEED,0.25)
                .add(ModAttributes.DRAGON_FLYING_SPRINTING_SPEED,2.2)
                .add(ModAttributes.DRAGON_TAMING_TRUST,100)
                .add(ModAttributes.DRAGON_SHOT_LIMIT,6);
    }

    public NightFury(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean canMate(@NonNull Animal otherAnimal) {
        if(otherAnimal instanceof Dragon dragon){
            if(dragon instanceof GenderedDragon genderedDragon){
                if(!this.getGender().equals(genderedDragon.getGender())){
                    return super.canMate(otherAnimal);
                }else {
                    return false;
                }
            }
        }
        return super.canMate(otherAnimal);
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

    int BlastRestorTimer;

    @Override
    public int BlastsLeft() {
        return shotsLeft;
    }

    @Override
    public void SetBlastsLeft(int BlastsLeft) {
        this.shotsLeft = BlastsLeft;
    }

    @Override
    public int getBlastRestorTimer() {
        return BlastRestorTimer;
    }

    @Override
    public void setBlastRestorTimer(int blastRestorTimer) {
        BlastRestorTimer = blastRestorTimer;
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
        output.putInt("HungryDecreaseTime", HungerDecreaseTimer);
        output.putInt("BlinkTime", blinkTimer);
        addFlyingData(output,this);
        output.putInt("DangerTimer", dangerTimer);
        writeSaddleData(output);
        output.putBoolean("IsMale",isMale());
        output.putInt("BlastsLeft", BlastsLeft());
        output.putInt("BlastRestoreTimer",BlastRestorTimer);
        super.addAdditionalSaveData(output);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        Trust.clear();
        Optional<TrustContainer> trustOptional = input.read("Trust",TrustContainer.CODEC);
        trustOptional.ifPresent(trustContainer -> Trust = trustContainer);
        readPersistentAngerSaveData(this.level(),input);
        readHungerSaveData(input);
        HungerDecreaseTimer = input.getIntOr("HungryDecreaseTime",200);
        blinkTimer = input.getIntOr("BlinkTime",200);
        readFlyingData(input,this);
        dangerTimer = input.getIntOr("DangerTimer",0);
        readSaddleData(input);
        if(input.getBooleanOr("IsMale",(this.random.nextInt(100)<55))){
            SetGender(genders.MALE);
        }else{
            SetGender(genders.FEMALE);
        }
        SetBlastsLeft(input.getIntOr("BlastsLeft", (int) this.getAttributeValue(ModAttributes.DRAGON_SHOT_LIMIT)));
        setBlastRestorTimer(input.getIntOr("BlastRestoreTimer",1000));
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
    public @NonNull AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.cache;
    }
    @Override
    public void aiStep() {
        super.aiStep();
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
        if(this.isSwimming() && !this.oldSwinging){
            this.swingTime = 12;
        }
        this.oldSwinging = this.isSwinging();
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
                    if (Trust.getTrust(livingEntity.getUUID()) < getAttributeValue(ModAttributes.DRAGON_TAMING_TRUST)*0.5){
                        if(this.distanceTo(livingEntity) <= 12 ){
                            if(entityIsDanger(livingEntity) && entityDoesNotHaveFoodItem(livingEntity)){
                                if(!livingEntity.isShiftKeyDown()){
                                    Should_be_aggro = true;
                                    if(dangerTimer <= 100 && getTarget() == null){
                                        dangerTimer += 1;
                                    }else {
                                        dangerTimer = 0;
                                        setTarget(livingEntity);
                                        Should_be_aggressive = true;
                                    }
                                }
                            }
                        }
                        if(this.distanceTo(livingEntity) <= 6 && entityDoesNotHaveFoodItem(livingEntity)){
                            if(!livingEntity.isShiftKeyDown()){
                                if(dangerTimer <= 100 && getTarget() == null){
                                    dangerTimer += 1;
                                }else {
                                    dangerTimer = 0;
                                    setTarget(livingEntity);
                                    Should_be_aggressive = true;
                                }
                            }
                        }
                    }else if(Trust.getTrust(livingEntity.getUUID())<getAttributeValue(ModAttributes.DRAGON_TAMING_TRUST)){
                        if(this.distanceTo(livingEntity) <= 6 ){
                            if(!livingEntity.isShiftKeyDown()){
                                Should_be_aggro = true;
                                if(entityDoesNotHaveFoodItem(livingEntity)){
                                    if(dangerTimer <= 100 && getTarget() == null){
                                        dangerTimer += 1;
                                    }else {
                                        dangerTimer = 0;
                                        setTarget(livingEntity);
                                        Should_be_aggressive = true;
                                    }
                                }
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
            setPersistentAngerEndTime(200);
        }
        entityData.set(AGGRO, Should_be_aggro);
    }

    public boolean entityDoesNotHaveFoodItem(LivingEntity entity){
        ItemStack MainStack = entity.getItemInHand(InteractionHand.MAIN_HAND);
        ItemStack OffStack = entity.getItemInHand(InteractionHand.OFF_HAND);
        return !canEat(MainStack.getItem()) || !canEat(OffStack.getItem());
    }

    public void AngerManager(){
    this.BigBox();
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(@NonNull ServerLevel level, @NonNull AgeableMob otherParent) {
        return new NightFury(ModEntityTypes.NIGHT_FURY.get(),level);
    }

    @Override
    public void spawnChildFromBreeding(@NonNull ServerLevel level, @NonNull Animal mate) {
        NightFuryEgg egg = new NightFuryEgg(ModEntityTypes.NIGHT_FURY_EGG.get(), level);
        NightFury nightFury = (NightFury) getBreedOffspring((ServerLevel) this.level(),mate);

        egg.setPos(this.position());
        setOffspringAttributes((Dragon) mate,nightFury);
        if(nightFury != null){
            egg.setDragonEgg(false,0,0,new DragonEggData(
                    nightFury.getAttributeValue(Attributes.ATTACK_DAMAGE),
                    nightFury.getAttributeValue(Attributes.ATTACK_KNOCKBACK),
                    nightFury.getAttributeValue(Attributes.JUMP_STRENGTH),
                    nightFury.getAttributeValue(Attributes.SNEAKING_SPEED),
                    nightFury.getAttributeValue(Attributes.MOVEMENT_SPEED),
                    nightFury.getAttributeValue(Attributes.FLYING_SPEED),
                    nightFury.getAttributeValue(ModAttributes.DRAGON_SPRINTING_SPEED),
                    nightFury.getAttributeValue(ModAttributes.DRAGON_FLYING_SPRINTING_SPEED),
                    nightFury.getAttributeValue(ModAttributes.DRAGON_SPRINTING_SPEED),
                    nightFury.getAttributeValue(ModAttributes.DRAGON_TAMING_TRUST)
            ));
        }
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
            AngerManager();
        }
        super.tick();
        if(!this.level().isClientSide()){
            if(this.getPassengers().isEmpty()){
                setSpeed((float) this.getAttributeValue(Attributes.MOVEMENT_SPEED));
            }

            scalesTick(this);
            updateBlastRestoreTimer(this);
            this.setDeltaMovement(this.getDeltaMovement().x,this.getDeltaMovement().y-0.01,this.getDeltaMovement().z);
            updtateFlying(this);
            if (isFlying()){
                this.setDeltaMovement(this.getDeltaMovement().add(0,-0.1,0));
            }
            if (isOnFire()){
                this.clearFire();
            }
            if(this.HungerDecreaseTimer <= 30){
                if(isFlapping()){
                    this.HungerDecreaseTimer = this.HungerDecreaseTimer +4;
                } else if (isFlying()) {
                    this.HungerDecreaseTimer = this.HungerDecreaseTimer +3;
                } else if (isSprinting()) {
                    this.HungerDecreaseTimer = this.HungerDecreaseTimer +3;
                } else if (isMoving() && !isShiftKeyDown()) {
                    this.HungerDecreaseTimer = this.HungerDecreaseTimer +2;
                } else if (isMoving() && isShiftKeyDown() || isAlive()) {
                    this.HungerDecreaseTimer = this.HungerDecreaseTimer +1;
                }
            }else {
                this.HungerDecreaseTimer = 0;
                this.setHunger(this.getHunger() -1);
            }
            updateHungerTimer();
            if (lastPassiveTrustIncrease > 0){
                lastPassiveTrustIncrease = lastPassiveTrustIncrease -1;
            }
            setSpeed((float) getMovementSpeed());
            if (lastPassiveTrustIncrease == 0){
                List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class,this.getHitbox().inflate(4), (livingEntity)->{
                        return FIND_ENTITY_PREDICATE.test((ServerLevel) this.level(), this, livingEntity);
                });
                if(!list.isEmpty()){
                    for (LivingEntity livingEntity : list) {
                        if (Trust.hasTrust(livingEntity.getUUID())) {
                            if (Trust.getTrust(livingEntity.getUUID()) >= getAttributeValue(ModAttributes.DRAGON_TAMING_TRUST)*0.2 && Trust.getTrust(livingEntity.getUUID()) <= getAttributeValue(ModAttributes.DRAGON_TAMING_TRUST)*0.1) {
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
    protected void tickRidden(@NonNull Player player, @NonNull Vec3 travelVector) {
        super.tickRidden(player, travelVector);
        if(level().isClientSide() && getControllingPassenger() instanceof LocalPlayer){
            LocalPlayer localPlayer = (LocalPlayer) player;
            boolean isForwardPressed = localPlayer.input.keyPresses.forward();
            boolean isSprintPressed = localPlayer.input.keyPresses.sprint();
            boolean isBackwardPressed = localPlayer.input.keyPresses.backward();
            boolean isJumpPressed = localPlayer.input.keyPresses.jump();
            this.setYHeadRot(player.getYHeadRot());
            if(isForwardPressed){
                this.setYRot(player.getYHeadRot());
            }


            if(this.playerJumpPendingScale > 0.0F && !this.isJumping()){
                executeRidersJump(playerJumpPendingScale, travelVector, this.onGround() || isInLiquid());
            }

            ModNetworkHandler.sendC2SPacket(new DragonRiderInputPackage(isForwardPressed,isBackwardPressed,isSprintPressed,isJumpPressed));
        }
    }

    @Override
    protected @NotNull Vec3 getRiddenInput(@NonNull Player player, @NonNull Vec3 travelVector) {
        return super.getRiddenInput(player, updateMovementInput(player, travelVector));
    }

    @Override
    protected void actuallyHurt(@NonNull ServerLevel level, DamageSource damageSource, float amount) {
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
    public boolean hurtServer(@NonNull ServerLevel level, DamageSource damageSource, float amount) {
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
    public boolean canAttack(@NonNull LivingEntity target) {
        return !this.hasEffect(ModEffects.DRAGON_NIP) && super.canAttack(target);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, @NonNull InteractionHand hand) {
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
                    player.sendOverlayMessage(Component.translatable("text.not_enough_dragons.dragon.dragon.not_eat"));
                    return InteractionResult.PASS;
                }
            }
            if(player.isShiftKeyDown() &&  stack.isEmpty() && (Trust.getTrust(player.getUUID()) >= getAttributeValue(ModAttributes.DRAGON_TAMING_TRUST)||player.isCreative())){
                if(!this.isTamed()){
                    this.Tame(player);
                    return InteractionResult.SUCCESS;
                } else {
                    if(player != this.getOwner()) {
                        player.sendOverlayMessage(Component.translatable("text.not_enough_dragons.dragon.dragon.not_tame_no_name"));
                        return InteractionResult.FAIL;
                    }else {
                    return InteractionResult.SUCCESS;
                }
                }
            } else if (stack.is(Items.STICK)) {
                player.sendOverlayMessage((Component.translatable("text.not_enough_dragons.dragon.get_trust").append(Component.literal(String.valueOf(Trust.getTrust(player.getUUID()))))));
                return InteractionResult.SUCCESS;
            } else if (stack.is(Items.SADDLE) && !this.hasSaddle()) {
                setSaddel(true);
                stack.consume(1,player);
                return InteractionResult.SUCCESS;
            } else if (stack.is(ModItems.DRAGON_NIP.get())){
                this.addEffect(new MobEffectInstance(ModEffects.DRAGON_NIP,1920));
                stack.consume(1,player);
                return InteractionResult.SUCCESS;
            }else {
                if((stack.isEmpty() && !stack.is(Items.STICK) && !isBaby()) && getPassengers().isEmpty()){
                    player.startRiding(this,true,true);
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public long getPersistentAngerEndTime() {
        return AngerTime;
    }

    @Override
    public void setPersistentAngerEndTime(long remainingPersistentAngerTime) {
        AngerTime = remainingPersistentAngerTime;
    }

    @Override
    public @Nullable EntityReference<LivingEntity> getPersistentAngerTarget() {
        return persistentAngerTarget;
    }

    @Override
    public void setPersistentAngerTarget(final @org.jspecify.annotations.Nullable EntityReference<LivingEntity> persistentAngerTarget) {
        this.persistentAngerTarget = persistentAngerTarget;
    }

    @Override
    public void startPersistentAngerTimer() {
        this.setPersistentAngerEndTime(200);
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
        setXRot(rider.getXRot());
        if (!onGround()){
            if(isFlying()){
                double flyUpSpeed;
                setSpeed(0.5F);
                if (inputForward()){
                    setSpeed((float) getAttributeValue(Attributes.FLYING_SPEED));
                    if(isSprinting()){
                        setSpeed((float) getAttributeValue(ModAttributes.DRAGON_FLYING_SPRINTING_SPEED));
                    }
                }
                if(isShiftKeyDown()){
                    setSpeed((float) getAttributeValue(ModAttributes.DRAGON_FLYING_SLOWDOWN_SPEED));
                    flyUpSpeed = -0.4;
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
                if(inputForward()){
                    this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
                }

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
                setSpeed((float) getAttributeValue(ModAttributes.DRAGON_SPRINTING_SPEED));
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
    public Block Scales() {
        return ModBlocks.NIGHT_FURY_SCALES.get();
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
        if(Trust.getTrust(player.getUUID()) > getAttributeValue(ModAttributes.DRAGON_TAMING_TRUST)*1.5) {
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

    @Override
    public void setOffspringAttributes(Dragon parent, Dragon child) {
        setOffspringAttribute(parent,child,Attributes.ATTACK_DAMAGE,5,8);
        setOffspringAttribute(parent,child,Attributes.ATTACK_KNOCKBACK,0.75,1.5);
        setOffspringAttribute(parent,child,Attributes.JUMP_STRENGTH,0.75,1.5);
        setOffspringAttribute(parent,child,Attributes.SNEAKING_SPEED,0.07,0.18);
        setOffspringAttribute(parent,child,Attributes.MOVEMENT_SPEED,0.25,0.5);
        setOffspringAttribute(parent,child,Attributes.FLYING_SPEED,0.85,1.5);
        setOffspringAttribute(parent,child,ModAttributes.DRAGON_SPRINTING_SPEED,0.65,0.85);
        setOffspringAttribute(parent,child,ModAttributes.DRAGON_FLYING_SLOWDOWN_SPEED,0.21,0.3);
        setOffspringAttribute(parent,child,ModAttributes.DRAGON_FLYING_SPRINTING_SPEED,1.85,3);
        setOffspringAttribute(parent,child,ModAttributes.DRAGON_TAMING_TRUST,75,130);
    }

    @Override
    public void randomizeAttributes(RandomSource random) {
        super.randomizeAttributes(random);
        setOwnAttributes(Attributes.ATTACK_DAMAGE,5.5,7);
        setOwnAttributes(Attributes.ATTACK_KNOCKBACK,0.9,1.3);
        setOwnAttributes(Attributes.JUMP_STRENGTH,0.85,1);
        setOwnAttributes(Attributes.SNEAKING_SPEED,0.08,0.13);
        setOwnAttributes(Attributes.MOVEMENT_SPEED,0.3,0.4);
        setOwnAttributes(Attributes.FLYING_SPEED,0.9,1.25);
        setOwnAttributes(ModAttributes.DRAGON_SPRINTING_SPEED,0.7,0.8);
        setOwnAttributes(ModAttributes.DRAGON_FLYING_SLOWDOWN_SPEED,0.23,0.27);
        setOwnAttributes(ModAttributes.DRAGON_FLYING_SPRINTING_SPEED,2,2.5);
        setOwnAttributes(ModAttributes.DRAGON_TAMING_TRUST,80,120);
    }

    @Override
    public @Nullable SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty, EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnGroupData) {
        if(this.random.nextInt(100)<55){
            SetGender(genders.MALE);
        }else {
            SetGender(genders.FEMALE);
        }
        if(spawnReason == EntitySpawnReason.STRUCTURE){
            List<NightFuryEgg> eggs = this.level().getEntitiesOfClass(NightFuryEgg.class,this.getHitbox().inflate(2));

            for (NightFuryEgg egg : eggs){
                if(egg.getParentF().isEmpty()){
                    egg.setParentF(EntityReference.of(this));
                    SetGender(genders.FEMALE);
                }
            }
        }
        return super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
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