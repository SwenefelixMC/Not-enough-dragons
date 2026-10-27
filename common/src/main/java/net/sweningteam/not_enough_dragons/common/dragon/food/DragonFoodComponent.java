package net.sweningteam.not_enough_dragons.common.dragon.food;

import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.codec.RegistryCodecs;
import net.minecraft.util.valueproviders.FloatProvider;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.crafting.Ingredient;
import net.sweningteam.not_enough_dragons.common.entity.dragons.base.Dragon;
import net.sweningteam.not_enough_dragons.common.registry.ModBuildInRegistries;
import net.sweningteam.not_enough_dragons.common.registry.ModRegistries;

public abstract class DragonFoodComponent {
    private final IntProvider foodGained;
    private final FloatProvider trustGained;
    private final Ingredient ingredient;

    public static final Codec<DragonFoodComponent> DIRECT_CODEC = ModBuildInRegistries.DRAGON_FOOD_COMPONENT_TYPE.byNameCodec().dispatch(DragonFoodComponent::type, DragonFoodComponentType::codec);
    public static final Codec<Holder<DragonFoodComponent>> CODEC = RegistryCodecs.holder(ModRegistries.DRAGON_FOOD_COMPONENT,DIRECT_CODEC);

    public DragonFoodComponent(IntProvider foodGained, FloatProvider trustGained, Ingredient ingredient) {
        this.foodGained = foodGained;
        this.trustGained = trustGained;
        this.ingredient = ingredient;
    }

    public IntProvider getFoodGained() {
        return foodGained;
    }
    public FloatProvider getTrustGained() {
        return trustGained;
    }
    public Ingredient getIngredient() {
        return ingredient;
    }

    public abstract DragonFoodComponentType<?> type();

    public void onEat(Dragon dragon) {

    }
}
