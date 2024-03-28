package com.rena.lost.common.biome.mesozoic;

import com.rena.lost.common.biome.LostBiome;
import com.rena.lost.common.world.surfacebuilder.LostConfiguredSurfaceBuilder;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.Features;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;

public class MesozoicMarsh extends LostBiome {

    private static final Biome.RainType PRECIPITATION = Biome.RainType.RAIN;
    private static final Biome.Category CATEGORY = Biome.Category.SWAMP;
    private static final float TEMPERATURE = 0.8F;
    private static final float DOWNFALL = 0.7F;
    private static final int WATER_COLOR = 6144441;
    private static final int WATER_FOG_COLOR = 3048880;
    private static final int FOG_COLOR = 1263846;
    private static final float SCALE = 0.1F;
    private static final float DEPTH = -0.12F;
    private static final Biome.Climate WEATHER = new Biome.Climate(PRECIPITATION, TEMPERATURE, Biome.TemperatureModifier.NONE, DOWNFALL);
    private static final MobSpawnInfo.Builder SPAWN_SETTINGS = new MobSpawnInfo.Builder().isValidSpawnBiomeForPlayer();
    private static final BiomeGenerationSettings.Builder GENERATION_SETTINGS = (new BiomeGenerationSettings.Builder()).withSurfaceBuilder(LostConfiguredSurfaceBuilder.MARSH::get);
    public MesozoicMarsh() {
        super(WEATHER, CATEGORY, DEPTH, SCALE, (new BiomeAmbience.Builder()).setWaterColor(WATER_COLOR).setWaterFogColor(WATER_FOG_COLOR)
                .withSkyColor(getSkyColorWithTemperatureModifier(TEMPERATURE)).setFogColor(FOG_COLOR).build(), GENERATION_SETTINGS.build(), SPAWN_SETTINGS.build(), false);
    }

    @Override
    public BiomeManager.BiomeType getBiomeType() {
        return super.getBiomeType();
    }

    @Override
    public int getWeight() {
        return 2;
    }

    @Override
    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.WET, BiomeDictionary.Type.SWAMP};
    }

    static {
        DefaultBiomeFeatures.withCavesAndCanyons(GENERATION_SETTINGS);
        DefaultBiomeFeatures.withLavaAndWaterLakes(GENERATION_SETTINGS);
        GENERATION_SETTINGS.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, Features.SEAGRASS_SWAMP);
        //LostConfiguredFeatures.withMarshVegetation(GENERATION_SETTINGS);
        //LostConfiguredFeatures.withDisk(GENERATION_SETTINGS);
    }
}
