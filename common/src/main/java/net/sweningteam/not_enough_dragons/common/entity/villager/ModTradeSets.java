package net.sweningteam.not_enough_dragons.common.entity.villager;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.trading.TradeSet;
import net.minecraft.world.item.trading.VillagerTrade;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.NumberProvider;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.registry.ModTags;

import java.util.Optional;

public class ModTradeSets {
    public static ResourceKey<TradeSet> VIKING_LEVEL_1 = resourceKey("viking/level_1");
    public static ResourceKey<TradeSet> VIKING_LEVEL_2 = resourceKey("viking/level_2");
    public static ResourceKey<TradeSet> VIKING_LEVEL_3 = resourceKey("viking/level_3");
    public static ResourceKey<TradeSet> VIKING_LEVEL_4 = resourceKey("viking/level_4");
    public static ResourceKey<TradeSet> VIKING_LEVEL_5 = resourceKey("viking/level_5");


    public static void bootstrap(BootstrapContext<TradeSet> context){
        register(context, VIKING_LEVEL_1, ModTags.VillagerTrades.VIKING_TRADER_LEVEL_1);
        register(context, VIKING_LEVEL_2, ModTags.VillagerTrades.VIKING_TRADER_LEVEL_2);
        register(context, VIKING_LEVEL_3, ModTags.VillagerTrades.VIKING_TRADER_LEVEL_3);
        register(context, VIKING_LEVEL_4, ModTags.VillagerTrades.VIKING_TRADER_LEVEL_4);
        register(context, VIKING_LEVEL_5, ModTags.VillagerTrades.VIKING_TRADER_LEVEL_5);
    }

    public static Holder.Reference<TradeSet> register(
            final BootstrapContext<TradeSet> context, final ResourceKey<TradeSet> resourceKey, final TagKey<VillagerTrade> tradeTag
    ) {
        return register(context, resourceKey, tradeTag, ConstantValue.exactly(2.0F));
    }

    public static Holder.Reference<TradeSet> register(
            final BootstrapContext<TradeSet> context, final ResourceKey<TradeSet> resourceKey, final TagKey<VillagerTrade> tradeTag, final NumberProvider numberProvider
    ) {
        return context.register(
                resourceKey,
                new TradeSet(
                        context.lookup(Registries.VILLAGER_TRADE).getOrThrow(tradeTag), numberProvider, false, Optional.of(resourceKey.identifier().withPrefix("trade_set/"))
                )
        );
    }

    public static ResourceKey<TradeSet> resourceKey(final String path) {
        return ResourceKey.create(Registries.TRADE_SET, Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,path));
    }
}
