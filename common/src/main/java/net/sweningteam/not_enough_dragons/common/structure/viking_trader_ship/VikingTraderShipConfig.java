package net.sweningteam.not_enough_dragons.common.structure.viking_trader_ship;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.ExtraCodecs;

public record VikingTraderShipConfig(float weight) {
    public static final Codec<VikingTraderShipConfig> CODEC = RecordCodecBuilder.create((instance)->instance.group(
            ExtraCodecs.POSITIVE_FLOAT.fieldOf("weight").forGetter(VikingTraderShipConfig::weight)
    ).apply(instance, VikingTraderShipConfig::new));
}
