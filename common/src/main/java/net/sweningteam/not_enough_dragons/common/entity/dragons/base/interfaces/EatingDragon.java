package net.sweningteam.not_enough_dragons.common.entity.dragons.base.interfaces;

import net.minecraft.core.HolderSet;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponent;
import net.sweningteam.not_enough_dragons.common.registry.ModRegistries;

import java.util.List;
import java.util.Optional;

public interface EatingDragon {

    List<DragonFoodComponent> foodComponentList();

    int getHunger();

    void setHunger(int hunger);

    int MaxHunger();

    int getSaturation();

    void setSaturation(int saturation);

    int MaxSaturation();

    int getRemainingHungerUpdateTime();
    void setRemainingHungerUpdateTime(int remainingHungerTimer);

    int MaxHungerUpdateTime();

    void updateSaturation();

    TagKey<DragonFoodComponent> FOOD_TAG();

    boolean handleFood(Player player, ItemStack item,Boolean onGround);

    default void addHungerSaveData(ValueOutput output){
        output.putInt("Hunger",getHunger());
        output.putInt("Saturation",getSaturation());
        output.putInt("HungerTime", getRemainingHungerUpdateTime());
    }

    default void readHungerSaveData(ValueInput input){
       setHunger(input.getIntOr("Hunger",MaxHunger()));
       setSaturation(input.getIntOr("Saturation", MaxSaturation()));
       setRemainingHungerUpdateTime(input.getIntOr("HungerTime", MaxHungerUpdateTime()));
    }

    default void startHungerUpdate(int food){
        startHungerTimer();
        setSaturation(food);
    }

    default void startHungerTimer(){
        setRemainingHungerUpdateTime(MaxHungerUpdateTime());
    }

    default void updateHungerTimer(){
        if(getRemainingHungerUpdateTime() > 0){
            setRemainingHungerUpdateTime(getRemainingHungerUpdateTime() -1);
        }else {
            if(getSaturation() > 0){
                setSaturation(getSaturation()-1);
                updateSaturation();
                startHungerTimer();
            }
        }
    }

    default DragonFoodComponent getComponent(ItemStack item){
        DragonFoodComponent component = null;
        for(DragonFoodComponent foodComponent : foodComponentList()){
            if(foodComponent.getIngredient().test(item)){
                component =foodComponent;
            }
        }
        return component;
    }

    default boolean canEat(ItemStack item){
        return getComponent(item) != null;
    }
    default boolean canIncreseSaturation(boolean other){
        return getRemainingHungerUpdateTime() <= 0 && (getHunger() < MaxHunger() || other);
    }

    default void getFoodComponent(Level level){
        Optional<HolderSet.Named<DragonFoodComponent>> testTag = level.registryAccess().lookupOrThrow(ModRegistries.DRAGON_FOOD_COMPONENT).get(FOOD_TAG());
        testTag.ifPresent(holders -> holders.forEach(foodComponent -> {
            foodComponentList().add(foodComponent.value());
        }));
    }
}