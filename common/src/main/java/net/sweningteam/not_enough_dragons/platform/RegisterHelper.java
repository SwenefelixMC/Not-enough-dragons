package net.sweningteam.not_enough_dragons.platform;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.Supplier;

public interface RegisterHelper {
    Supplier<CreativeModeTab> createCreativeTab(String name, Supplier<ItemStack> icon, ArrayList<Supplier<? extends Item>>... items);

    <T> Supplier<T> register(Registry<? super T> registry, String name, Supplier<T> value);

    <T> Holder<T> registerHolder(Registry<? super T> registry,String name, T value);

    default Supplier<PoiType> registerPOIType(String name, Supplier<Block> block,int tickets, int range){
        return register(
                BuiltInRegistries.POINT_OF_INTEREST_TYPE,
                name,
                ()-> {
                    Block block1 = block.get();
                    if (block1 == null) {
                        throw new IllegalStateException("Attempted to register PoiType '" + name + "' before block was registered!");
                    }
                    return new PoiType(Set.copyOf(block1.getStateDefinition().getPossibleStates()),
                            tickets, range);
                });
    };

    default KeyMapping registerKeyMapping(String name, InputConstants.Type type, int key, KeyMapping.Category category){
        KeyMapping mapping = new KeyMapping(name, type, key, category);
        return mapping;
    }

}
