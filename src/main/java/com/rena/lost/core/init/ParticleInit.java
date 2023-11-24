package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.particles.ParticleType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ParticleInit {

    public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, LostInTime.MOD_ID);

    public static final RegistryObject<BasicParticleType> BLOOD_PARTICLE = PARTICLES.register("blood_particle",
            () -> new BasicParticleType(true));
}
