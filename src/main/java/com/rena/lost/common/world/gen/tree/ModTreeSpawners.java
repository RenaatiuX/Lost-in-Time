package com.rena.lost.common.world.gen.tree;

import com.rena.lost.common.world.gen.LostConfiguredFeatures;
import net.minecraft.world.gen.feature.ConfiguredFeature;

import javax.annotation.Nullable;
import java.util.Random;

public class ModTreeSpawners {

    public static final TreeSpawner ARAUCARIOXYLON = new TreeSpawner() {
        @Nullable
        public ConfiguredFeature<ModTreeConfig, ?> getTreeFeature(Random random) {
            return LostConfiguredFeatures.ARAUCARIOXYLON;
        }
    };

}
