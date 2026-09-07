package net.sweningteam.common.registry;

import com.mojang.serialization.Codec;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.common.entity.egg.base.DragonEggData;
import net.sweningteam.platform.Services;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

public class ModDataComponentTypes {

    public static final Supplier<DataComponentType<Boolean>> IS_DEAD = register(
            "is_dead",
            builder -> builder.persistent(Codec.BOOL).networkSynchronized(ByteBufCodecs.BOOL));

    public static final Supplier<DataComponentType<Integer>> EMBRYO_AGE = register(
            "embryo_age",
            builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    public static final Supplier<DataComponentType<Integer>> EGG_TICK = register(
            "egg_tick",
            builder -> builder.persistent(Codec.INT).networkSynchronized(ByteBufCodecs.INT));

    public static final Supplier<DataComponentType<DragonEggData>> DRAGON_EGG_DATA = register(
            "dragon_egg_data",
            builder -> builder.persistent(DragonEggData.CODEC).networkSynchronized(ByteBufCodecs.fromCodec(DragonEggData.CODEC)));


    public static <T>Supplier<DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builder){
        return Services.PLATFORM.getRegisterHelper().register(BuiltInRegistries.DATA_COMPONENT_TYPE,name,()->builder.apply(DataComponentType.builder()).build());
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Data Components for "+ NotEnoughDragons.MOD_NAME);
    }
}
