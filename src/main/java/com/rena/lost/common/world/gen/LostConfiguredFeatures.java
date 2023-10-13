package com.rena.lost.common.world.gen;

import com.google.common.collect.ImmutableList;
import com.rena.lost.common.world.gen.tree.ModTreeConfig;
import com.rena.lost.core.init.BlockInit;
import com.rena.lost.core.init.FeatureInit;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class LostConfiguredFeatures {

    public static final ConfiguredFeature<?, ?> PATCH_DUCKWEED = Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(BlockInit.DUCKWEED.get().getDefaultState()), SimpleBlockPlacer.PLACER
    )).tries(10).build()).withPlacement(Features.Placements.PATCH_PLACEMENT).count(10);
    public static final ConfiguredFeature<?, ?> PATCH_QUILLWORT_1 = Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(BlockInit.QUILLWORT_1.get().getDefaultState()), new DoublePlantBlockPlacer()
    )).replaceable().tries(64).preventProjection().build()).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).count(4);
    public static final ConfiguredFeature<?, ?> PATCH_QUILLWORT_2 = Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(BlockInit.QUILLWORT_2.get().getDefaultState()), new DoublePlantBlockPlacer()
    )).replaceable().tries(64).preventProjection().build()).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).count(4);
    public static final ConfiguredFeature<?, ?> DISK_MUD = Feature.DISK.withConfiguration(new SphereReplaceConfig(BlockInit.MUD.get().getDefaultState(),
            FeatureSpread.create(4, 2), 1,
            ImmutableList.of(Blocks.DIRT.getDefaultState(), BlockInit.MUD.get().getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);

    public static final ConfiguredFeature<?, ?> VOIDITE_PILLAR = FeatureInit.VOIDITE_PILLAR.get().withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).count(4);

    public static final ConfiguredFeature<ModTreeConfig, ?> ARAUCARIOXYLON = FeatureInit.ARAUCARIOXYLON.get().withConfiguration(
            new ModTreeConfig.Builder().setTrunkBlock(BlockInit.ARAUCARIOXYLON_LOG.get()).setLeavesBlock(BlockInit.ARAUCARIOXYLON_LEAVES.get()).setMaxHeight(35).setMinHeight(34).build());
    public static final ConfiguredFeature<?, ?> ARAUCARIOXYLON_TREE = ARAUCARIOXYLON.withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(
            new AtSurfaceWithExtraConfig(1, 0.3F, 2)));

}
