package net.sweningteam.not_enough_dragons.common.dragon.food.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.item.crafting.Ingredient;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponent;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponentType;
import net.sweningteam.not_enough_dragons.common.registry.ModDragonFoodComponentTypes;

public class StandardDragonFoodComponent extends DragonFoodComponent {
    public StandardDragonFoodComponent(IntProvider foodGained, FloatProvider trustGained, Ingredient ingredient) {
        super(foodGained, trustGained, ingredient);
    }

    public static final MapCodec<StandardDragonFoodComponent> codec(){
        return RecordCodecBuilder.mapCodec((instance) -> instance.group(
                IntProviders.CODEC.fieldOf("food_gained").forGetter(DragonFoodComponent::getFoodGained),
                FloatProviders.CODEC.fieldOf("trust_gained").forGetter(DragonFoodComponent::getTrustGained),
                Ingredient.CODEC.fieldOf("ingredient").forGetter(DragonFoodComponent::getIngredient)
        ).apply(instance, StandardDragonFoodComponent::new));
    }

    @Override
    public DragonFoodComponentType<?> type() {
        return ModDragonFoodComponentTypes.BASE_FOOD_COMPONENT.get();
    }
}
