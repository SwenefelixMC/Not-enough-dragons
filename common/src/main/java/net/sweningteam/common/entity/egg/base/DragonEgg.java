package net.sweningteam.common.entity.egg.base;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sweningteam.common.entity.dragons.base.Dragon;

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
            }
        }
        super.tick();
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

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource damageSource, float amount) {
        Random random1 = new Random();
        int ran = random1.nextInt(100);
        if(!(ran < 10)){
            Dead = true;
        }
        return super.hurtServer(level, damageSource, amount);
    }

    public abstract Dragon getDragon(Level level);

    public void BirthDragon(){
        Dragon dragon = getDragon(this.level());
        dragon.setPos(this.position().x,this.position().y,this.position().z);
        this.level().addFreshEntity(dragon);
    }
}
