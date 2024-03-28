package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import com.rena.lost.common.biome.BiomeData;
import com.rena.lost.common.biome.LostBiome;
import com.rena.lost.common.biome.mesozoic.AraucarioxylonForest;
import com.rena.lost.common.biome.mesozoic.MesozoicMarsh;
import com.rena.lost.common.biome.mesozoic.MesozoicOcean;
import com.rena.lost.common.biome.mesozoic.MesozoicRiver;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.biome.*;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class BiomeInit {
    public static final DeferredRegister<Biome> BIOMES = DeferredRegister.create(ForgeRegistries.BIOMES, LostInTime.MOD_ID);
    public static List<PreserveBiomeOrder> biomeList = new ArrayList<>();
    public static Set<Integer> integerList = new HashSet<>();

    public static final LostBiome MESOZOIC_MARSH_BIOME = new MesozoicMarsh();
    public static final LostBiome MESOZOIC_RIVER_BIOME = new MesozoicRiver();
    public static final LostBiome ARAUCARIOXYLON_FOREST_BIOME = new AraucarioxylonForest();
    public static final LostBiome MESOZOIC_OCEAN_BIOME = new MesozoicOcean();

    public static final RegistryObject<Biome> MESOZOIC_MARSH = register("mesozoic_marsh", MESOZOIC_MARSH_BIOME::getBiome, 1);
    public static final RegistryObject<Biome> MESOZOIC_RIVER = register("mesozoic_river", MESOZOIC_RIVER_BIOME::getBiome, 2);
    public static final RegistryObject<Biome> ARAUCARIOXYLON_FOREST = register("araucarioxylon_forest",
            ARAUCARIOXYLON_FOREST_BIOME::getBiome, 3);
    public static final RegistryObject<Biome> MESOZOIC_OCEAN = register("mesozoic_ocean", MESOZOIC_OCEAN_BIOME::getBiome, 4);

    public static RegistryObject<Biome> register(String name, Supplier<? extends Biome> biomeSupplier, int numericalID) {
        ResourceLocation id = new ResourceLocation(LostInTime.MOD_ID, name);
        if (ForgeRegistries.BIOMES.containsKey(id)) {
            throw new IllegalStateException("Biome ID: \"" + id + "\" already exists in the Biome registry!");
        }
        RegistryObject<Biome> biomeRegistryObject = BiomeInit.BIOMES.register(name, biomeSupplier);
        biomeList.add(new PreserveBiomeOrder(biomeRegistryObject, numericalID));
        integerList.add(numericalID);

        return biomeRegistryObject;
    }

    public static void addBiomeEntries() {
        for (BiomeData biomeData : LostBiome.biomeData) {
            List<BiomeDictionary.Type> dictionaryList = Arrays.stream(biomeData.getDictionaryTypes()).collect(Collectors.toList());
            ResourceLocation key = ForgeRegistries.BIOMES.getKey(biomeData.getBiome());
            if (biomeData.getBiomeWeight() > 0) {
                BiomeManager.addBiome(biomeData.getBiomeType(), new BiomeManager.BiomeEntry(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, key), biomeData.getBiomeWeight()));
            }
        }
    }

    public static void fillBiomeDictionary() {
        for (BiomeData biomeData : LostBiome.biomeData) {
            BiomeDictionary.addTypes(RegistryKey.getOrCreateKey(Registry.BIOME_KEY, ForgeRegistries.BIOMES.getKey(biomeData.getBiome())), biomeData.getDictionaryTypes());
        }
    }

    public static class PreserveBiomeOrder {
        private final RegistryObject<Biome> biome;
        private final int orderPosition;

        public PreserveBiomeOrder(RegistryObject<Biome> biome, int orderPosition) {
            this.biome = biome;
            this.orderPosition = orderPosition;
        }

        public RegistryObject<Biome> getBiome() {
            return biome;
        }

        public int getOrderPosition() {
            return orderPosition;
        }

    }
}
