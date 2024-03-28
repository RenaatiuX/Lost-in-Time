package com.rena.lost.common.world.gen;

import com.google.common.collect.ImmutableList;
import com.rena.lost.LostInTime;
import com.rena.lost.common.world.gen.tree.ModTreeConfig;
import com.rena.lost.core.init.BlockInit;
import com.rena.lost.core.init.FeatureInit;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockplacer.DoublePlantBlockPlacer;
import net.minecraft.world.gen.blockplacer.SimpleBlockPlacer;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BlobFoliagePlacer;
import net.minecraft.world.gen.placement.AtSurfaceWithExtraConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.SquarePlacement;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class LostConfiguredFeatures {
    public static final Supplier<ConfiguredFeature<?, ?>> PATCH_DUCKWEED = () -> Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(BlockInit.DUCKWEED.get().getDefaultState()), SimpleBlockPlacer.PLACER))
            .tries(10).build()).withPlacement(Features.Placements.PATCH_PLACEMENT).count(10);
    public static final Supplier<ConfiguredFeature<?, ?>> PATCH_QUILLWORT_1 = () -> Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(BlockInit.QUILLWORT_1.get().getDefaultState()), new DoublePlantBlockPlacer()
    )).replaceable().tries(64).preventProjection().build()).withPlacement(Features.Placements.HEIGHTMAP_SPREAD_DOUBLE_PLACEMENT).count(50);
    public static final Supplier<ConfiguredFeature<?, ?>> PATCH_QUILLWORT_2 = () -> Feature.RANDOM_PATCH.withConfiguration((new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(BlockInit.QUILLWORT_2.get().getDefaultState()), new DoublePlantBlockPlacer()
    )).replaceable().tries(64).preventProjection().build()).withPlacement(Features.Placements.HEIGHTMAP_SPREAD_DOUBLE_PLACEMENT).count(100);
    public static final Supplier<ConfiguredFeature<?, ?>> DISK_MUD = () -> Feature.DISK.withConfiguration(new SphereReplaceConfig(BlockInit.MUD.get().getDefaultState(),
            FeatureSpread.create(4, 2), 1,
            ImmutableList.of(Blocks.DIRT.getDefaultState(), BlockInit.MUD.get().getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);
    public static final Supplier<ConfiguredFeature<?, ?>> WEICHSELIA = () -> Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(BlockInit.WEICHSELIA.get().getDefaultState()), new DoublePlantBlockPlacer())
                    .tries(16).preventProjection().build()).withPlacement(Features.Placements.VEGETATION_PLACEMENT)
            .withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).count(7);
    public static final Supplier<ConfiguredFeature<?, ?>> ARCHAEFRUCTUS = () -> Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(
                    new SimpleBlockStateProvider(BlockInit.ARCHAEFRUCTUS.get().getDefaultState()), new SimpleBlockPlacer())
                    .replaceable().tries(64).preventProjection().build()).withPlacement(Features.Placements.HEIGHTMAP_SPREAD_DOUBLE_PLACEMENT)
            .count(7);
    public static final Supplier<ConfiguredFeature<?, ?>> CLODOPHLEBIS = () -> Feature.RANDOM_PATCH.withConfiguration(new BlockClusterFeatureConfig.Builder(
            new SimpleBlockStateProvider(BlockInit.CLADOPHLEBIS.get().getDefaultState()), SimpleBlockPlacer.PLACER)
            .replaceable().tries(32).build()).withPlacement(Features.Placements.PATCH_PLACEMENT).count(10);
    public static final Supplier<ConfiguredFeature<?, ?>> BORDER_MUD = () -> Feature.DISK.withConfiguration(
            new SphereReplaceConfig(BlockInit.MUD.get().getDefaultState(),
                    FeatureSpread.create(4, 2), 1, ImmutableList.of(Blocks.DIRT.getDefaultState(),
                    Blocks.GRASS_BLOCK.getDefaultState(), Blocks.COARSE_DIRT.getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT).count(5);
    public static final Supplier<ConfiguredFeature<?, ?>> BROWN_CLAY = () -> Feature.DISK.withConfiguration(
            new SphereReplaceConfig(BlockInit.BROWN_CLAY.get().getDefaultState(),
                    FeatureSpread.create(2, 1), 1, ImmutableList.of(Blocks.DIRT.getDefaultState(),
                    BlockInit.BROWN_CLAY.get().getDefaultState()))).withPlacement(Features.Placements.SEAGRASS_DISK_PLACEMENT);

    public static final Supplier<ConfiguredFeature<?, ?>> VOIDITE_PILLAR = () -> FeatureInit.VOIDITE_PILLAR.get()
            .withConfiguration(IFeatureConfig.NO_FEATURE_CONFIG);

    public static final Supplier<ConfiguredFeature<ModTreeConfig, ?>> ARAUCARIOXYLON = () -> FeatureInit.ARAUCARIOXYLON.get().withConfiguration(
            new ModTreeConfig.Builder().setTrunkBlock(BlockInit.ARAUCARIOXYLON_LOG.get()).setLeavesBlock(BlockInit.ARAUCARIOXYLON_LEAVES.get()).setMaxHeight(35).setMinHeight(34).build());
    public static final Supplier<ConfiguredFeature<?, ?>> ARAUCARIOXYLON_TREE = () -> ARAUCARIOXYLON.get().withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).withPlacement(Placement.COUNT_EXTRA.configure(
            new AtSurfaceWithExtraConfig(1, 0.3F, 2))).withPlacement(Features.Placements.HEIGHTMAP_PLACEMENT).count(4);

    public static void withMarshVegetation(BiomeGenerationSettings.Builder builder) {
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, LostConfiguredFeatures.WEICHSELIA.get());
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, LostConfiguredFeatures.PATCH_DUCKWEED.get());
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, LostConfiguredFeatures.PATCH_QUILLWORT_1.get());
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, LostConfiguredFeatures.PATCH_QUILLWORT_2.get());
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, LostConfiguredFeatures.ARCHAEFRUCTUS.get());
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, LostConfiguredFeatures.CLODOPHLEBIS.get());
    }

    public static void withDisk(BiomeGenerationSettings.Builder builder) {
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, LostConfiguredFeatures.DISK_MUD.get());
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, LostConfiguredFeatures.BORDER_MUD.get());
        builder.withFeature(GenerationStage.Decoration.UNDERGROUND_ORES, LostConfiguredFeatures.BROWN_CLAY.get());
    }

    private static <FC extends IFeatureConfig> ConfiguredFeature<FC, ?> register(String name, ConfiguredFeature<FC, ?> feature) {
        return Registry.register(WorldGenRegistries.CONFIGURED_FEATURE, new ResourceLocation(LostInTime.MOD_ID, name), feature);
    }
}
