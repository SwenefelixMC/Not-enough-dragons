package net.sweningteam.not_enough_dragons.common.block;

import com.mojang.serialization.MapCodec;
import net.minecraft.world.level.block.VegetationBlock;

public class PlantBlock extends VegetationBlock {
    public static final MapCodec<PlantBlock> CODEC = simpleCodec(PlantBlock::new);

    public PlantBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected MapCodec<? extends VegetationBlock> codec() {
        return CODEC;
    }
}
