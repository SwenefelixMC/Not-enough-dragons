package net.sweningteam.not_enough_dragons.common.util;

public class MathHelper {
    public static double moveToward(double variable, double goal, double speed){
        if(variable < goal){
            return variable + speed;
        }else if (variable > goal){
            return variable - speed;
        }else{
            return goal;
        }
    }
    public static double getDistanceBetween(Double v1, Double v2){
        return Math.abs(v1-v2);
    }
}
