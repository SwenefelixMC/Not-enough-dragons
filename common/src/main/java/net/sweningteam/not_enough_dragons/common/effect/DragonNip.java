package net.sweningteam.not_enough_dragons.common.effect;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;

public class DragonNip extends MobEffect {
    public DragonNip(MobEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public boolean applyEffectTick(ServerLevel level, LivingEntity entity, int amplifier) {
        if(entity instanceof Dragon dragon){
            if(dragon.getTarget()!= null){
                dragon.setTarget(null);
            }
        }
        return super.applyEffectTick(level, entity, amplifier);
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
