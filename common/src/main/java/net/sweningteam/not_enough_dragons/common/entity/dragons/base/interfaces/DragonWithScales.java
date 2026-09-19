package net.sweningteam.not_enough_dragons.common.entity.dragons.base.interfaces;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.VegetationBlock;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;
import net.sweningteam.not_enough_dragons.common.util.Brushable;

public interface DragonWithScales extends Brushable {
    Block Scales();

    default void scalesTick(Dragon dragon){
        RandomSource random = dragon.getRandom();
        if(random.nextInt(10000) > 9995){
            Level level = dragon.level();
            BlockPos pos = dragon.blockPosition();
            boolean bl = false;
            for(int i = 0; i < 100; i++){
                BlockPos pos1 = pos.offset(0,i*-1,0);
                if(level.getBlockState(pos).isAir() && !level.getBlockState(pos1).isFaceSturdy(level,pos1, Direction.UP)){

                    if(Scales() instanceof VegetationBlock vegetationBlock){
                        //bl = vegetationBlock.canSurvive(level.getBlockState(pos),level,pos);
                        //todo
                    }
                    level.setBlockAndUpdate(pos,Scales().defaultBlockState());
                    bl = true;
                    break;
                }
                if(!level.getBlockState(pos).isAir()){
                    break;
                }
            }
            if(!bl){
                ItemEntity Item = new ItemEntity(level,dragon.getX(),dragon.getY(),dragon.getZ(),new ItemStack(Scales()));
                Item.setThrower(dragon);
                level.addFreshEntity(Item);
            }
        }
    }

}
