package net.sweningteam.not_enough_dragons.fabric.platform;

import net.fabricmc.fabric.api.creativetab.v1.FabricCreativeModeTab;
import net.fabricmc.fabric.api.object.builder.v1.world.poi.PoiHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.platform.RegisterHelper;

import java.util.ArrayList;
import java.util.function.Supplier;

public class FabricRegistryHelper implements RegisterHelper {
    @SafeVarargs
    @Override
    public final Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items) {
        CreativeModeTab tab = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, name), FabricCreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + NotEnoughDragons.MOD_ID + "." + name))
                .icon(icon)
                .displayItems((parameters, output) -> {
                    for (ArrayList<Supplier<? extends Item>> item : items)
                        item.forEach((item1) -> output.accept(item1.get()));
                })
                .build());
        return () -> tab;
    }

    @Override
    public <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value) {
        T registeredObject = Registry.register(
                registry,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,name),
                value.get());
        return () -> registeredObject;
    }

    @Override
    public <T> Holder<T> registerHolder(Registry<? super T> registry, String name, T value) {
        Holder<T> registeredObject = Registry.registerForHolder(
                registry,
                Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,name),
                value);
        return registeredObject;
    }

    @Override
    public Supplier<PoiType> registerPOIType(String name, Supplier<Block> block, int tickets, int range) {
        PoiType poitype = PoiHelper.register(Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, name),tickets,range,block.get());
        return ()-> poitype;
    }

}
