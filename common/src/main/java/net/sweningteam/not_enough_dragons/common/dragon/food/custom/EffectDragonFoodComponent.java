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

import java.util.List;

public class EffectDragonFoodComponent extends DragonFoodComponent {
    public final List<MobEffectInstance> effects;
    public EffectDragonFoodComponent(IntProvider foodGained, FloatProvider trustGained, Ingredient ingredient, List<MobEffectInstance> effect) {
        super(foodGained, trustGained, ingredient);
        this.effects = effect;
    }

    public static MapCodec<EffectDragonFoodComponent> codec() {
        return RecordCodecBuilder.mapCodec((instance) -> instance.group(
                IntProviders.CODEC.fieldOf("food_gained").forGetter(DragonFoodComponent::getFoodGained),
                FloatProviders.CODEC.fieldOf("trust_gained").forGetter(DragonFoodComponent::getTrustGained),
                Ingredient.CODEC.fieldOf("ingredient").forGetter(DragonFoodComponent::getIngredient),
                MobEffectInstance.CODEC.listOf().fieldOf("effects").forGetter(EffectDragonFoodComponent::getEffects)
        ).apply(instance, EffectDragonFoodComponent::new));
    }

    public List<MobEffectInstance> getEffects() {
        return effects;
    }

    @Override
    public DragonFoodComponentType<?> type() {
        return ModDragonFoodComponentTypes.EFFECT_FOOD_COMPONENT.get();
    }

    @Override
    public void onEat(Dragon dragon) {
        super.onEat(dragon);
        for (MobEffectInstance effect : effects) {
            dragon.addEffect(effect);
        }
    }
}
