package net.sweningteam.fabric.datagen.data.tags;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.PoiTypeTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.sweningteam.common.registry.ModVillagers;

import java.util.concurrent.CompletableFuture;

public class ModPoiTypeTags extends FabricTagProvider<PoiType> {

    public ModPoiTypeTags(FabricDataOutput output, CompletableFuture registriesFuture) {
        super(output, Registries.POINT_OF_INTEREST_TYPE, registriesFuture);
    }

    private ResourceLocation getKey(PoiType poiType){
        return BuiltInRegistries.POINT_OF_INTEREST_TYPE.getKey(poiType);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        getOrCreateRawBuilder(PoiTypeTags.ACQUIRABLE_JOB_SITE)
                .addElement(ModVillagers.VIKING_POI_KEY.location());
    }
}
