package com.rena.lost.common.world.surfacebuilder.biomesurface;

import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.Random;

public class MarshSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public MarshSurfaceBuilder(Codec<SurfaceBuilderConfig> p_i232136_1_) {
        super(p_i232136_1_);
    }

    @Override
    public void buildSurface(Random random, IChunk chunkIn, Biome biomeIn, int x, int z, int startHeight, double noise, BlockState defaultBlock, BlockState defaultFluid, int seaLevel, long seed, SurfaceBuilderConfig config) {
        double d0 = Biome.INFO_NOISE.noiseAt((double) x * 0.25D, (double) z * 0.25D, false);
        if (d0 > 0.0D) {
            int i = x & 15;
            int j = z & 15;
            BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable();

            for (int k = startHeight; k >= 0; --k) {
                blockpos$mutable.setPos(i, k, j);
                if (!chunkIn.getBlockState(blockpos$mutable).isAir()) {
                    if (k == 62 && chunkIn.getBlockState(blockpos$mutable).getBlock() != defaultFluid.getBlock()) {
                        chunkIn.setBlockState(blockpos$mutable, defaultFluid, false);
                    }
                    break;
                }
            }
        }
        SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, config);
    }
}

