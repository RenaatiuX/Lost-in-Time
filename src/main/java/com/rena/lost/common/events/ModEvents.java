package com.rena.lost.common.events;

import com.rena.lost.LostInTime;
import com.rena.lost.client.particles.BloodParticle;
import com.rena.lost.core.init.BiomeInit;
import com.rena.lost.core.init.DimensionInit;
import com.rena.lost.core.init.ParticleInit;
import net.minecraft.client.Minecraft;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.Comparator;

@Mod.EventBusSubscriber(modid = LostInTime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEvents {

    /*@SubscribeEvent
    public void setup(final FMLCommonSetupEvent event) {
        BiomeInit.addBiomeEntries();
        BiomeInit.fillBiomeDictionary();
        DimensionInit.init();
    }*/

    @SubscribeEvent
    public static void registerParticleFactory(final ParticleFactoryRegisterEvent event) {
        Minecraft.getInstance().particles.registerFactory(ParticleInit.BLOOD_PARTICLE.get(), BloodParticle.Factory::new);
    }

    /*@SubscribeEvent(priority = EventPriority.LOW)
    public static void createBiomes(RegistryEvent.Register<Biome> event) {
        BiomeInit.biomeList.sort(Comparator.comparingInt(BiomeInit.PreserveBiomeOrder::getOrderPosition));
        BiomeInit.biomeList.forEach(preserveBiomeOrder -> event.getRegistry().register(preserveBiomeOrder.getBiome().get()));
    }*/

}
