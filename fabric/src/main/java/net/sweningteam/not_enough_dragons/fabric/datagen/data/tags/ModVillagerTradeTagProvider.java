package net.sweningteam.not_enough_dragons.fabric.datagen.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.VillagerTradeTags;
import net.minecraft.world.item.trading.VillagerTrade;
import net.sweningteam.not_enough_dragons.common.entity.villager.ModTrades;
import net.sweningteam.not_enough_dragons.common.registry.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModVillagerTradeTagProvider extends FabricTagsProvider<VillagerTrade> {
    public ModVillagerTradeTagProvider(FabricPackOutput output,CompletableFuture<HolderLookup.Provider> registryLookupFuture) {
        super(output, Registries.VILLAGER_TRADE, registryLookupFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        getOrCreateRawBuilder(VillagerTradeTags.TOOLSMITH_LEVEL_3)
                .addElement(ModTrades.WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_AXE.identifier())
                .addElement(ModTrades.WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_SWORD.identifier())
                .addElement(ModTrades.WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_SHOVEL.identifier())
                .addElement(ModTrades.WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_PICKAXE.identifier())
                .addElement(ModTrades.WEAPONSMITH_3_EMERALD_GRONCKLE_IRON_HOE.identifier());

        getOrCreateRawBuilder(ModTags.VillagerTrades.VIKING_TRADER_LEVEL_1)
                .addElement(ModTrades.VIKING_1_DIAMOND_GOLD_COIN.identifier())
                .addElement(ModTrades.VIKING_1_IRON_INGOT_GOLD_COIN.identifier())
                .addElement(ModTrades.VIKING_1_GRONCKLE_IRON_INGOT_GOLD_COIN.identifier())
                .addElement(ModTrades.VIKING_1_DRAGON_PROVED_STEAL_INGOT_GOLD_COIN.identifier())
                .addElement(ModTrades.VIKING_1_GOLD_COIN_SADDLE.identifier())
                .addElement(ModTrades.VIKING_1_GOLD_COIN_NAME_TAG.identifier());

        getOrCreateRawBuilder(ModTags.VillagerTrades.VIKING_TRADER_LEVEL_2)
                .addElement(ModTrades.VIKING_2_GOLD_COIN_MAP.identifier())
                .addElement(ModTrades.VIKING_2_GOLD_COIN_PAPER.identifier())
                .addElement(ModTrades.VIKING_2_GOLD_COIN_COMPASS.identifier())
                .addElement(ModTrades.VIKING_2_GOLD_COIN_GRONCKLE_SCALES.identifier())
                .addElement(ModTrades.VIKING_2_GOLD_COIN_NIGHT_FURY_SCALES.identifier());

        getOrCreateRawBuilder(ModTags.VillagerTrades.VIKING_TRADER_LEVEL_3)
                .addElement(ModTrades.VIKING_3_GOLD_COIN_DRAGON_MANUAL.identifier())
                .addElement(ModTrades.VIKING_3_GOLD_COIN_DRAGON_NIP.identifier())
                .addElement(ModTrades.VIKING_3_GOLD_COIN_GRONCKLE_IRON_BLOCK.identifier())
                .addElement(ModTrades.VIKING_3_GOLD_COIN_DRAGON_PROVED_STEAL_BLOCK.identifier());

        getOrCreateRawBuilder(ModTags.VillagerTrades.VIKING_TRADER_LEVEL_4)
                .addElement(ModTrades.VIKING_4_GOLD_COIN_COD.identifier())
                .addElement(ModTrades.VIKING_4_GOLD_COIN_SALMON.identifier())
                .addElement(ModTrades.VIKING_4_GOLD_COIN_RABBIT_STEW.identifier())
                .addElement(ModTrades.VIKING_4_GOLD_COIN_GRANITE.identifier());

        getOrCreateRawBuilder(ModTags.VillagerTrades.VIKING_TRADER_LEVEL_5)
                .addElement(ModTrades.VIKING_5_GOLD_COIN_GRONCKLE_EGG.identifier())
                .addElement(ModTrades.VIKING_5_GOLD_COIN_NIGHT_FURY_EGG.identifier());
    }
}
