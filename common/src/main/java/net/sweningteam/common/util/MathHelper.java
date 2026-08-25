package net.sweningteam.common.util;

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
}
