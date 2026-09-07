package net.sweningteam.common.dragon.trust;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.sweningteam.NotEnoughDragons;

import java.lang.annotation.Native;
import java.util.*;

public class DragonTrust {

    Map<String, Double> Trust = Maps.newHashMap();
    Double newTrust = 0.0D;
    UUID playerUUid;
    Gson gson = new Gson();

    public void setTrust(UUID key, Double value){
        if (playerUUid != key){
            newTrust = value;
            playerUUid = key;
        }else{
            newTrust = newTrust+value;
        }
    }

    public void increaseTrust(UUID key, Double amount){
        if (playerUUid != key){
            newTrust = amount;
            playerUUid = key;
        }else{
            newTrust = newTrust+amount;
        }
    }

    public boolean hasTrust(UUID key){
        return playerUUid == key;

    }

    public Double getTrust(UUID key){
        if(key == playerUUid){
            return newTrust;
        }else{
            return 0.0D;
        }
    }



    public void clear(){
        newTrust = 0.0D;
    }

    public void writeTrustData(ValueOutput output){
        output.putDouble("Trust",newTrust);
        if(playerUUid!=null){
            output.store("TamingPlayer", UUIDUtil.CODEC,playerUUid);
        }
    }
    public void readTrustData(ValueInput input){
        newTrust = input.getDoubleOr("Trust",0.0D);
        if (input.read("TamingPlayer",UUIDUtil.CODEC).isPresent()){
            playerUUid = input.read("TamingPlayer",UUIDUtil.CODEC).get();
        }
    }
}
