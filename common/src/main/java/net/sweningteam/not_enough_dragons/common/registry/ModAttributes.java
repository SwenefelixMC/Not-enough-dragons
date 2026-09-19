package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.platform.Services;

public class ModAttributes {
    public static final Holder<Attribute> DRAGON_SPRINTING_SPEED = register("dragon_sprinting_speed", new RangedAttribute("dragon.sprinting_speed",0.7,0,1024).setSyncable(true));
    public static final Holder<Attribute> DRAGON_FLYING_SPRINTING_SPEED = register("dragon_flying_sprinting_speed", new RangedAttribute("dragon.flying_sprinting_speed",11,0,1024).setSyncable(true));
    public static final Holder<Attribute> DRAGON_FLYING_SLOWDOWN_SPEED = register("dragon_flying_slowdown_speed", new RangedAttribute("dragon.flying_slowdown_speed",1,0,1024).setSyncable(true));
    public static final Holder<Attribute> DRAGON_TAMING_TRUST = register("dragon_taming_trust", new RangedAttribute("dragon.taming_trust",1,0,1024).setSyncable(true));
    public static final Holder<Attribute> DRAGON_SHOT_LIMIT = register("dragon_shot_limit", new RangedAttribute("dragon.shot_limit",1,0,1024).setSyncable(true));

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering attributes for {}",NotEnoughDragons.MOD_NAME);
    }

    public static Holder<Attribute> register(String name,Attribute attribute){
        return Services.PLATFORM.getRegisterHelper().registerHolder(BuiltInRegistries.ATTRIBUTE,name,attribute);
    }
}
