package net.sweningteam.not_enough_dragons.fabric.datagen.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.EntityType;
import net.sweningteam.not_enough_dragons.common.registry.ModTags;

import java.util.concurrent.CompletableFuture;

public class ModEntityTagProvider extends FabricTagsProvider.EntityTypeTagsProvider {
    public ModEntityTagProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    private Identifier getKey(EntityType entity){
        return BuiltInRegistries.ENTITY_TYPE.getKey(entity);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateRawBuilder(ModTags.EntityTypes.NIGHT_FURY_GETS_ANGRY_AT)
                .addOptionalTag(EntityTypeTags.RAIDERS.location());
    }
}
