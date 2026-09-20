package net.sweningteam.not_enough_dragons.common.recipie;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import net.sweningteam.not_enough_dragons.common.registry.ModItems;

import java.util.List;
import java.util.Map;

public class GronckleRecipies {
    public static Map<List<ItemStack>,ItemStack> ALL_RECIPIES = ImmutableMap.of(
            ImmutableList.of(
                    new ItemStack(Blocks.STONE,3),
                    new ItemStack(Blocks.SANDSTONE,2),
                    new ItemStack(Blocks.IRON_ORE),
                    new ItemStack(Blocks.COBBLESTONE)
            ),
            new ItemStack(ModItems.MOLTEN_GROCKLE_IRON.get(),6),
            ImmutableList.of(
                    new ItemStack(Blocks.COPPER_BLOCK.weathering().oxidized() ,3),
                    new ItemStack(ModItems.GRONCKLE_IRON_INGOT.get() ,3),
                    new ItemStack(Blocks.IRON_BLOCK,2),
                    new ItemStack(Blocks.ANCIENT_DEBRIS)
            ),
            new ItemStack(ModItems.MOLTEN_DRAGON_PROVED_STEEL.get(),6)
    );
}
