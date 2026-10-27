package net.sweningteam.not_enough_dragons.common.dragon.food;

import com.mojang.serialization.MapCodec;

public class DragonFoodComponentType<F extends DragonFoodComponent> {
    private final MapCodec<F> codec;

    public DragonFoodComponentType(MapCodec<F> codec) {
        this.codec = codec;
    }

    public MapCodec<F> codec() {
        return codec;
    }
}
