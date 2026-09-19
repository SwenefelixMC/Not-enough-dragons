package net.sweningteam.not_enough_dragons.fabric.datagen.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.sweningteam.not_enough_dragons.common.registry.ModPoiTypes;

import java.util.concurrent.CompletableFuture;

public class ModPoiTypeTags extends FabricTagsProvider<PoiType> {

    public ModPoiTypeTags(FabricPackOutput output, CompletableFuture registriesFuture) {
        super(output, Registries.POINT_OF_INTEREST_TYPE, registriesFuture);
    }

    private Identifier getKey(PoiType poiType){
        return BuiltInRegistries.POINT_OF_INTEREST_TYPE.getKey(poiType);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateRawBuilder(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .addOptionalElement(ModPoiTypes.VIKING_POI.identifier());
    }
}
