package com.rena.lost.common.biome.mesozoic;

import com.rena.lost.common.biome.LostBiome;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

public class MesozoicRiver extends LostBiome {

    private static final ConfiguredSurfaceBuilder<?> SURFACE_BUILDER = ConfiguredSurfaceBuilders.GRASS;
    private static final Biome.RainType PRECIPITATION = Biome.RainType.RAIN;
    private static final Biome.Category CATEGORY = Biome.Category.RIVER;
    private static final float TEMPERATURE = 0.5F;
    private static final float DOWNFALL = 0.5F;
    private static final int WATER_COLOR = 4159204;
    private static final int WATER_FOG_COLOR = 329011;
    private static final int FOLIAGE_COLOR = 0x53711f;
    private static final int FOG_COLOR = 1263846;
    private static final float SCALE = 0.0F;
    private static final float DEPTH = -0.5F;

    private static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    private static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
    private static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(SURFACE_BUILDER);

    public MesozoicRiver() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(WATER_COLOR).setWaterFogColor(WATER_FOG_COLOR).
                withSkyColor(getSkyColorWithTemperatureModifier(TEMPERATURE)).setFogColor(FOG_COLOR).withFoliageColor(FOLIAGE_COLOR).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build(), false);
    }

    @Override
    public Biome getRiver() {
        return super.getRiver();
    }

    @Override
    public BiomeDictionary.Type[] getBiomeDictionary() {
        return super.getBiomeDictionary();
    }

    static {
        DefaultBiomeFeatures.withCavesAndCanyons(GENERATION_SETTINGS);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_RIVER);
    }
}
