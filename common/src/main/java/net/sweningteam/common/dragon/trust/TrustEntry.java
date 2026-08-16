package net.sweningteam.common.dragon.trust;

import com.mojang.serialization.Codec;

import java.util.UUID;

public class TrustEntry {
    UUID key;
    Double value;
    public TrustEntry(UUID key, Double value){
        this.key = key;
        this.value = value;
    }

    public UUID getKey() {
        return key;
    }
    public Double getValue() {
        return value;
    }

}
