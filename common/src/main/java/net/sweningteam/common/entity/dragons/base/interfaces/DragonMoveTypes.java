package net.sweningteam.common.entity.dragons.base.interfaces;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public interface DragonMoveTypes {
    enum moveType {
        Idle,
        Sneak,
        Walk,
        SneakWalk,
        Sprint,
        Jump,
        Fall,
        FallFly,
        Fly,
        FlyUp,
        Glide,
        FlyDown,
        Land,
        None
    }

    moveType MoveType();

    default moveType setMoveType(String savedMoveType){
        return switch (savedMoveType) {
            case "Idle" -> moveType.Idle;
            case "Sneak" -> moveType.Sneak;
            case "Walk" -> moveType.Walk;
            case "SneakWalk" -> moveType.SneakWalk;
            case "Sprint" -> moveType.Sprint;
            case "Jump" -> moveType.Jump;
            case "Fall" -> moveType.Fall;
            case "FallFly" -> moveType.FallFly;
            case "Fly" -> moveType.Fly;
            case "FlyUp" -> moveType.FlyUp;
            case "Glide" -> moveType.Glide;
            case "FlyDown" -> moveType.FlyDown;
            case "Land" -> moveType.Land;
            case null, default -> moveType.None;
        };
    }

    default String getMoveType(){
        if (MoveType() == moveType.Idle){
            return "Idle";
        } else if (MoveType() == moveType.Sneak) {
            return "Sneak";
        } else if (MoveType() == moveType.Walk){
            return "Walk";
        }
        else if (MoveType() == moveType.SneakWalk){
            return "SneakWalk";
        }
        else if (MoveType() == moveType.Sprint) {
            return "Sprint";
        }
        else if (MoveType() == moveType.Jump){
            return "Jump";
        }
        else if (MoveType() == moveType.Fall){
            return "Fall";
        }
        else if (MoveType() == moveType.FallFly){
            return "FallFly";
        }
        else if (MoveType() == moveType.Fly){
            return "Fly";
        }
        else if (MoveType() == moveType.FlyUp){
            return "FlyUp";
        }
        else if (MoveType() == moveType.Glide){
            return "Glide";
        }
        else if (MoveType() == moveType.FlyDown){
            return "FlyDown";
        }
        else if (MoveType() == moveType.Land){
            return "Land";
        }
        else {
            return "";
        }
    }

    default void addMoveTypeSaveData(ValueOutput output){
        output.putString("MoveType",getMoveType());
    }

    default void readMoveTypeSaveData(ValueInput input){
        setMoveType(input.getStringOr("MoveType","Idle"));
    }
}
