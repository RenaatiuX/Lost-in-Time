package com.rena.lost.client.events;

import com.rena.lost.LostInTime;
import com.rena.lost.client.model.SahonachelysModel;
import com.rena.lost.client.model.TepexichthysModel;
import com.rena.lost.client.render.*;
import com.rena.lost.core.init.BlockInit;
import com.rena.lost.core.init.EntityInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = LostInTime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        entityRenderer();
        registerBlockRenderer();
    }

    private static void entityRenderer() {
        ItemRenderer itemRendererIn = Minecraft.getInstance().getItemRenderer();
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.SAHONACHELYS.get(), SahonachelysRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.DAKOSAURUS.get(), DakosaurusRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.DIPLOMOCERAS.get(), DiplomocerasRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.HYPSOCORMUS.get(), HypsocormusRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.TEPEXICHTHYS.get(), TepexichthysRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.PELECANIMIMUS.get(), PelecanimimusRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.MIRARCE.get(), MirarceRenderer::new);

        RenderingRegistry.registerEntityRenderingHandler(EntityInit.CUSTOM_EGG.get(), manager -> new SpriteRenderer<>(manager, itemRendererIn));

    }

    private static void registerBlockRenderer() {
        RenderTypeLookup.setRenderLayer(BlockInit.DIPLOMOCERAS_SHELL.get(), RenderType.getCutout());
    }
}
