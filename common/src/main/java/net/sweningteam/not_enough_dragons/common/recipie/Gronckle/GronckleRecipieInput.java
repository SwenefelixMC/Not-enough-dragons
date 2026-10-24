package net.sweningteam.not_enough_dragons.common.recipie.Gronckle;

import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeInput;
import net.minecraft.world.item.crafting.RecipeSerializer;

import java.util.List;

public record GronckleRecipieInput(List<ItemStack> input) implements RecipeInput {

    @Override
    public ItemStack getItem(int index) {
        return input.get(index);
    }

    @Override
    public int size() {
        return input().size();
    }
}
