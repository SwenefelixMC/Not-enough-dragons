package net.sweningteam.common.dragon.trust;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import net.sweningteam.NotEnoughDragons;

import java.lang.annotation.Native;
import java.util.*;

public class DragonTrust {

    Map<String, Double> Trust = Maps.newHashMap();
    Gson gson = new Gson();

    public void setTrust(UUID key, Double value){
        if(Trust.containsKey(key.toString())){
            Trust.replace(key.toString(), value);
        }else {
            Trust.put(key.toString(), value);
        }
    }

    public void increaseTrust(UUID key, Double amount){
        if(Trust.containsKey(key.toString())){
            Double oldTrust =Trust.get(key.toString());
            Trust.replace(key.toString(), oldTrust+amount);
        }else {
            Trust.put(key.toString(), amount);
        }
    }

    public boolean hasTrust(UUID key){
        return Trust.containsKey(key);

    }

    public Double getTrust(UUID key){
        return Trust.getOrDefault(key, 0D);
    }



    public void clear(){
        Trust.clear();
    }

    @Override
    public String toString() {
        return gson.toJson(Trust);
    }

    public void getTrustFromString(String trust){
        this.Trust = gson.fromJson(trust,Trust.getClass());
    }

    public Map<String, Double> getTrustMap(){
        return Trust;
    }

    public void resortMap(){
        Map<String,Double> newMap = Maps.newHashMap();
        for(String key : Trust.keySet()){
            if(newMap.containsKey(key)){
                newMap.replace(key, Trust.get(key));
            } else {
                newMap.put(key,Trust.get(key));
            }
        }
        Trust.clear();
        Trust.putAll(newMap);
    }

}
