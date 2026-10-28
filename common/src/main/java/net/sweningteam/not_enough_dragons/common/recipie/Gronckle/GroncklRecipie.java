package net.sweningteam.not_enough_dragons.common.recipie.Gronckle;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.sweningteam.not_enough_dragons.common.registry.ModRecipieSerializers;
import net.sweningteam.not_enough_dragons.common.registry.ModRecipieTypes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public record GroncklRecipie(List<Ingredient> recipie, ItemStackTemplate output) implements Recipe<GronckleRecipieInput> {
    public static final MapCodec<GroncklRecipie> CODEC =RecordCodecBuilder.mapCodec((instance)->instance.group(
            Ingredient.CODEC.listOf().fieldOf("input").forGetter(GroncklRecipie::recipie),
            ItemStackTemplate.CODEC.fieldOf("output").forGetter(GroncklRecipie::output))
            .apply(instance,GroncklRecipie::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, GroncklRecipie> STREAM_CODEC =
            StreamCodec.composite(
              Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()),
              GroncklRecipie::recipie,

              ItemStackTemplate.STREAM_CODEC,
              GroncklRecipie::output,

              GroncklRecipie::new);

    @Override
    public boolean matches(GronckleRecipieInput input, Level level) {
        if(level.isClientSide()){
            return false;
        }

        if(input.size() != recipie.size()){
            return false;
        }

        for (int i = 0; i < recipie.size(); i++) {
            if(!recipie.get(i).test(input.getItem(i))){
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack assemble(GronckleRecipieInput input) {
        return output.create().copy();
    }

    @Override
    public boolean showNotification() {
        return false;
    }

    @Override
    public String group() {
        return "Gronckle_Smelting";
    }

    @Override
    public RecipeSerializer<? extends Recipe<GronckleRecipieInput>> getSerializer() {
        return ModRecipieSerializers.GRONCKLE_RECIPIE.get();
    }

    @Override
    public RecipeType<? extends Recipe<GronckleRecipieInput>> getType() {
        return ModRecipieTypes.GRONCKLE_SMELTING.get();
    }

    @Override
    public PlacementInfo placementInfo() {
        return PlacementInfo.NOT_PLACEABLE;
    }

    @Override
    public RecipeBookCategory recipeBookCategory() {
        return RecipeBookCategories.CRAFTING_MISC;
    }

    private boolean listMatches(List<ItemStack> list1, List<ItemStack> list2){
        if(list1.size()!=list2.size()){
            return false;
        }
        for(int i = 0; i < list1.size();i++){
            ItemStack stack1 = list1.get(i);
            ItemStack stack2 = list2.get(i);

            if(!ItemStack.isSameItemSameComponents(stack1,stack2) || stack1.getCount() != stack2.getCount()){
                return false;
            }
        }
        return true;
    }

    private List<ItemStack> normalizeAndSort(List<ItemStack>inputList){
        List<ItemStack> combined = new ArrayList<>();
        for(ItemStack stack : inputList){
            if(stack.isEmpty()) continue;
            boolean matches = false;
            for(ItemStack exsisting : combined){
                if(ItemStack.isSameItemSameComponents(exsisting,stack)){
                    exsisting.grow(stack.getCount());
                    matches = true;
                    break;
                }
            }
            if(!matches){
                combined.add(stack.copy());
            }
        }
        combined.sort(Comparator.comparing(a -> a.getItem().getDescriptionId()));
        return combined;
    }
}
