package net.sweningteam.common.entity.misc;

import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.SharedConstants;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.*;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.monster.*;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.registry.ModVillagerTrades;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

public class VikingTrader extends AbstractVillager {
    private static final int[] NEXT_LEVEL_XP_THRESHOLDS = new int[]{0, 10, 10, 10, 10};
    public VikingTrader(EntityType<? extends AbstractVillager> entityType, Level level) {
        super(entityType, level);
        this.villagerXp = 0;
        this.lastRestockGameTime = 0L;
        this.numberOfRestocksToday = 0;
    }

    private int villagerXp;
    private int updateMerchantTimer;
    private boolean increaseProfessionLevelOnUpdate;

    private long lastRestockGameTime;
    private int numberOfRestocksToday;
    private long lastRestockCheckDayTime;

    public static final EntityDataAccessor<Integer> LEVEL;

    public int getTradeLevel(){
        return entityData.get(LEVEL);
    }
    public void setTradeLevel(int value){
        entityData.set(LEVEL,value);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LEVEL,1);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new TradeWithPlayerGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Zombie.class, 8.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Evoker.class, 12.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Vindicator.class, 8.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Vex.class, 8.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Pillager.class, 15.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Illusioner.class, 12.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, Zoglin.class, 10.0F, (double)0.5F, (double)0.5F));
        this.goalSelector.addGoal(1, new PanicGoal(this, (double)0.5F));
        this.goalSelector.addGoal(1, new LookAtTradingPlayerGoal(this));
        this.goalSelector.addGoal(4, new MoveTowardsRestrictionGoal(this, 0.35));
        this.goalSelector.addGoal(8, new WaterAvoidingRandomStrollGoal(this, 0.35));
        this.goalSelector.addGoal(9, new InteractGoal(this, Player.class, 3.0F, 1.0F));
        this.goalSelector.addGoal(10, new LookAtPlayerGoal(this, Mob.class, 8.0F));

    }

    public static AttributeSupplier.Builder createAtributes() {
        return Animal.createLivingAttributes()
                .add(Attributes.ATTACK_DAMAGE,6)
                .add(Attributes.MAX_HEALTH,40)
                .add(Attributes.MOVEMENT_SPEED,0.75)
                .add(Attributes.FLYING_SPEED,1.1)
                .add(Attributes.STEP_HEIGHT,1.1)
                .add(Attributes.ARMOR,1)
                .add(Attributes.ATTACK_KNOCKBACK,1.25)
                .add(Attributes.FOLLOW_RANGE,20)
                .add(Attributes.JUMP_STRENGTH,0.8)
                .add(Attributes.SNEAKING_SPEED,0.1D);
    }

    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);
        if (!itemStack.is(Items.VILLAGER_SPAWN_EGG) && this.isAlive() && !this.isTrading() && !this.isBaby()) {
            if (hand == InteractionHand.MAIN_HAND) {
                player.awardStat(Stats.TALKED_TO_VILLAGER);
            }

            if (!this.level().isClientSide()) {
                if (this.getOffers().isEmpty()) {
                    return InteractionResult.CONSUME;
                }

                this.setTradingPlayer(player);
                this.openTradingScreen(player, this.getDisplayName(), 1);
            }

            return InteractionResult.SUCCESS;
        } else {
            return super.mobInteract(player, hand);
        }
    }

    public void restock() {
        this.updateDemand();

        for(MerchantOffer merchantOffer : this.getOffers()) {
            merchantOffer.resetUses();
        }

        this.resendOffersToTradingPlayer();
        this.lastRestockGameTime = this.level().getGameTime();
        ++this.numberOfRestocksToday;
    }

    private boolean allowedToRestock() {
        return this.numberOfRestocksToday == 0 || this.numberOfRestocksToday < 2 && this.level().getGameTime() > this.lastRestockGameTime + 2400L;
    }

    public boolean shouldRestock() {
        long l = this.lastRestockGameTime + 12000L;
        long m = this.level().getGameTime();
        boolean bl = m > l;
        long n = this.level().getDayTime();
        if (this.lastRestockCheckDayTime > 0L) {
            long o = this.lastRestockCheckDayTime / 24000L;
            long p = n / 24000L;
            bl |= p > o;
        }

        this.lastRestockCheckDayTime = n;
        if (bl) {
            this.lastRestockGameTime = m;
            this.resetNumberOfRestocks();
        }

        return this.allowedToRestock() && this.needsToRestock();
    }

    private boolean needsToRestock() {
        for(MerchantOffer merchantOffer : this.getOffers()) {
            if (merchantOffer.needsRestock()) {
                return true;
            }
        }

        return false;
    }

    private void updateDemand() {
        for(MerchantOffer merchantOffer : this.getOffers()) {
            merchantOffer.updateDemand();
        }

    }
    private void resendOffersToTradingPlayer() {
        MerchantOffers merchantOffers = this.getOffers();
        Player player = this.getTradingPlayer();
        if (player != null && !merchantOffers.isEmpty()) {
            player.sendMerchantOffers(player.containerMenu.containerId, merchantOffers, getTradeLevel(), this.getVillagerXp(), this.showProgressBar(), this.canRestock());
        }

    }

    @Override
    protected void customServerAiStep(ServerLevel level) {
        if (shouldRestock()){
            restock();
        }
        if (!this.isTrading() && this.updateMerchantTimer > 0) {
            --this.updateMerchantTimer;
            if (this.updateMerchantTimer <= 0) {
                if (this.increaseProfessionLevelOnUpdate) {
                    this.increaseMerchantCareer();
                    this.increaseProfessionLevelOnUpdate = false;
                }

                this.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
            }
        }
        super.customServerAiStep(level);
    }

    @Override
    protected void rewardTradeXp(MerchantOffer offer) {
        this.villagerXp += offer.getXp();
        int i = 3 + this.random.nextInt(4);
        if (this.shouldIncreaseLevel()) {
            this.updateMerchantTimer = 40;
            this.increaseProfessionLevelOnUpdate = true;
            i += 5;
        }

        if (offer.shouldRewardExp()) {
            this.level().addFreshEntity(new ExperienceOrb(this.level(), this.getX(), this.getY() + (double)0.5F, this.getZ(), i));
        }
    }
    public static int getMaxXpPerLevel(int level) {
        return canLevelUp(level) ? NEXT_LEVEL_XP_THRESHOLDS[level] : 0;
    }

    public static boolean canLevelUp(int level) {
        return level >= 1 && level < 5;
    }

    private boolean shouldIncreaseLevel() {
        int i = this.getTradeLevel();
        return canLevelUp(i) && this.villagerXp >= getMaxXpPerLevel(i);
    }

    private void increaseMerchantCareer() {
        this.villagerXp = villagerXp -10;
        this.setTradeLevel(getTradeLevel()+1);
        this.updateTrades();
    }

    @Override
    protected void updateTrades() {
        MerchantOffers merchantOffers = this.getOffers();

        Int2ObjectMap<ModVillagerTrades.ItemListing[]> int2ObjectMap2 = ModVillagerTrades.VIKING_TRADER_TRADES;
        ModVillagerTrades.ItemListing[] itemListings = int2ObjectMap2.get(getTradeLevel());
        if (itemListings != null) {
            this.addOffersFromItemListings(merchantOffers, itemListings, 2);
            if (SharedConstants.DEBUG_UNLOCK_ALL_TRADES && getTradeLevel() < int2ObjectMap2.size()) {
                increaseMerchantCareer();
            }

        }
    }

    protected void addOffersFromItemListings(MerchantOffers givenMerchantOffers, ModVillagerTrades.ItemListing[] newTrades, int maxNumbers) {
        ArrayList<ModVillagerTrades.ItemListing> arrayList = Lists.newArrayList(newTrades);
        int i = 0;

        while(i < maxNumbers && !arrayList.isEmpty()) {
            MerchantOffer merchantOffer = (arrayList.remove(this.random.nextInt(arrayList.size()))).getOffer(this, this.random);
            if (merchantOffer != null) {
                givenMerchantOffers.add(merchantOffer);
                ++i;
            }
        }

    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel level, AgeableMob otherParent) {
        return null;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        entityData.set(LEVEL,input.getIntOr("TradeLevel",1));
        this.villagerXp = input.getIntOr("Xp", 0);
        this.lastRestockGameTime = input.getLongOr("LastRestock", 0L);
        this.numberOfRestocksToday = input.getIntOr("RestocksToday", 0);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("TradeLevel",entityData.get(LEVEL));
        output.putInt("Xp", this.villagerXp);
        output.putLong("LastRestock", this.lastRestockGameTime);
        output.putInt("RestocksToday", this.numberOfRestocksToday);
    }

    public int getVillagerXp() {
        return this.villagerXp;
    }

    public void setVillagerXp(int villagerXp) {
        this.villagerXp = villagerXp;
    }

    private void resetNumberOfRestocks() {
        this.catchUpDemand();
        this.numberOfRestocksToday = 0;
    }
    private void catchUpDemand() {
        int i = 2 - this.numberOfRestocksToday;
        if (i > 0) {
            for(MerchantOffer merchantOffer : this.getOffers()) {
                merchantOffer.resetUses();
            }
        }

        for(int j = 0; j < i; ++j) {
            this.updateDemand();
        }

        this.resendOffersToTradingPlayer();
    }

    static {
        LEVEL = SynchedEntityData.defineId(VikingTrader.class,EntityDataSerializers.INT);
    }
}
