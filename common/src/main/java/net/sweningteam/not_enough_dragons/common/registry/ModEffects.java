package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.effect.DragonNip;
import net.sweningteam.not_enough_dragons.platform.Services;

public class ModEffects {

    public static final Holder<MobEffect> DRAGON_NIP = register("dragon_nip",new DragonNip(MobEffectCategory.NEUTRAL,0x5bff00));

    public static Holder<MobEffect> register(String name, MobEffect effect){
        return Services.PLATFORM.getRegisterHelper().registerHolder(BuiltInRegistries.MOB_EFFECT, name,effect);
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Effects for {}!",NotEnoughDragons.MOD_NAME);
    }
}
