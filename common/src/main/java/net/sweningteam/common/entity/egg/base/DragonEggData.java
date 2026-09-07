package net.sweningteam.common.entity.egg.base;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record DragonEggData (Double AttackDamage, Double Attack_knockback, Double Jump_strength, Double Sneaking_speed, Double Movement_speed, Double Flying_Speed, Double Sprinting_Speed, Double Fly_Slow_Speed, Double Flying_Sprint_Speed) {

    public static final Codec<DragonEggData> CODEC = RecordCodecBuilder.create((instance)->
                    instance.group(Codec.DOUBLE.fieldOf("AttackDamage").forGetter(DragonEggData::AttackDamage),
                    Codec.DOUBLE.fieldOf("Attack_knockback").forGetter(DragonEggData::Attack_knockback),
                    Codec.DOUBLE.fieldOf("Jump_strength").forGetter(DragonEggData::Jump_strength),
                    Codec.DOUBLE.fieldOf("Sneaking_speed").forGetter(DragonEggData::Sneaking_speed),
                    Codec.DOUBLE.fieldOf("Movement_speed").forGetter(DragonEggData::Movement_speed),
                    Codec.DOUBLE.fieldOf("Flying_Speed").forGetter(DragonEggData::Flying_Speed),
                    Codec.DOUBLE.fieldOf("Sprinting_Speed").forGetter(DragonEggData::Sprinting_Speed),
                    Codec.DOUBLE.fieldOf("Fly_Slow_Speed").forGetter(DragonEggData::Fly_Slow_Speed),
                    Codec.DOUBLE.fieldOf("Flying_Sprint_Speed").forGetter(DragonEggData::Flying_Sprint_Speed)
            ).apply(instance,DragonEggData::new));
}
