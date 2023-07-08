package com.rena.lost.common.world.gen.feature;

import com.mojang.serialization.Codec;
import com.rena.lost.core.init.BlockInit;
import net.minecraft.block.BlockState;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;

public class VoiditePeaksFeature extends Feature<NoFeatureConfig> {
    private static final Direction[] DIRECTIONS = Direction.values();
    public VoiditePeaksFeature(Codec<NoFeatureConfig> codec) {
        super(codec);
    }

    @Override
    public boolean generate(ISeedReader reader, ChunkGenerator generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int height = rand.nextInt(6) + 2;
        BlockPos topPos = pos.up(height);
        for (BlockPos columnPos : BlockPos.getAllInBoxMutable(pos, topPos)) {
            reader.setBlockState(columnPos, BlockInit.VOIDITE_PILLAR.get().getDefaultState(), 2);
        }

        reader.setBlockState(topPos, BlockInit.VOIDITE_CRYSTAL.get().getDefaultState(), 2);

        for (int i = 0; i < height; i++) {
            int sideBlock = rand.nextInt(3) + 1;
            for (int j = 0; j < sideBlock; j++) {
                //defining the horizontal direction in which the crystal might spawn
                Direction sideDirection = Direction.values()[rand.nextInt(Direction.values().length)];
                BlockPos crystalPos = pos.offset(sideDirection).add(0, i, 0);
                if (reader.isAirBlock(crystalPos)) {
                    //goin back in opposite direction will make give us the pillarPos
                    BlockPos pillarPos = crystalPos.offset(sideDirection.getOpposite());
                    //checking whether the pillar has a full face there
                    if (reader.getBlockState(pillarPos).isSolidSide(reader, pillarPos, sideDirection.getOpposite())) {
                        //spawwning the crystal with the correct facing, always away from the pillar
                        BlockState sideBlockState = BlockInit.VOIDITE_CRYSTAL.get().getDefaultState().with(BlockStateProperties.FACING, sideDirection);
                        reader.setBlockState(crystalPos, sideBlockState, 2);
                    }
                }
            }
        }
        return true;
    }
}
