package com.rena.lost.common.biome;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static net.minecraftforge.common.BiomeDictionary.Type.*;

public class LostBiome {

    public static final List<LostBiome> PF_BIOMES = new ArrayList<>();
    private final Biome biome;
    private boolean isSnowy;
    public static List<BiomeData> biomeData = new ArrayList<>();

    public LostBiome(Biome.Climate climate, Biome.Category category, float depth, float scale, BiomeAmbience effects,
                     BiomeGenerationSettings biomeGenerationSettings, MobSpawnInfo mobSpawnInfo, boolean isSnowy) {
        biome = new Biome(climate, category, depth, scale, effects, biomeGenerationSettings, mobSpawnInfo);
        this.isSnowy = isSnowy;
        PF_BIOMES.add(this);
    }

    public LostBiome(Biome.Builder builder) {
        this.biome = builder.build();
        PF_BIOMES.add(this);
    }

    public LostBiome(Biome biome) {
        this.biome = biome;
        PF_BIOMES.add(this);
    }

    public Biome getBiome() {
        return this.biome;
    }

    public Biome getRiver() {
        if (isSnowy) {
            return WorldGenRegistries.BIOME.getOrThrow(Biomes.FROZEN_RIVER);
        } else {
            return WorldGenRegistries.BIOME.getOrThrow(Biomes.RIVER);
        }
    }

    @Nullable
    public Biome getBeach() {
        if (isSnowy) {
            return WorldGenRegistries.BIOME.getOrThrow(Biomes.SNOWY_BEACH);
        } else {
            return WorldGenRegistries.BIOME.getOrThrow(Biomes.BEACH);
        }
    }

    public BiomeDictionary.Type[] getBiomeDictionary() {
        return new BiomeDictionary.Type[]{BiomeDictionary.Type.OVERWORLD};
    }

    public BiomeManager.BiomeType getBiomeType() {
        return BiomeManager.BiomeType.WARM;
    }

    public int getWeight() {
        return 5;
    }

    public RegistryKey<Biome> getKey() {
        return RegistryKey.getOrCreateKey(Registry.BIOME_KEY, Objects.requireNonNull(WorldGenRegistries.BIOME.getKey(this.biome)));
    }

    public static int getSkyColorWithTemperatureModifier(float temperature) {
        float lvt_1_1_ = temperature / 3.0F;
        lvt_1_1_ = MathHelper.clamp(lvt_1_1_, -1.0F, 1.0F);
        return MathHelper.hsvToRGB(0.62222224F - lvt_1_1_ * 0.05F, 0.5F + lvt_1_1_ * 0.1F, 1.0F);
    }
}
