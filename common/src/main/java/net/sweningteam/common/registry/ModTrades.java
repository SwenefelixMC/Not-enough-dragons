package net.sweningteam.common.registry;

import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;

import java.util.ArrayList;
import java.util.List;

public class ModTrades {
    public ModTrades(){
        registry.add(new TradeEntry(
                VillagerProfession.LIBRARIAN,
                5,
                new MerchantOffer(
                        new ItemCost(ModItems.GOLD_COIN.get(),3),
                        new ItemStack(ModBlocks.BOOK_OF_DRAGONS.get().asItem(),1),
                        1,
                        4,
                        0.05f)
        ));
    }

    public static List<TradeEntry> registry = new ArrayList<>();


    public record TradeEntry(ResourceKey<VillagerProfession> profession,
                             int level,
                             MerchantOffer merchantOffer
                             ){

    }
}
