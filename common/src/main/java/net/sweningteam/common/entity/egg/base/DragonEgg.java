package net.sweningteam.common.entity.egg.base;

import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sweningteam.common.entity.dragons.base.Dragon;
import net.sweningteam.common.item.egg.DragonEggItem;
import net.sweningteam.common.registry.ModAttributes;
import net.sweningteam.common.registry.ModItems;

import java.util.Optional;

public abstract class DragonEgg extends Mob {
    int EmbryoAge = 0;
    int AgeUpdateTick = 24000;
    int HatchTick = 0;
    public int HatchAge = 3;
    boolean Dead;

    public Dragon embryo;

    public DragonEggData dragonEggData = new DragonEggData(6.5,1.25,0.8,0.1D,0.38,1.1,0.7D,0.25,2.2);

    protected DragonEgg(EntityType<? extends Mob> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    public Item EggItem(){
        return null;
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
            ItemStack itemStack = new ItemStack(EggItem());
            DragonEggItem.SetDragon(this.Dead,this.EmbryoAge,this.HatchTick,itemStack,dragonEggData);
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
        output.store("Embryo",DragonEggData.CODEC,dragonEggData);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        EmbryoAge = input.getIntOr("EmbryoAge",0);
        HatchTick = input.getIntOr("HatchTick",0);
        Dead = input.getBooleanOr("Dead", false);
        Optional<DragonEggData> dragonEggDataOptional = input.read("Embryo",DragonEggData.CODEC);
        dragonEggDataOptional.ifPresent(eggData -> dragonEggData = eggData);
    }

    public void setDragonEgg(Boolean dead, Integer EmbryoAge, Integer HatchTick,DragonEggData DragonEggData){
        this.Dead = dead;
        this.EmbryoAge = EmbryoAge;
        this.HatchTick = HatchTick;
        this.dragonEggData = DragonEggData;
    }

    public abstract Dragon getDragon(Level level);

    public void onHatch(Dragon dragon){

    }

    public void BirthDragon(){
        Dragon dragon = getDragon(this.level());
        dragon.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(dragonEggData.AttackDamage());
        dragon.getAttribute(Attributes.ATTACK_KNOCKBACK).setBaseValue(dragonEggData.Attack_knockback());
        dragon.getAttribute(Attributes.JUMP_STRENGTH).setBaseValue(dragonEggData.Jump_strength());
        dragon.getAttribute(Attributes.SNEAKING_SPEED).setBaseValue(dragonEggData.Sneaking_speed());
        dragon.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(dragonEggData.Movement_speed());
        dragon.getAttribute(Attributes.FLYING_SPEED).setBaseValue(dragonEggData.Flying_Speed());
        dragon.getAttribute(Holder.direct(ModAttributes.DRAGON_SPRINTING_SPEED.get())).setBaseValue(dragonEggData.Sprinting_Speed());
        dragon.getAttribute(Holder.direct(ModAttributes.DRAGON_FLYING_SPRINTING_SPEED.get())).setBaseValue(dragonEggData.Flying_Sprint_Speed());
        dragon.getAttribute(Holder.direct(ModAttributes.DRAGON_FLYING_SLOWDOWN_SPEED.get())).setBaseValue(dragonEggData.Fly_Slow_Speed());
        dragon.setPos(this.position().x,this.position().y,this.position().z);
        dragon.setBaby(true);
        onHatch(dragon);
        this.level().addFreshEntity(dragon);
    }
    public void compileDragonEggData(DragonEggData data){
        this.dragonEggData = data;
    }
}
