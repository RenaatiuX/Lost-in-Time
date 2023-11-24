package com.rena.lost.common.events;

import com.rena.lost.LostInTime;
import com.rena.lost.client.particles.BloodParticle;
import com.rena.lost.core.init.ParticleInit;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = LostInTime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    @SubscribeEvent
    public static void registerParticleFactory(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(ParticleInit.BLOOD_PARTICLE.get(), BloodParticle.Factory::new);
    }

}
