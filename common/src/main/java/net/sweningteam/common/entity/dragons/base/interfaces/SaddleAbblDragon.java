package net.sweningteam.common.entity.dragons.base.interfaces;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public interface SaddleAbblDragon {
    boolean hasSaddle();
    void setSaddel(boolean bl);

    default void writeSaddleData(ValueOutput output){
        output.putBoolean("Saddle", hasSaddle());
    }
    default void readSaddleData(ValueInput input){
        setSaddel(input.getBooleanOr("Saddle",false));
    }
}
