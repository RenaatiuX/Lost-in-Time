package com.rena.lost;

import com.rena.lost.common.entities.*;
import com.rena.lost.core.init.*;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib3.GeckoLib;

import java.util.stream.Collectors;

@Mod(LostInTime.MOD_ID)
public class LostInTime
{
    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "lost";
    public static final String ARMOR_DIR = MOD_ID + ":textures/armor/";

    public LostInTime() {
        // Register the setup method for modloading
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);
        modEventBus.addListener(this::doClientStuff);
        modEventBus.addListener(this::registerEntityAttributes);
        GeckoLib.initialize();

        MinecraftForge.EVENT_BUS.register(this);

        ItemInit.ITEMS.register(modEventBus);
        BlockInit.BLOCKS.register(modEventBus);
        EntityInit.ENTITY_TYPES.register(modEventBus);
        TileEntityInit.TILE_ENTITIES.register(modEventBus);
        ContainerInit.CONTAINER_TYPES.register(modEventBus);
    }

    private void setup(final FMLCommonSetupEvent event)
    {

    }

    private void doClientStuff(final FMLClientSetupEvent event) {

    }

    public static ResourceLocation modLoc(String name) {
        return new ResourceLocation(MOD_ID, name);
    }

    private void registerEntityAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityInit.SAHONACHELYS.get(), Sahonachelys.createAttributes().create());
        event.put(EntityInit.DAKOSAURUS.get(), Dakosaurus.createAttributes().create());
        event.put(EntityInit.DIPLOMOCERAS.get(), Diplomoceras.createAttributes().create());
        event.put(EntityInit.HYPSOCORMUS.get(), Hypsocormus.createAttributes().create());
        event.put(EntityInit.TEPEXICHTHYS.get(), Tepexichthys.createAttributes().create());
        event.put(EntityInit.PELECANIMIMUS.get(), Pelecanimimus.createAttributes().create());
        event.put(EntityInit.MIRARCE.get(), Mirarce.createAttributes().create());
    }
}
