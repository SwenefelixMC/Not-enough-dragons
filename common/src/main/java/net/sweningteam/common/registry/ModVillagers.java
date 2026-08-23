package net.sweningteam.common.registry;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.platform.Services;

import java.util.Set;
import java.util.function.Supplier;

public class ModVillagers {

    public static final ResourceKey<PoiType> VIKING_POI_KEY = registerPoiKey("viking");

    public static final Supplier<PoiType> VIKING_POI = registerPOIType("viking", ModBlocks.BOOK_OF_DRAGONS);

    public static final Supplier<VillagerProfession> VIKING = registerVillagerProffesion("viking",VIKING_POI_KEY);

    public static Supplier<PoiType> registerPOIType(String name, Supplier<Block> block){
        return Services.PLATFORM.getRegisterHelper().register(
                BuiltInRegistries.POINT_OF_INTEREST_TYPE,
                name,
                ()-> {
                    Block block1 = block.get();
                    if (block1 == null) {
                        throw new IllegalStateException("Attempted to register PoiType '" + name + "' before block was registered!");
                    }
                    return new PoiType(Set.copyOf(block1.getStateDefinition().getPossibleStates()),
                            1, 1);
                });
    }

    public static Supplier<VillagerProfession> registerVillagerProffesion(String name, ResourceKey<PoiType> poiType){
        return Services.PLATFORM.getRegisterHelper().register(
                BuiltInRegistries.VILLAGER_PROFESSION,
                name,
                ()-> new VillagerProfession(
                        Component.translatable("villager."+NotEnoughDragons.MOD_ID+"."+name),
                        holder -> holder.is(poiType),
                        poiTypeHolder -> poiTypeHolder.is(poiType),
                        ImmutableSet.of(),
                        ImmutableSet.of(),
                        SoundEvents.VILLAGER_WORK_LIBRARIAN)
        );
    }

    public static ResourceKey<PoiType> registerPoiKey(String name){
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, ResourceLocation.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,name));
    }

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Villagers for {}", NotEnoughDragons.MOD_NAME);
    }
}
