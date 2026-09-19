package net.sweningteam.not_enough_dragons.mixin;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.level.Level;
import net.sweningteam.not_enough_dragons.common.config.ModMainConfig;
import net.sweningteam.not_enough_dragons.common.entity.dragons.strike_class.night_furry.NightFury;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Raider.class)
public abstract class RaiderMixins extends PatrollingMonster {
    protected RaiderMixins(EntityType<? extends PatrollingMonster> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "registerGoals", at = @At("TAIL"))
    public void targetSelectorMixins(CallbackInfo ci){
        targetSelector.addGoal(2,new NearestAttackableTargetGoal((Mob) (Object)this, NightFury.class,true, this::canAttackPillager));
    }

    @Unique
    public boolean canAttackPillager(LivingEntity entity, ServerLevel level){
        return ModMainConfig.DragonsFightRaiders;
    }
}
