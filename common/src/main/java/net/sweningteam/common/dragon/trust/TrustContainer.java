package net.sweningteam.common.dragon.trust;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.UUIDUtil;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

import java.util.*;

public class TrustContainer {
    Map<UUID,Double> Trust = new HashMap<>();
    public static final Codec<TrustContainer> CODEC = TrustEntry.CODEC.listOf().xmap(TrustContainer::new,(TrustContainer::SplitIterator));

    public TrustContainer(){

    }

    private TrustContainer(List<TrustEntry> entries){
        entries.forEach(trustEntry->Trust.put(trustEntry.key(),trustEntry.value()));
    }


    public void setTrust(UUID key, Double value){
        if(Trust.containsKey(key)){
            Trust.replace(key, value);
        }else {
            Trust.put(key, value);
        }
    }

    public void increaseTrust(UUID key, Double amount){
        if(Trust.containsKey(key)){
            Double oldTrust =Trust.get(key);
            Trust.replace(key, oldTrust+amount);
        }else {
            Trust.put(key, amount);
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

    public Map<UUID, Double> getTrustMap(){
        return Trust;
    }

    public void resortMap(){
        Map<UUID,Double> newMap = new HashMap<>();
        for(UUID key : Trust.keySet()){
            if(newMap.containsKey(key)){
                newMap.replace(key, Trust.get(key));
            } else {
                newMap.put(key,Trust.get(key));
            }
        }
        Trust.clear();
        Trust.putAll(newMap);
    }

    private List<TrustEntry> SplitIterator(){
        List<TrustEntry> entriesList = new ArrayList<>();
        for (Map.Entry<UUID,Double> entries : Trust.entrySet()){
            entriesList.add(new TrustEntry(entries.getKey(),entries.getValue()));
        }
        return entriesList;
    }

    public record TrustEntry(UUID key,Double value){
        public static final Codec<TrustEntry> CODEC = RecordCodecBuilder.create((instance)->instance.group(
                UUIDUtil.CODEC.fieldOf("key").forGetter(TrustEntry::key),
                Codec.DOUBLE.fieldOf("value").forGetter(TrustEntry::value))
                .apply(instance,TrustEntry::new));
    }
}
