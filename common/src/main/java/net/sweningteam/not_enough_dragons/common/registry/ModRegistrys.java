package net.sweningteam.not_enough_dragons.common.registry;

import net.minecraft.core.Registry;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;

public class ModRegistrys {

    private static <T> ResourceKey<Registry<T>> createRegistryKey(final String name) {
        return ResourceKey.createRegistryKey(Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID,name));
    }}
