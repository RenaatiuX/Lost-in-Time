package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import com.rena.lost.common.world.gen.feature.VoiditePeaksFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FeatureInit {

    public static final DeferredRegister<Feature<?>> FEATURE = DeferredRegister.create(ForgeRegistries.FEATURES, LostInTime.MOD_ID);

    public static final RegistryObject<Feature<NoFeatureConfig>> VOIDITE_PILLAR = FEATURE.register("voidite_pillar",
            () -> new VoiditePeaksFeature(NoFeatureConfig.CODEC));

}
