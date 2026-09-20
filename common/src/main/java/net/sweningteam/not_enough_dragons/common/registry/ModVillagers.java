package net.sweningteam.not_enough_dragons.common.registry;

import com.google.common.collect.ImmutableSet;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import net.minecraft.world.item.trading.TradeSets;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.entity.villager.ModTradeSets;
import net.sweningteam.not_enough_dragons.platform.Services;

import java.util.function.Supplier;

public class ModVillagers {

    public static final Supplier<VillagerProfession> VIKING = registerVillagerProfession("viking",ModPoiTypes.VIKING_POI);

    public static Supplier<VillagerProfession> registerVillagerProfession(String name, ResourceKey<PoiType> poiType){
        return Services.PLATFORM.getRegisterHelper().register(
                BuiltInRegistries.VILLAGER_PROFESSION,
                name,
                ()-> new VillagerProfession(
                        Component.translatable("villager."+NotEnoughDragons.MOD_ID+"."+name),
                        holder -> holder.is(poiType),
                        poiTypeHolder -> poiTypeHolder.is(poiType),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        SoundEvents.VILLAGER_WORK_LIBRARIAN,
                        Int2ObjectMap.ofEntries(new Int2ObjectMap.Entry[]{Int2ObjectMap.entry(1, ModTradeSets.VIKING_LEVEL_1), Int2ObjectMap.entry(2, ModTradeSets.VIKING_LEVEL_2), Int2ObjectMap.entry(3, ModTradeSets.VIKING_LEVEL_3), Int2ObjectMap.entry(4, ModTradeSets.VIKING_LEVEL_4), Int2ObjectMap.entry(5, ModTradeSets.VIKING_LEVEL_5)})
                ));

    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Villagers for {}", NotEnoughDragons.MOD_NAME);
    }
}
