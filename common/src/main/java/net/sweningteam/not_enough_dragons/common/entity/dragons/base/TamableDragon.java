package net.sweningteam.not_enough_dragons.common.entity.dragons.base;

import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityReference;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.OwnableEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.scores.PlayerTeam;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class TamableDragon extends Dragon implements OwnableEntity {
    private static final EntityDataAccessor<Boolean> TAMED;
    private static final EntityDataAccessor<Optional<EntityReference<LivingEntity>>> DATA_OWNERUUID_ID;

    private EntityReference<LivingEntity> owner;

    public boolean isTamed(){
        return entityData.get(TAMED);
    }

    public void setTamed(boolean tamed){
        entityData.set(TAMED,tamed);
    }

    public void setTame(boolean tame, boolean applyTamingSideEffects) {
        this.setTamed(tame);

        if (applyTamingSideEffects) {
            this.applyTamingSideEffects();
        }

    }

    protected TamableDragon(EntityType<? extends Animal> entityType, Level level) {
        super(entityType, level);
    }

    @Nullable
    public EntityReference<LivingEntity> getOwnerReference() {
        Optional<EntityReference<LivingEntity>> OwnerUUIDOptional = this.entityData.get(DATA_OWNERUUID_ID);
        return OwnerUUIDOptional.orElse(null);
    }

    public void setOwner(@Nullable LivingEntity owner) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(owner).map(EntityReference::of));
    }

    public void setOwnerReference(@Nullable EntityReference<LivingEntity> owner) {
        this.entityData.set(DATA_OWNERUUID_ID, Optional.ofNullable(owner));
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(TAMED,false);
        builder.define(DATA_OWNERUUID_ID, Optional.empty());
        super.defineSynchedData(builder);
    }

    public void Tame(Player player){
        if(player instanceof ServerPlayer serverPlayer){
            this.setTamed(true);
            this.setOwner(player);
            applyTamingSideEffects();
            if (player instanceof ServerPlayer) {
                CriteriaTriggers.TAME_ANIMAL.trigger(serverPlayer, this);
            }

            this.level().broadcastEntityEvent(this, (byte)7);
        }
    }

    public final boolean unableToMoveToOwner() {
        return this.isPassenger() || this.mayBeLeashed() || this.getOwner() != null && this.getOwner().isSpectator();
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        EntityReference<LivingEntity> entityReference = this.getOwnerReference();
        EntityReference.store(entityReference, output, "Owner");
        super.addAdditionalSaveData(output);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        EntityReference<LivingEntity> entityreference = EntityReference.readWithOldOwnerConversion(input, "Owner", this.level());
        if (entityreference != null) {
            try {
                this.entityData.set(DATA_OWNERUUID_ID, Optional.of(entityreference));
                this.setTame(true, false);
            } catch (Throwable var4) {
                this.setTame(false, true);
            }
        } else {
            this.entityData.set(DATA_OWNERUUID_ID, Optional.empty());
            this.setTame(false, true);
        }
    }

    @Nullable
    public PlayerTeam getTeam() {
        PlayerTeam playerteam = super.getTeam();
        if (playerteam != null) {
            return playerteam;
        } else {
            if (this.isTamed()) {
                LivingEntity livingentity = this.getRootOwner();
                if (livingentity != null) {
                    return livingentity.getTeam();
                }
            }

            return null;
        }
    }

    protected void applyTamingSideEffects() {
    }

    static {
        TAMED = SynchedEntityData.defineId(TamableDragon.class, EntityDataSerializers.BOOLEAN);
        DATA_OWNERUUID_ID = SynchedEntityData.defineId(TamableDragon.class,EntityDataSerializers.OPTIONAL_LIVING_ENTITY_REFERENCE);
    }
}
