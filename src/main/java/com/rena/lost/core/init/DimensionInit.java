package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import com.rena.lost.common.world.dimension.MesozoicBiomeProvider;
import com.rena.lost.common.world.dimension.MesozoicChunkGenerator;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class DimensionInit {

    public static RegistryKey<World> MESOZOIC = RegistryKey.getOrCreateKey(Registry.WORLD_KEY,
            new ResourceLocation(LostInTime.MOD_ID, "mesozoic"));

    public static void init() {
        Registry.register(Registry.BIOME_PROVIDER_CODEC, new ResourceLocation(LostInTime.MOD_ID,
                "mesozoic"), MesozoicBiomeProvider.CODEC);
        Registry.register(Registry.CHUNK_GENERATOR_CODEC, new ResourceLocation(LostInTime.MOD_ID,
                "chunk_generator"), MesozoicChunkGenerator.CODEC);
    }
}
