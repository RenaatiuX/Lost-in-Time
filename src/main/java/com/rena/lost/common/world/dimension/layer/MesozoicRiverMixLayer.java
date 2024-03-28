package com.rena.lost.common.world.dimension.layer;

import com.rena.lost.common.world.dimension.LostLayerUtil;
import com.rena.lost.core.init.BiomeInit;
import net.minecraft.world.gen.INoiseRandom;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.layer.traits.IAreaTransformer2;
import net.minecraft.world.gen.layer.traits.IDimOffset0Transformer;

public enum MesozoicRiverMixLayer implements IAreaTransformer2, IDimOffset0Transformer {
    INSTANCE;

    MesozoicRiverMixLayer() {

    }

    @Override
    public int apply(INoiseRandom iNoiseRandom, IArea iArea, IArea iArea1, int i, int i1) {
        int val1 = iArea.getValue(this.getOffsetX(i), this.getOffsetZ(i1));
        int val2 = iArea1.getValue(this.getOffsetX(i), this.getOffsetZ(i1));

        if (val2 == LostLayerUtil.getBiomeId(BiomeInit.MESOZOIC_RIVER_BIOME.getKey())) {
            return val2;
        } else {
            return val1;
        }
    }
}
