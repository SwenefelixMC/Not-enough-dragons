package net.sweningteam.common.entity.egg.base;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sweningteam.common.entity.dragons.base.Dragon;
import net.sweningteam.common.registry.ModItems;

import java.util.Random;

public abstract class DragonEgg extends Mob {
    int EmbryoAge = 0;
    int AgeUpdateTick = 24000;
    int HatchTick = 0;
    public int HatchAge = 1;
    boolean Dead;

    public Dragon embryo;

    protected DragonEgg(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public void tick() {
        if (!this.level().isClientSide()){
            if(!Dead){
                if(HatchTick < AgeUpdateTick){
                    HatchTick = HatchTick + 1;
                }else{
                    if(EmbryoAge < HatchAge){
                        EmbryoAge = EmbryoAge + 1;
                    }else {
                        BirthDragon();
                        this.remove(RemovalReason.DISCARDED);
                    }
                }
                if(this.getMaxHealth()-this.getHealth()>this.getMaxHealth()*0.75){
                    this.Dead = true;
                }
            }
        }
        super.tick();
    }

    @Override
    protected InteractionResult mobInteract(Player player, InteractionHand hand) {
        if(player.isShiftKeyDown() && !this.level().isClientSide()){
            ItemStack itemStack = new ItemStack(ModItems.NIGHT_FURY_EGG.get());
            ItemEntity itemEntity = new ItemEntity(this.level(), this.position().x(), this.position().y(), this.position().z(), itemStack);
            itemEntity.setDefaultPickUpDelay();
            this.level().addFreshEntity(itemEntity);
            this.remove(RemovalReason.DISCARDED);
            return InteractionResult.SUCCESS;
        }else{
            return super.mobInteract(player, hand);
        }
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putInt("EmbryoAge", EmbryoAge);
        output.putInt("Tick", HatchTick);
        output.putBoolean("Dead", Dead);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        EmbryoAge = input.getIntOr("EmbryoAge",0);
        HatchTick = input.getIntOr("HatchTick",0);
        Dead = input.getBooleanOr("Dead", false);
    }

    public abstract Dragon getDragon(Level level);

    public void BirthDragon(){
        Dragon dragon = getDragon(this.level());
        dragon.setPos(this.position().x,this.position().y,this.position().z);
        dragon.setBaby(true);
        this.level().addFreshEntity(dragon);
    }
}
