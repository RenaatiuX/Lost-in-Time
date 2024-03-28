package com.rena.lost.common.events;

import com.rena.lost.LostInTime;
import com.rena.lost.common.world.gen.LostOverworldGeneration;
import com.rena.lost.core.init.BiomeInit;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Comparator;

@Mod.EventBusSubscriber(modid = LostInTime.MOD_ID)
public class WorldEvents {

    @SubscribeEvent
    public static void biomeLoadingEvent(final BiomeLoadingEvent event) {
        LostOverworldGeneration.generate(event);
    }
}
