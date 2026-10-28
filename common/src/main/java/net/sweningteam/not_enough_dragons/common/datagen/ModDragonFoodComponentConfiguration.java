package net.sweningteam.not_enough_dragons.common.datagen;

import com.google.common.collect.ImmutableList;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.valueproviders.ConstantFloat;
import net.minecraft.util.valueproviders.ConstantInt;
import net.minecraft.util.valueproviders.UniformFloat;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.sweningteam.not_enough_dragons.common.dragon.food.DragonFoodComponent;
import net.sweningteam.not_enough_dragons.common.dragon.food.custom.EffectDragonFoodComponent;
import net.sweningteam.not_enough_dragons.common.dragon.food.custom.StandardDragonFoodComponent;
import net.sweningteam.not_enough_dragons.common.registry.ModDragonFoodComponents;

public class ModDragonFoodComponentConfiguration {
    public static void bootstrap(BootstrapContext<DragonFoodComponent> context) {
        context.register(
                ModDragonFoodComponents.NIGHT_FURY_COD,
                new StandardDragonFoodComponent(new UniformInt(4,6), new UniformFloat(1.5F,2.5F), Ingredient.of(Items.COD))
        );
        context.register(
                ModDragonFoodComponents.NIGHT_FURY_SALMON,
                new StandardDragonFoodComponent(new UniformInt(6,8), new UniformFloat(2.5F,3.5F), Ingredient.of(Items.SALMON))
        );
        context.register(
                ModDragonFoodComponents.NIGHT_FURY_TROPICAL_FISH,
                new StandardDragonFoodComponent(new UniformInt(2,4), new ConstantFloat(1F), Ingredient.of(Items.TROPICAL_FISH))
        );
        context.register(
                ModDragonFoodComponents.NIGHT_FURY_PUFFER_FISH,
                new EffectDragonFoodComponent(new ConstantInt(1), new ConstantFloat(-5F),Ingredient.of(Items.PUFFERFISH), ImmutableList.of(new MobEffectInstance(MobEffects.POISON,400)))
        );
        context.register(
                ModDragonFoodComponents.NIGHT_FURY_COOKED_COD,
                new StandardDragonFoodComponent(new UniformInt(9,11), new UniformFloat(3.5F,4.5F), Ingredient.of(Items.COOKED_COD))
        );
        context.register(
                ModDragonFoodComponents.NIGHT_FURY_COOKED_SALMON,
                new StandardDragonFoodComponent(new UniformInt(13,15), new UniformFloat(5.5f,6.5F), Ingredient.of(Items.COOKED_SALMON))
        );
    }
}
