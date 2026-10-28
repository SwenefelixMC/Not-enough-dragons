package net.sweningteam.not_enough_dragons.common.structure.viking_trader_ship;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.templatesystem.*;
import net.sweningteam.not_enough_dragons.common.registry.ModStructurPieceTypes;

import java.util.List;

public class VikingTraderShipPiece extends TemplateStructurePiece {
    public VikingTraderShipPiece(final HolderLookup.Provider registries, StructureTemplateManager structureTemplateManager, Identifier templateLocation, BlockPos position, final Rotation rotation, final Mirror mirror, final BlockPos pos) {
        super(ModStructurPieceTypes.VIKING_TRADER_SHIP_PICE.get(), 0, structureTemplateManager, templateLocation, templateLocation.toString(), makeSettings(registries,mirror,rotation, pos), position);
    }

    public VikingTraderShipPiece(StructurePieceSerializationContext context, CompoundTag tag) {
        super(ModStructurPieceTypes.VIKING_TRADER_SHIP_PICE.get(), tag, context.structureTemplateManager(),(location) -> makeSettings(context.registryAccess(), context.structureTemplateManager(), tag, location));
    }

    private static StructurePlaceSettings makeSettings(final HolderLookup.Provider registries, final StructureTemplateManager structureTemplateManager, final CompoundTag tag, final Identifier location) {
        StructureTemplate template = structureTemplateManager.getOrCreate(location);
        BlockPos pivot = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
        return makeSettings(registries, (Mirror)tag.read("Mirror", Mirror.CODEC).orElseThrow(), (Rotation)tag.read("Rotation", Rotation.CODEC).orElseThrow(), pivot);
    }

    private static StructurePlaceSettings makeSettings(final HolderLookup.Provider registries, final Mirror mirror, final Rotation rotation, final BlockPos pivot) {
        HolderLookup.RegistryLookup<Block> blocks = registries.lookupOrThrow(Registries.BLOCK);
        List<ProcessorRule> rules = Lists.newArrayList();

        return (new StructurePlaceSettings()).setRotation(rotation).setMirror(mirror).setRotationPivot(pivot).addProcessor(BlockIgnoreProcessor.STRUCTURE_BLOCK).addProcessor(new RuleProcessor(rules)).addProcessor(new ProtectedBlockProcessor(blocks.getOrThrow(BlockTags.FEATURES_CANNOT_REPLACE))).addProcessor(new LavaSubmergedBlockProcessor());
    }

    @Override
    protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
        super.addAdditionalSaveData(context, tag);
        tag.store("Rotation", Rotation.CODEC, this.placeSettings.getRotation());
        tag.store("Mirror", Mirror.CODEC, this.placeSettings.getMirror());
    }

    @Override
    protected void handleDataMarker(String markerId, BlockPos position, ServerLevelAccessor level, RandomSource random, BoundingBox chunkBB) {

    }
}
