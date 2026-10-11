package net.sweningteam.not_enough_dragons.common.entity.dragons.base.interfaces;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;

public interface FlyingDragon {
    Boolean isFlying();

    void setFlying(Boolean bl);

    double flyingSpeed();

    void setFlyingSpeed(double speed);

    default void addFlyingData(ValueOutput output, Dragon dragon){
        output.putBoolean("Flying",isFlying());
        output.putDouble("FlyingSpeed", flyingSpeed());
    }

    default void readFlyingData(ValueInput input, Dragon dragon){
        setFlying(input.getBooleanOr("Flying",false));
        setFlyingSpeed(input.getDoubleOr("Flying",0));
    }

    default void updtateFlying(Dragon dragon){
        if(dragon.isInLiquid() && !dragon.hasControllingPassenger()){
            setFlying(false);
        }
        if(dragon.onGround() && isFlying()){
            setFlying(false);
        }
    }

}
