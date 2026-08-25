package net.sweningteam.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.level.block.Block;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.platform.Services;

import java.util.Set;
import java.util.function.Supplier;

public class ModAttributes {
    public static final Supplier<Attribute> DRAGON_SPRINTING_SPEED = register("dragon_sprinting_speed", ()->new RangedAttribute("dragon.sprinting_speed",0.7,0,1024).setSyncable(true));
    public static final Supplier<Attribute> DRAGON_FLYING_SPRINTING_SPEED = register("dragon_flying_sprinting_speed", ()->new RangedAttribute("dragon.flying_sprinting_speed",11,0,1024).setSyncable(true));
    public static final Supplier<Attribute> DRAGON_FLYING_SLOWDOWN_SPEED = register("dragon_flying_slowdown_speed", ()->new RangedAttribute("dragon.flying_slowdown_speed",1,0,1024).setSyncable(true));

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering attributes for {}",NotEnoughDragons.MOD_NAME);
    }

    public static Supplier<Attribute> register(String name, Supplier<Attribute> attribute){
        return Services.PLATFORM.getRegisterHelper().register(BuiltInRegistries.ATTRIBUTE,name,()-> {
            Attribute attribute1 = attribute.get();
            if (attribute1 == null) {
                throw new IllegalStateException("Attempted to register PoiType '" + name + "' before block was registered!");
            }
            return attribute1;
        });
    }
}
