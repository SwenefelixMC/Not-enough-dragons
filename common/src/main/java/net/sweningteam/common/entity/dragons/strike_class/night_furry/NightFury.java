package net.sweningteam.common.entity.dragons.strike_class.night_furry;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.Skeleton;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;

import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;

import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.dragon.food.DragonFoodComponentType;
import net.sweningteam.common.entity.dragons.base.Dragon;
import net.sweningteam.common.entity.dragons.base.interfaces.DragonWithScales;
import net.sweningteam.common.entity.dragons.base.interfaces.EatingDragon;
import net.sweningteam.common.entity.dragons.base.interfaces.FlyingDragon;
import net.sweningteam.common.entity.dragons.base.interfaces.TrustingDragon;
import net.sweningteam.common.entity.projectile.PlasmaBlast;
import net.sweningteam.common.network.ModNetworkHandler;
import net.sweningteam.common.network.packages.c2s.DragonRiderInputPackage;
import net.sweningteam.common.registry.ModBlocks;
import net.sweningteam.common.registry.ModDragonFoodComponentTypes;
import net.sweningteam.common.registry.ModEntityTypes;
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

import java.util.List;
import java.util.Random;
import java.util.UUID;

public class NightFury extends Dragon implements NeutralMob, OwnableEntity, EatingDragon, PlayerRideableJumping , DragonWithScales, FlyingDragon {
    Random random = new Random();

    private static final EntityDataAccessor<Boolean> AGGRO;
    private static final EntityDataAccessor<Boolean> SNEAKING;
    private static final EntityDataAccessor<Boolean> SPRINTING;
    private static final EntityDataAccessor<Boolean> TAMED;
    private static final EntityDataAccessor<Boolean> BITING;
    private static final EntityDataAccessor<Boolean> BLINKING;
    private static final EntityDataAccessor<Boolean> IS_FLYING;
    private static final TargetingConditions.Selector TRUST_FILTER;

    private static final EntityDataAccessor<Boolean> MOVE_FORWARD_PRESSED;
    private static final EntityDataAccessor<Boolean> SPRINT_PRESSED;
    private static final EntityDataAccessor<Boolean> MOVE_BACKWARD_PRESSED;
    private static final EntityDataAccessor<Boolean> JUMP_PRESSED;

    static final TargetingConditions FIND_ENTITY_PREDICATE;

    boolean inputForward(){return this.entityData.get(MOVE_FORWARD_PRESSED);};
    boolean inputSlow(){return this.entityData.get(MOVE_BACKWARD_PRESSED);};
    boolean inputSprint(){return this.entityData.get(SPRINT_PRESSED);};
    boolean inputJump(){return this.entityData.get(JUMP_PRESSED);};

    private int lastPassiveTrustIncrease = 0;

    int hunger = MaxHunger();

    @Override
    public Boolean isFlying() {
        return entityData.get(IS_FLYING);
    }

    @Override
    public void setFlying(Boolean bl) {
        entityData.set(IS_FLYING,bl);
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
        return 15;
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

    boolean isTamed = false;
    private EntityReference<LivingEntity> owner;

    private int AngerTime = 0;
    private UUID persistentAngerTarget;

    int HungerDecreseTimer = 200;

    boolean oldSwinging = false;
    int swingTime = 0;

    int blinkTimer = 200;
    int blinkProgress = 0;

    protected float playerJumpPendingScale;
    protected boolean allowStandSliding;



    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(AGGRO, false);
        builder.define(SNEAKING, false);
        builder.define(SPRINTING,false);
        builder.define(TAMED, false);
        builder.define(BITING, false);
        builder.define(BLINKING,false);

        builder.define(MOVE_FORWARD_PRESSED,false);
        builder.define(SPRINT_PRESSED, false);
        builder.define(MOVE_BACKWARD_PRESSED, false);
        builder.define(JUMP_PRESSED,false);

        builder.define(IS_FLYING,false);
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
                        if (entityData.get(TAMED)) {
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
                event.setAndContinue(RawAnimation.begin().thenPlay("animation.nightfury.Fly"));
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

        this.goalSelector.addGoal(1,new MeleeAttackGoal(this,1D,false));

        this.goalSelector.addGoal(2,new WaterAvoidingRandomStrollGoal(this,1.0));

        this.goalSelector.addGoal(4,new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(0,new NearestAttackableTargetGoal<>(this, LivingEntity.class, 10,true, false, this::isAngryAt));

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Skeleton.class, true, false));
        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Zombie.class, true, false));

    }

    public static AttributeSupplier.Builder createAtributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH,140)
                .add(Attributes.MOVEMENT_SPEED,0.38)
                .add(Attributes.FLYING_SPEED,6)
                .add(Attributes.STEP_HEIGHT,1.1)
                .add(Attributes.ARMOR,1)
                .add(Attributes.ATTACK_DAMAGE,6.5)
                .add(Attributes.ATTACK_KNOCKBACK,1.25)
                .add(Attributes.FOLLOW_RANGE,20)
                .add(Attributes.JUMP_STRENGTH,0.8);
    }

    public NightFury(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);

    }

    @Override
    public boolean isFlyingVehicle() {
        return true;
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        addTrustSaveData(output);
        output.putBoolean("tamed", isTamed());
        addPersistentAngerSaveData(output);
        EntityReference.store(this.owner, output, "Owner");
        addHungerSaveData(output);
        output.putInt("HungryDecreaseTime", HungerDecreseTimer);
        output.putInt("BlinkTime", blinkTimer);
        addFlyingData(output,this);
        super.addAdditionalSaveData(output);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        readTrustSaveData(input);
        setTamed(input.getBooleanOr("tamed",false));
        readPersistentAngerSaveData(this.level(),input);
        this.owner = EntityReference.readWithOldOwnerConversion(input, "Owner", this.level());
        readHungerSaveData(input);
        HungerDecreseTimer = input.getIntOr("HungryDecreaseTime",200);
        blinkTimer = input.getIntOr("BlinkTime",200);
        readFlyingData(input,this);
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

    protected void executeRidersJump(float playerJumpPendingScale, Vec3 travelVector) {
        double jumpingPower = this.getJumpPower(playerJumpPendingScale);
        Vec3 deltaMovement = this.getDeltaMovement();
        this.addDeltaMovement(this.getLookAngle().multiply((double)1.0F, (double)0.0F, (double)1.0F).normalize().scale((double)(15 * playerJumpPendingScale * deltaMovement.horizontalDistance()) * this.getAttributeValue(Attributes.MOVEMENT_SPEED) * (double)this.getBlockSpeedFactor()).add((double)0.0F, (double)(0.1 + 1.1 * playerJumpPendingScale) * jumpingPower, (double)0.0F));
    }

    private boolean isTamed(){
        return isTamed;
    }

    private void setTamed(boolean tamed){
        this.isTamed = tamed;
    }

    private void Tame(Player player){
        this.setOwner(player);
        this.setTamed(true);

        if (player instanceof ServerPlayer) {
            CriteriaTriggers.TAME_ANIMAL.trigger((ServerPlayer)player, this);
        }

        this.level().broadcastEntityEvent(this, (byte)7);
    }

    private void setOwner(@Nullable LivingEntity owner){
        this.owner = EntityReference.of(owner);
    }

    @Override
    public boolean handleFood(Player player, Item item) {
        boolean bl = false;
        if(dragonFoodComponentType().contains(item)){
            trust.increaseTrust(player.getUUID(),dragonFoodComponentType().get(item).getTrust_gained());
            dragonFoodComponentType().get(item).onEat(this);
            startHungerUpdate(this.dragonFoodComponentType().get(item).getFood_gained());
            SetEatingProgress(35);
            bl = true;
        }
        return bl;
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
        List<LivingEntity> list = this.level().getEntitiesOfClass(LivingEntity.class,this.getHitbox().inflate(30)).stream().toList();
        for ( int i = 1; i < list.size(); i++){
            LivingEntity entity = list.get(i);
            if(entity instanceof Player && !((Player) entity).isCreative()) {
                if (TrustingDragon.trust.getTrust(entity.getUUID()) < 100 || trust.getTrust(entity.getUUID()) == null) {
                    Should_be_sneaking = true;
                }
                if (entity.getUUID().equals(getPersistentAngerTarget()) || (trust.getTrust(entity.getUUID()) < 50)) {
                    if (!entity.isShiftKeyDown()) {
                        setTarget(entity);
                        Should_be_aggressive = true;
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
    }

    public void SmallBox(){
        boolean Should_be_aggro = false;
        List<LivingEntity> list1 = this.level().getEntitiesOfClass(LivingEntity.class,this.getHitbox().inflate(9)).stream().toList();
        for(int i = 1; i<list1.size(); i++){
            LivingEntity entity = list1.get(i);
            if(entity instanceof Player && !((Player) entity).isCreative()) {
                if (trust.getTrust(entity.getUUID()) <= 50) {
                    Should_be_aggro = true;
                }
                if(trust.getTrust(entity.getUUID())< 50) {
                    setTarget(entity);
                }else if(trust.getTrust(entity.getUUID()) < 100){
                    if(!entity.isShiftKeyDown()) {
                        setTarget(entity);
                    }
                }
            }
        }
        if(Should_be_aggro){
            entityData.set(AGGRO, true);
        }else {
            entityData.set(AGGRO, false);
        }
    }

    public void AngerManager(){
    this.BigBox();
    this.SmallBox();
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
            updtateFlying(this);
            if (isFlying()){
                this.setDeltaMovement(this.getDeltaMovement().add(0,-0.1,0));
            }
            if (isOnFire()){
                this.clearFire();
            }
            if(this.HungerDecreseTimer <= 200){
                if(isFlapping()){
                    this.HungerDecreseTimer = this.HungerDecreseTimer +3;
                } else if (isFallFlying()) {
                    this.HungerDecreseTimer = this.HungerDecreseTimer +2;
                } else if (isSprinting()) {
                    this.HungerDecreseTimer = this.HungerDecreseTimer +2;
                } else if (isMoving() && !isShiftKeyDown()) {
                    this.HungerDecreseTimer = this.HungerDecreseTimer +1;
                }
            }else {
                this.HungerDecreseTimer = 0;
                this.setHunger(this.getHunger() -1);
            }
            entityData.set(TAMED,this.isTamed());
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
                            if (trust.hasTrust(livingEntity.getUUID())) {
                                if (trust.getTrust(livingEntity.getUUID()) >= 20 && trust.getTrust(livingEntity.getUUID()) <= 10) {
                                    if (tamingConditions(livingEntity)) {
                                        if (this.isLookingAtMe(livingEntity, 6, true, true, this.getEyeY())) {
                                            trust.increaseTrust(livingEntity.getUUID(), 4D);
                                        } else {
                                            trust.increaseTrust(livingEntity.getUUID(), 2D);
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
            if(this.onGround()){
                if(this.playerJumpPendingScale > 0.0F && !this.isJumping()){
                    executeRidersJump(playerJumpPendingScale, travelVector);
                }
                this.playerJumpPendingScale = 0.0F;
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
            if (trust.getTrust(entity.getUUID()) < 100) {
                trust.increaseTrust(entity.getUUID(), (double) amount);
                setTarget((LivingEntity) entity);
            } else {
                trust.increaseTrust(entity.getUUID(), amount * -0.5);
                if (this.getHealth() < this.getHealth() * 0.25) {
                    setTarget((LivingEntity) entity);
                }
            }
            if (entity != this){
                performRangedAttack((LivingEntity) entity,2.5F);
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
                    if( handleFood(player, item)) {
                        stack.consume(1, player);
                        return InteractionResult.SUCCESS;
                    }}else {
                        player.displayClientMessage(Component.translatable("text.not_enough_dragons.dragon.dragon.not_eat"), true);
                        return InteractionResult.PASS;
                    }}
            if(player.isShiftKeyDown() && ( stack.isEmpty()) && TrustingDragon.trust.getTrust(player.getUUID()) >= 100){
                if(!this.isTamed()){
                    this.Tame(player);
                    return InteractionResult.SUCCESS;
                } else {
                      player.displayClientMessage(Component.translatable("text.not_enough_dragons.dragon.dragon.not_tame_no_name"), true);
                    return InteractionResult.FAIL;
                }
            } else if (stack.is(Items.STICK)) {
                player.displayClientMessage((Component.translatable("text.not_enough_dragons.dragon.get_trust").append(Component.literal(String.valueOf(trust.getTrust(player.getUUID()))))), true);
                return InteractionResult.SUCCESS;
            }else {
                if((stack.isEmpty() && !stack.is(Items.STICK))){
                    player.startRiding(this,true,true);
                }
            }
        }
        return super.mobInteract(player, hand);
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
    public @Nullable EntityReference<LivingEntity> getOwnerReference() {
        return this.owner;
    }

    @Override
    public void setInputs(boolean forward, boolean backward, boolean sprint, boolean jump){
        this.entityData.set(MOVE_FORWARD_PRESSED, forward);
        this.entityData.set(MOVE_BACKWARD_PRESSED, backward);
        this.entityData.set(SPRINT_PRESSED, sprint);
        this.entityData.set(JUMP_PRESSED, jump);
         if (!this.onGround()&&entityData.get(JUMP_PRESSED)){
            setNoGravity(true);
            setFlying(true);
        }
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
        if (!onGround()){
            float g = 0.0F;
            float h = 0.0F;
            if (rider.zza != 0.0F) {
                float i = Mth.cos(rider.getXRot() * ((float)Math.PI / 180F));
                float j = -Mth.sin(rider.getXRot() * ((float)Math.PI / 180F));
                if (rider.zza < 0.0F) {
                    i *= -1F;
                    j *= -1F;
                }

                h = j;
                g = i;
            }

            if (rider.isJumping()) {
                h += 0.5F;
            }
            setRot(rider.getYHeadRot(),rider.getXRot()*0.5F);
            this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();

            return (new Vec3(0, (double)h* this.getAttributeValue(Attributes.FLYING_SPEED)*5, (double)g* this.getAttributeValue(Attributes.FLYING_SPEED)*5));
        } else {
            setFlying(false);
            setNoGravity(false);
            entityData.set(SNEAKING,false);
            entityData.set(SPRINTING, false);
            zza = 0;
            float y = 0.0F;
            if(inputForward()) zza = 1;
            if(inputSprint()) setSprinting(true);
            if(inputSlow()) setShiftKeyDown(true);
            double landSpeed = zza * getAttributeValue(Attributes.MOVEMENT_SPEED);
            if(isSprinting()){
                entityData.set(SPRINTING, true);
                setSpeed(0.7F);
            }else if(isShiftKeyDown()){
                entityData.set(SNEAKING,true);
                setSpeed(0.1F);
            }
            setRot(rider.getYHeadRot(),rider.getXRot()*0.5F);
            this.yRotO = this.yBodyRot = this.yHeadRot = this.getYRot();
            return new Vec3(0,movementInput.y - 0.001, landSpeed);
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

    public void performRangedAttack(LivingEntity target, float velocity) {
        ItemStack itemStack = this.getItemInHand(ProjectileUtil.getWeaponHoldingHand(this, Items.BOW));
        ItemStack itemStack2 = this.getProjectile(itemStack);

        double x = target.getX() - this.getX();
        double y = target.getY() - this.getEyeY();
        double z = target.getZ() - this.getZ();
        Level level = this.level();
        Vec3 vec32 = new Vec3(x, y, z);
        PlasmaBlast plasmaBlast = new PlasmaBlast(ModEntityTypes.PLASMA_BLAST.get(),this,vec32.normalize(),this.level());
        plasmaBlast.setPos(new Vec3(getX(),getEyeY(),getZ()));
        plasmaBlast.setYRot(this.getYHeadRot());
        if (level instanceof ServerLevel serverLevel) {
            level().addFreshEntity(plasmaBlast);
        }

        this.playSound(SoundEvents.SKELETON_SHOOT, 1.0F, 1.0F / (this.getRandom().nextFloat() * 0.4F + 0.8F));
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
        TAMED = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
        BITING = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
        BLINKING = SynchedEntityData.defineId(NightFury.class,EntityDataSerializers.BOOLEAN);
        FIND_ENTITY_PREDICATE = TargetingConditions.forNonCombat().selector(TRUST_FILTER);

        MOVE_FORWARD_PRESSED = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
        SPRINT_PRESSED = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
        MOVE_BACKWARD_PRESSED = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
        JUMP_PRESSED = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);

        IS_FLYING = SynchedEntityData.defineId(NightFury.class, EntityDataSerializers.BOOLEAN);
    }
}