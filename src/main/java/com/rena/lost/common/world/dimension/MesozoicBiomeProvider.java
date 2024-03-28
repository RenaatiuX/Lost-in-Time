package com.rena.lost.common.world.dimension;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.rena.lost.LostInTime;
import com.rena.lost.core.init.BiomeInit;
import jdk.nashorn.internal.ir.annotations.Immutable;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;

import java.util.List;


public class MesozoicBiomeProvider extends BiomeProvider {

    public static final Codec<MesozoicBiomeProvider> CODEC = RecordCodecBuilder.create((instance) -> instance.group(
            Codec.LONG.fieldOf("seed").orElseGet(SeedBearer::giveMeSeed).forGetter((chunkGenerator) -> {
                return chunkGenerator.seed;
            }),
            RegistryLookupCodec.getLookUpCodec(Registry.BIOME_KEY).forGetter((obj) -> obj.registry)
    ).apply(instance, instance.stable(MesozoicBiomeProvider::new)));

    private final long seed;
    private final Registry<Biome> registry;
    private final Layer genBiomes;
    private static final List<RegistryKey<Biome>> biomes = ImmutableList.of(
            BiomeInit.MESOZOIC_RIVER_BIOME.getKey(), BiomeInit.MESOZOIC_MARSH_BIOME.getKey(),
            BiomeInit.ARAUCARIOXYLON_FOREST_BIOME.getKey(), BiomeInit.MESOZOIC_OCEAN_BIOME.getKey());
    protected MesozoicBiomeProvider(long seed, Registry<Biome> registry) {
        super(biomes.stream().map(define -> () -> registry.getOrThrow(define)));
        this.seed = seed;
        this.registry = registry;
        this.genBiomes = LostLayerUtil.makeLayers(seed, registry);
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return CODEC;
    }

    @Override
    public BiomeProvider getBiomeProvider(long seed) {
        return new MesozoicBiomeProvider(seed, registry);
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        return this.sample(registry, x, z);
    }

    public Biome sample(Registry<Biome> dynamicBiomeRegistry, int x, int z) {
        int resultBiomeID = this.genBiomes.field_215742_b.getValue(x, z);
        Biome biome = dynamicBiomeRegistry.getByValue(resultBiomeID);
        if (biome == null) {
            if (SharedConstants.developmentMode) {
                throw Util.pauseDevMode(new IllegalStateException("Unknown biome id: " + resultBiomeID));
            } else {
                // Spawn ocean if we can't resolve the biome from the layers.
                RegistryKey<Biome> backupBiomeKey = BiomeRegistry.getKeyFromID(0);
                LostInTime.LOGGER.warn("Unknown biome id: ${}. Will spawn ${} instead.", resultBiomeID, backupBiomeKey.getLocation());
                return dynamicBiomeRegistry.getValueForKey(backupBiomeKey);
            }
        } else {
            return biome;
        }
    }
}
