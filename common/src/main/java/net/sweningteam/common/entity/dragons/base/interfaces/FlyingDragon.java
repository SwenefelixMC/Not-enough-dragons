package net.sweningteam.common.entity.dragons.base.interfaces;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.entity.dragons.base.Dragon;

public interface FlyingDragon {
    Boolean isFlying();

    void setFlying(Boolean bl);

    default void addFlyingData(ValueOutput output, Dragon dragon){
        output.putBoolean("Flying",isFlying());
    }

    default void readFlyingData(ValueInput input, Dragon dragon){
        setFlying(input.getBooleanOr("Flying",false));
    }

    default void updtateFlying(Dragon dragon){
        if(dragon.onGround() && isFlying()){
            NotEnoughDragons.LOGGER.info("Stoped Flying");
            setFlying(false);
        }
    }

}
