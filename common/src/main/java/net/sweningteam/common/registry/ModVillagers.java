package net.sweningteam.common.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.platform.Services;

import java.util.function.Supplier;

public class ModVillagers {

    public static final Supplier<VillagerProfession> VIKING = registerVillagerProfession("viking",ModPoiTypes.VIKING_POI);

    public static Supplier<VillagerProfession> registerVillagerProfession(String name, Supplier<PoiType> poiType){
        return Services.PLATFORM.getRegisterHelper().register(
                BuiltInRegistries.VILLAGER_PROFESSION,
                name,
                ()-> new VillagerProfession(
                        Component.translatable("villager."+NotEnoughDragons.MOD_ID+"."+name),
                        holder -> holder.value() == poiType.get(),
                        poiTypeHolder -> poiTypeHolder.value() == poiType.get(),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        SoundEvents.VILLAGER_WORK_LIBRARIAN)
        );
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Villagers for {}", NotEnoughDragons.MOD_NAME);
    }
}
