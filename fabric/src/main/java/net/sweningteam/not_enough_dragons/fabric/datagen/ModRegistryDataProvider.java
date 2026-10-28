package net.sweningteam.not_enough_dragons.fabric.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.sweningteam.not_enough_dragons.common.registry.ModRegistries;

import java.util.concurrent.CompletableFuture;

public class ModRegistryDataProvider extends FabricDynamicRegistryProvider {
    public ModRegistryDataProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(HolderLookup.Provider provider, Entries entries) {
        entries.addAll(provider.lookupOrThrow(Registries.VILLAGER_TRADE));
        entries.addAll(provider.lookupOrThrow(Registries.TRADE_SET));
        entries.addAll(provider.lookupOrThrow(Registries.FEATURE));
        entries.addAll(provider.lookupOrThrow(Registries.PLACED_FEATURE));
        entries.addAll(provider.lookupOrThrow(Registries.STRUCTURE));
        entries.addAll(provider.lookupOrThrow(Registries.TEMPLATE_POOL));
        entries.addAll(provider.lookupOrThrow(Registries.STRUCTURE_SET));
        entries.addAll(provider.lookupOrThrow(ModRegistries.DRAGON_FOOD_COMPONENT));
    }

    @Override
    public String getName() {
        return "Not Enough Dragons Data Provider";
    }
}
