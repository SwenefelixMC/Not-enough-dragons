package net.sweningteam.not_enough_dragons.common.dragon.food.custom;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.FloatProviders;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.item.crafting.Ingredient;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponent;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponentType;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;
import net.sweningteam.not_enough_dragons.common.registry.ModDragonFoodComponentTypes;

public class EffectDragonFoodComponent extends DragonFoodComponent {
    public final MobEffectInstance effect;
    public EffectDragonFoodComponent(IntProvider foodGained, FloatProvider trustGained, Ingredient ingredient, MobEffectInstance effect) {
        super(foodGained, trustGained, ingredient);
        this.effect = effect;
    }

    public static MapCodec<EffectDragonFoodComponent> codec() {
        return RecordCodecBuilder.mapCodec((instance) -> instance.group(
                IntProviders.CODEC.fieldOf("food_gained").forGetter(DragonFoodComponent::getFoodGained),
                FloatProviders.CODEC.fieldOf("trust_gained").forGetter(DragonFoodComponent::getTrustGained),
                Ingredient.CODEC.fieldOf("ingredient").forGetter(DragonFoodComponent::getIngredient),
                MobEffectInstance.CODEC.fieldOf("effect").forGetter(EffectDragonFoodComponent::getEffect)
        ).apply(instance, EffectDragonFoodComponent::new));
    }

    public MobEffectInstance getEffect() {
        return effect;
    }

    @Override
    public DragonFoodComponentType<?> type() {
        return ModDragonFoodComponentTypes.EFFECT_FOOD_COMPONENT.get();
    }

    @Override
    public void onEat(Dragon dragon) {
        super.onEat(dragon);
        dragon.addEffect(effect);
    }
}
