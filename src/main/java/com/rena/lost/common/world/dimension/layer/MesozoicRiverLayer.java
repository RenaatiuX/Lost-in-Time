package com.rena.lost.common.world.dimension.layer;

import com.rena.lost.common.world.dimension.LostLayerUtil;
import com.rena.lost.core.init.BiomeInit;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.layer.traits.ICastleTransformer;

public enum MesozoicRiverLayer implements ICastleTransformer {
    INSTANCE;

    MesozoicRiverLayer() {

    }

    @Override
    public int apply(INoiseRandom iNoiseRandom, int i, int i1, int i2, int i3, int i4) {
        if (shouldRiver(i4, i1, i2, i3, i)) {
            return LostLayerUtil.getBiomeId(BiomeInit.MESOZOIC_RIVER_BIOME.getKey());
        } else {
            return -1;
        }
    }

    boolean shouldRiver(int mid, int left, int down, int right, int up) {
        return shouldRiver(mid, left) || shouldRiver(mid, right) || shouldRiver(mid, down) || shouldRiver(mid, up);
    }

    boolean shouldRiver(int id1, int id2) {
        if (id1 == id2) return false;
        if (id1 == id2) return false;
        return true;
    }
}
