package net.sweningteam.common.entity.dragons.base.interfaces;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sweningteam.common.entity.dragons.base.Dragon;

public interface FlyingDragon {
    Boolean isFlying();

    void setIsFlying(Boolean bl);

    default void addFlyingData(ValueOutput output, Dragon dragon){
        output.putBoolean("Flying",isFlying());
        output.putBoolean("NoGravity",dragon.isNoGravity());
    }

    default void readFlyingData(ValueInput input, Dragon dragon){
        setIsFlying(input.getBooleanOr("Flying",false));
        dragon.setNoGravity(input.getBooleanOr("NoGravity",false));
    }

}
