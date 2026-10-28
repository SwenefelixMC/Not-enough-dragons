package net.sweningteam.not_enough_dragons.common.structure.viking_trader_ship;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Util;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.RuinedPortalPiece;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.sweningteam.not_enough_dragons.NotEnoughDragons;
import net.sweningteam.not_enough_dragons.common.registry.ModStructureTypes;

import java.util.Optional;

public class VikingTraderShipStructure extends Structure {
    final VikingTraderShipConfig Config;
    public static final MapCodec<VikingTraderShipStructure> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            settingsCodec(instance),
            VikingTraderShipConfig.CODEC.fieldOf("config").forGetter((s)->s.Config)

    ).apply(instance, VikingTraderShipStructure::new));
    public VikingTraderShipStructure(StructureSettings settings, VikingTraderShipConfig config) {
        super(settings);
        this.Config = config;
    }

    @Override
    protected Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        WorldgenRandom random = context.random();
        Identifier templateLocation = Identifier.fromNamespaceAndPath(NotEnoughDragons.MOD_ID, "viking_trader_ship");
        StructureTemplate template = context.structureTemplateManager().getOrCreate(templateLocation);
        Rotation rotation = (Rotation) Util.getRandom(Rotation.values(), random);
        Mirror mirror = random.nextFloat() < 0.5F ? Mirror.NONE : Mirror.FRONT_BACK;
        BlockPos pivot = new BlockPos(template.getSize().getX() / 2, 0, template.getSize().getZ() / 2);
        ChunkGenerator chunkGenerator = context.chunkGenerator();
        BlockPos basePosition = context.chunkPos().getWorldPosition();
        int Height = chunkGenerator.getSeaLevel()-2;
        LevelHeightAccessor heightAccessor = context.heightAccessor();
        RandomState randomState = context.randomState();
        BoundingBox boundingBox = template.getBoundingBox(basePosition, rotation, pivot, mirror);
        BlockPos center = boundingBox.getCenter();
        int surfaceY = chunkGenerator.getBaseHeight(center.getX(), center.getZ(), Heightmap.Types.OCEAN_FLOOR, heightAccessor, randomState) - 1;
        BlockPos origin;
        if(surfaceY < Height) {
            origin = new BlockPos(basePosition.getX(), Height, basePosition.getZ());
        }else{
            origin = new BlockPos(basePosition.getX(), surfaceY, basePosition.getZ());
        }
        return Optional.of(new Structure.GenerationStub(origin,
                (builder) -> {
            builder.addPiece(new VikingTraderShipPiece(
                    context.registryAccess(),
                    context.structureTemplateManager(),
                    templateLocation,
                    origin,
                    rotation,
                    mirror,
                    pivot));
                }));
    }
    @Override
    public StructureType<?> type() {
        return ModStructureTypes.VIKING_TRADER_SHIP_TYPE.get();
    }
}
