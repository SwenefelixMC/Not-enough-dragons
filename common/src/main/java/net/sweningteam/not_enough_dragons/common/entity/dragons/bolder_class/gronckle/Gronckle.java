package net.sweningteam.not_enough_dragons.common.entity.dragons.bolder_class.gronckle;

import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.util.GeckoLibUtil;
import com.mojang.serialization.Codec;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Ravager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.interfaces.DragonWithScales;
import net.sweningteam.not_enough_dragons.common.entity.egg.GronckleEgg;
import net.sweningteam.not_enough_dragons.common.recipie.Gronckle.GroncklRecipie;
import net.sweningteam.not_enough_dragons.common.recipie.Gronckle.GronckleRecipieInput;
import net.sweningteam.not_enough_dragons.common.registry.*;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class Gronckle extends Dragon implements DragonWithScales {

    private List<ItemStack> recipie = new ArrayList<>();
    public static final Codec<List<ItemStack>> RECIPIE_CODEC = ItemStack.CODEC.listOf();
    int coolDownTimer = 0;

    private final AnimatableInstanceCache cache = GeckoLibUtil.createInstanceCache(this);
    public Gronckle(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    public static AttributeSupplier.Builder createAtributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.MAX_HEALTH,200)
                .add(Attributes.MOVEMENT_SPEED,0.2)
                .add(Attributes.FLYING_SPEED,0.5)
                .add(Attributes.STEP_HEIGHT,1.1)
                .add(Attributes.ARMOR,4)
                .add(Attributes.ATTACK_DAMAGE,6)
                .add(Attributes.ATTACK_KNOCKBACK,1)
                .add(Attributes.FOLLOW_RANGE,20)
                .add(Attributes.JUMP_STRENGTH,0.4)
                .add(Attributes.SNEAKING_SPEED,0.05D)
                .add(ModAttributes.DRAGON_SPRINTING_SPEED,0.4D)
                .add(ModAttributes.DRAGON_FLYING_SLOWDOWN_SPEED,0.2D)
                .add(ModAttributes.DRAGON_FLYING_SPRINTING_SPEED,0.6D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));

        this.goalSelector.addGoal(3,new AvoidEntityGoal<>(this, Ravager.class,15F,1F,2F));
        this.goalSelector.addGoal(4,new MeleeAttackGoal(this,1D,false));

        this.goalSelector.addGoal(6,new WaterAvoidingRandomStrollGoal(this,1.0));

        this.goalSelector.addGoal(7,new RandomLookAroundGoal(this));

        this.goalSelector.addGoal(4,new BreedGoal(this,1.0D));

        this.targetSelector.addGoal(3, new NearestAttackableTargetGoal<>(this, Mob.class, 5, true, false,(livingEntity, serverLevel) -> livingEntity.is(ModTags.EntityTypes.NIGHT_FURY_GETS_ANGRY_AT)));
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return stack.is(Blocks.GRANITE.asItem());
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return new Gronckle(ModEntityTypes.GRONCKLE.get(),level);
    }

    @Override
    public void spawnChildFromBreeding(ServerLevel level, Animal mate) {
        GronckleEgg egg = new GronckleEgg(ModEntityTypes.GRONCKLE_EGG.get(), level);
        Gronckle gronckle = new Gronckle(ModEntityTypes.GRONCKLE.get(),level);
        egg.setPos(this.position());
        setOffspringAttributes((Dragon) mate,gronckle);
        this.finalizeSpawnChildFromBreeding(level, mate, gronckle);
        level.addFreshEntity(egg);
    }

    @Override
    protected void actuallyHurt(ServerLevel level, DamageSource damageSource, float amount) {
        if(damageSource.is(DamageTypes.EXPLOSION)){
            super.actuallyHurt(level, damageSource, 0);
        }
        if(damageSource.is(DamageTypes.LAVA)){
            super.actuallyHurt(level, damageSource, 0);
        }
        if(damageSource.is(DamageTypes.ON_FIRE)){
            super.actuallyHurt(level, damageSource, 0);
        }
        super.actuallyHurt(level, damageSource, amount);
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        if(damageSource.is(DamageTypes.EXPLOSION)){
            return false;
        }
        if(damageSource.is(DamageTypes.LAVA)){
            return false;
        }
        if(damageSource.is(DamageTypes.ON_FIRE)){
            return false;
        }
        return super.hurtServer(level, damageSource, amount);
    }

    public void handelRecipes(){
        if(recipie.isEmpty()){
            return;
        }

        Optional<RecipeHolder<GroncklRecipie>> recipe = getCurrenRecipie();
        if(recipe.isEmpty()){
            return;
        }

        ItemStack outputStack = recipe.get().value().assemble(new GronckleRecipieInput(this.recipie));

        ItemEntity output = new ItemEntity(this.level(),this.getX(),this.getY(),this.getZ(),outputStack);
        output.setThrower(this);
        this.level().addFreshEntity(output);
        recipie.clear();
    }

    private Optional<RecipeHolder<GroncklRecipie>> getCurrenRecipie() {
        return ((ServerLevel) level()).recipeAccess().getRecipeFor(ModRecipieTypes.GRONCKLE_SMELTING.get(), new GronckleRecipieInput(this.recipie),level());
    }

    @Override
    public boolean tamingConditions(LivingEntity living) {
        return false;
    }

    @Override
    public void setOffspringAttributes(Dragon parent, Dragon child) {

    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        if(this.level() instanceof ServerLevel serverLevel){
            ItemStack stack = player.getMainHandItem();
            Item item = stack.getItem();
            NotEnoughDragons.LOGGER.info(item.toString());
            if(player.isShiftKeyDown()){
                if(stack.is(Items.FEATHER)){
                    if(!recipie.isEmpty()){
                        recipie.clear();
                        serverLevel.setBlockAndUpdate(this.blockPosition(),Blocks.LAVA.defaultBlockState());
                        return InteractionResult.SUCCESS;
                    }
                }else if(coolDownTimer <= 0 ){
                    ItemStack toAdd = stack.copyWithCount(1);
                    boolean combine = false;
                    for(ItemStack existing : this.recipie){
                        if(ItemStack.isSameItemSameComponents(existing,toAdd)){
                            existing.grow(toAdd.getCount());
                            combine = true;
                        }
                    }
                    if(!combine){
                        this.recipie.add(toAdd);
                    }
                    coolDownTimer = 5;
                    return InteractionResult.CONSUME;
                }
            }else if(stack.isEmpty() && this.getPassengers().isEmpty()){
                player.startRiding(this,true,true);
            }
        }
        return super.mobInteract(player, hand);
    }

    @Override
    public void tick() {
        coolDownTimer += -1;
        super.tick();
        if(this.level() instanceof ServerLevel serverLevel){
            scalesTick(this);
            handelRecipes();
        }
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        coolDownTimer = input.getIntOr("CoolDownTimer",0);
        Optional<List<ItemStack>> RecipieOptional = input.read("Recipe",RECIPIE_CODEC);
        recipie.clear();
        RecipieOptional.ifPresent(itemStacks ->
                recipie.addAll(itemStacks));
        super.readAdditionalSaveData(input);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        output.putInt("CoolDownTimer",coolDownTimer);
        output.store("Recipe",RECIPIE_CODEC, recipie);
        super.addAdditionalSaveData(output);
    }

    @Override
    public void setInputs(boolean forward, boolean backward, boolean sprint, boolean jump, boolean charging) {

    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return cache;
    }

    @Override
    public Block Scales() {
        return ModBlocks.GRONCKLE_SCALES.get();
    }

    @Override
    public void on_brush_progress_change(int progress) {

    }
}
