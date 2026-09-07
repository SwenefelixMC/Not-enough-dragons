package net.sweningteam.common.registry;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.StructureTags;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.saveddata.maps.MapDecorationType;
import net.minecraft.world.level.saveddata.maps.MapDecorationTypes;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import net.sweningteam.NotEnoughDragons;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ModVillagerTrades {

    public static void init(){
        NotEnoughDragons.LOGGER.info("Registering Villager Trades for {}",NotEnoughDragons.MOD_NAME);
    }

    public static final Int2ObjectMap<ItemListing[]> VIKING_TRADER_TRADES = toIntMap(ImmutableMap.of(
            1,new ItemListing[]{
            new InputForOutput(ModItems.GOLD_COIN.get(),Items.DIAMOND, 1,10,2,2),
            new InputForOutput(Items.SADDLE,ModItems.GOLD_COIN.get(), 2,2,2,1),
            new InputForOutput(Items.NAME_TAG, ModItems.GOLD_COIN.get(),  1,5,2,1),
            new InputForOutput(ModItems.GOLD_COIN.get(),Items.IRON_INGOT,5,10,2,1)
    },2, new ItemListing[]{
            new InputForOutput(Items.MAP,ModItems.GOLD_COIN.get(), 1,5,2,1),
            new TreasureMapForInput(ModItems.GOLD_COIN.get(),20, StructureTags.ON_TREASURE_MAPS,"filled_map.buried_treasure", MapDecorationTypes.TARGET_X,1,5),
            new TreasureMapForInput(ModItems.GOLD_COIN.get(),20, StructureTags.ON_WOODLAND_EXPLORER_MAPS,"filled_map.mansion", MapDecorationTypes.WOODLAND_MANSION,1,5),
            new TreasureMapForInput(ModItems.GOLD_COIN.get(),20, StructureTags.ON_JUNGLE_EXPLORER_MAPS,"filled_map.explorer_jungle", MapDecorationTypes.JUNGLE_TEMPLE,1,5),
            new TreasureMapForInput(ModItems.GOLD_COIN.get(),20, StructureTags.ON_OCEAN_EXPLORER_MAPS,"filled_map.monument", MapDecorationTypes.OCEAN_MONUMENT,1,5),
            new TreasureMapForInput(ModItems.GOLD_COIN.get(),20, StructureTags.ON_SWAMP_EXPLORER_MAPS,"filled_map.explorer_swamp", MapDecorationTypes.OCEAN_MONUMENT,1,5),
            new TreasureMapForInput(ModItems.GOLD_COIN.get(),20, StructureTags.ON_TRIAL_CHAMBERS_MAPS,"filled_map.trial_chambers", MapDecorationTypes.TRIAL_CHAMBERS,1,5),
            new InputForOutput(Items.PAPER,ModItems.GOLD_COIN.get(),  1,5,2,1),
            new InputForOutput(Items.COMPASS,ModItems.GOLD_COIN.get(),  1,5,2,1),
            new InputForOutput(ModBlocks.NIGHT_FURY_SCALES.get(),ModItems.GOLD_COIN.get(),1,10,2,2)
    },3,new ItemListing[]{
            new InputForOutput(ModBlocks.BOOK_OF_DRAGONS.get(),ModItems.GOLD_COIN.get(),  5,1,2,1),
            new InputForOutput(Items.WHEAT,ModItems.GOLD_COIN.get(), 1,20,2,1)
    },4, new ItemListing[]{
            new InputForOutput(Items.COD,ModItems.GOLD_COIN.get(),  1,20,2,2),
            new InputForOutput(Items.SALMON, ModItems.GOLD_COIN.get(),  1,20,2,2)
    },5, new ItemListing[]{
            new InputForOutput( ModItems.NIGHT_FURY_EGG.get(), ModItems.GOLD_COIN.get(),40,1,2,1),
            new InputForOutput( ModItems.GRONCKLE_EGG.get(), ModItems.GOLD_COIN.get(),40,1,2,1)
    }));

    private static Int2ObjectMap<ItemListing[]> toIntMap(ImmutableMap<Integer, ItemListing[]> map) {
        return new Int2ObjectOpenHashMap<>(map);
    }

    public static class InputForOutput implements  ItemListing{
        ItemLike Currency;
        ItemLike Output;
        ItemCost ItemCost;
        int MaxUses;
        int VillagerXP;
        int CurrencyAmount;
        public InputForOutput(ItemLike currency, ItemLike output, int cost, int maxUses, int villagerXp, int currencyAmount){
            this.Currency = currency;
            this.Output = output;
            this.ItemCost = new ItemCost(output.asItem(),cost);
            this.MaxUses = maxUses;
            this.VillagerXP = villagerXp;
            this.CurrencyAmount = currencyAmount;
        }

        @Override
        public @Nullable MerchantOffer getOffer(Entity trader, RandomSource random) {
            return new MerchantOffer(this.ItemCost, new ItemStack(Currency,CurrencyAmount), this.MaxUses,VillagerXP,0.05F);
        }
    }
    public static class TreasureMapForInput implements ItemListing {
        private final int emeraldCost;
        private final TagKey<Structure> destination;
        private final String displayName;
        private final Holder<MapDecorationType> destinationType;
        private final int maxUses;
        private final int villagerXp;
        private final ItemLike Input;

        public TreasureMapForInput(ItemLike input, int emeraldCost, TagKey<Structure> destination, String displayName, Holder<MapDecorationType> destinationType, int maxUses, int villagerXp) {
            this.emeraldCost = emeraldCost;
            this.destination = destination;
            this.displayName = displayName;
            this.destinationType = destinationType;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.Input = input;
        }

        @Nullable
        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            Level var4 = trader.level();
            if (var4 instanceof ServerLevel serverLevel) {
                BlockPos blockPos = serverLevel.findNearestMapStructure(this.destination, trader.blockPosition(), 100, true);
                if (blockPos != null) {
                    ItemStack itemStack = MapItem.create(serverLevel, blockPos.getX(), blockPos.getZ(), (byte)2, true, true);
                    MapItem.renderBiomePreviewMap(serverLevel, itemStack);
                    MapItemSavedData.addTargetDecoration(itemStack, blockPos, "+", this.destinationType);
                    itemStack.set(DataComponents.ITEM_NAME, Component.translatable(this.displayName));
                    return new MerchantOffer(new ItemCost(Input, this.emeraldCost), itemStack, this.maxUses, this.villagerXp, 0.2F);
                } else {
                    return null;
                }
            } else {
                return null;
            }
        }
    }
    public interface ItemListing {
        @Nullable
        MerchantOffer getOffer(Entity trader, RandomSource random);
    }
}
