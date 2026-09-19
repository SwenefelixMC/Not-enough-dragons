package net.sweningteam.not_enough_dragons.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.InsideBlockEffectApplier;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;
import net.sweningteam.not_enough_dragons.common.registry.ModEffects;

public class DragonNipBlock extends PlantBlock{
    public DragonNipBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity, InsideBlockEffectApplier applier, boolean intersects) {
        if(level instanceof ServerLevel serverLevel) {
            if (entity instanceof Dragon dragon) {
                dragon.addEffect(new MobEffectInstance(ModEffects.DRAGON_NIP,480));
            }
        }
        super.entityInside(state, level, pos, entity, applier, intersects);
    }
}
