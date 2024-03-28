package com.rena.lost.common.world.dimension;

import com.rena.lost.core.init.BiomeInit;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.IAreaTransformer0;

public class LostBiomeLayer implements IAreaTransformer0 {

    private static final int UNCOMMON_BIOME_CHANCE = 8;
    private static final int RARE_BIOME_CHANCE = 16;

    public LostBiomeLayer() {

    }

    protected int[] commonBiomes = new int[]{
            LostLayerUtil.getBiomeId(BiomeInit.ARAUCARIOXYLON_FOREST_BIOME.getKey())
    };

    protected int[] uncommonBiomes = new int[]{
            LostLayerUtil.getBiomeId(BiomeInit.MESOZOIC_MARSH_BIOME.getKey())
    };

    protected int[] rareBiomes = new int[]{
            LostLayerUtil.getBiomeId(BiomeInit.MESOZOIC_OCEAN_BIOME.getKey())
    };

    @Override
    public int apply(INoiseRandom iNoiseRandom, int i, int i1) {
        if (iNoiseRandom.random(RARE_BIOME_CHANCE) == 0) {
            return rareBiomes[iNoiseRandom.random(rareBiomes.length)];
        } else if (iNoiseRandom.random(UNCOMMON_BIOME_CHANCE) == 0) {
            return uncommonBiomes[iNoiseRandom.random(uncommonBiomes.length)];
        } else {
            return commonBiomes[iNoiseRandom.random(commonBiomes.length)];
        }
    }
}
