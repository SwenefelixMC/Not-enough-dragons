package net.sweningteam.platform;

import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.function.Supplier;

public interface RegisterHelper {
    Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items);

    <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value);

//    Holder<PoiType> registerPOIType(String name, Block block);
//
//    Holder<VillagerProfession> registerVillagerProfession(String name,PoiType poiType);
}
