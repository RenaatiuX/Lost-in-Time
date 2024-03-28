package com.rena.lost.common.biome.mesozoic;

import com.rena.lost.common.biome.LostBiome;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilders;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

public class AraucarioxylonForest extends LostBiome {
    private static final ConfiguredSurfaceBuilder<?> SURFACE_BUILDER = ConfiguredSurfaceBuilders.GRASS;
    private static final Biome.RainType PRECIPITATION = Biome.RainType.RAIN;
    private static final Biome.Category CATEGORY = Biome.Category.FOREST;
    private static final float TEMPERATURE = 0.6F;
    private static final float DOWNFALL = 0.8F;
    private static final int WATER_COLOR = 4159204;
    private static final int WATER_FOG_COLOR = 329011;
    private static final int FOG_COLOR = 12638463;
    private static final float SCALE = 0.2F;
    private static final float DEPTH = 0.1F;
    private static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    private static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
    private static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(SURFACE_BUILDER);
    public AraucarioxylonForest() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(WATER_COLOR).setWaterFogColor(WATER_FOG_COLOR)
                .withSkyColor(getSkyColorWithTemperatureModifier(TEMPERATURE)).setFogColor(FOG_COLOR).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build(), false);
    }

    @Override
    public BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.COOL;
    }

    @Override
    public int getWeight() {
        return 7;
    }

    @Override
    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.FOREST};
    }

    static {
        DefaultBiomeFeatures.withCavesAndCanyons(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withLavaAndWaterLakes(GENERATION_SETTINGS);
    }
}

