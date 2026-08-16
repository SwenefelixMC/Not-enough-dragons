package net.sweningteam.neoforge.platform;

import it.unimi.dsi.fastutil.objects.Reference2ObjectOpenHashMap;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.sweningteam.NotEnoughDragons;
import net.sweningteam.platform.RegisterHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class NeoForgeRegistryHelper implements RegisterHelper {




    @SafeVarargs
    @Override
    public final Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items) {
        return register(BuiltInRegistries.CREATIVE_MODE_TAB, name, () -> CreativeModeTab.builder()
                .title(Component.translatable("itemGroup." + NotEnoughDragons.MOD_ID + "." + name))
                .icon(icon)
                .displayItems((context, entries) -> {
                    for (ArrayList<Supplier<? extends Item>> item : items)
                        item.forEach((item1) -> entries.accept(item1.get()));
                })
                .build());
    }

    private static final List<DeferredRegister> defaultedRegistries = new ArrayList<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value) {
        //return ((DeferredRegister<T>) CACHED.computeIfAbsent(registry.key(), key -> DeferredRegister.create(key.name(), NotEnoughDragons.MOD_ID))).register(name, value);
        DeferredRegister deferredRegister = DeferredRegister.create(registry.key(), NotEnoughDragons.MOD_ID);
        if (!defaultedRegistries.contains(deferredRegister)){
            defaultedRegistries.add(deferredRegister);
        }
        return deferredRegister.register(name,value);
    }

    public static void register(final IEventBus bus) {
        for (DeferredRegister defaultedRegistry : defaultedRegistries) {
            defaultedRegistry.register(bus);
        }
    }

}

