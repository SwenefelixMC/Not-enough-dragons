package net.sweningteam.common.dragon.food;

import net.sweningteam.common.entity.dragons.base.Dragon;

public class DragonFoodComponent {
    int food_gained;
    double trust_gained;
    DragonFoodComponent food_component;
    public DragonFoodComponent(int food_gained, double trust_gained){
        this.food_gained = food_gained;
        this.trust_gained = trust_gained;
        this.food_component = this;
    }
    public void onEat(Dragon dragon){

    }
    public int getFood_gained(){
        return this.food_gained;
    }

    public double getTrust_gained() {
        return this.trust_gained;
    }
}
