package com.rena.lost.client.events;

import com.rena.lost.LostInTime;
import com.rena.lost.client.render.*;
import com.rena.lost.client.render.tileentities.AmberTeRenderer;
import com.rena.lost.client.render.tileentities.NestTeRenderer;
import com.rena.lost.client.screens.NestScreen;
import com.rena.lost.common.items.ConcavenatorMaskItem;
import com.rena.lost.core.init.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScreenManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static net.minecraft.item.ItemModelsProperties.registerProperty;

@Mod.EventBusSubscriber(modid = LostInTime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientEvents {

    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        entityRenderer();
        registerBlockRenderer();
        armorModel();
        registerModelProperties();
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
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.MUD_BALL.get(), manager -> new SpriteRenderer<>(manager, itemRendererIn));
        RenderingRegistry.registerEntityRenderingHandler(EntityInit.SPEAR.get(), SpearRenderer::new);

        ClientRegistry.bindTileEntityRenderer(TileEntityInit.NEST_TE.get(), NestTeRenderer::new);
        ClientRegistry.bindTileEntityRenderer(TileEntityInit.AMBER_TE.get(), AmberTeRenderer::new);

        ScreenManager.registerFactory(ContainerInit.NEST_CONTAINER.get(), NestScreen::new);
    }

    private static void registerBlockRenderer() {
        RenderTypeLookup.setRenderLayer(BlockInit.DIPLOMOCERAS_SHELL.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.DUCKWEED.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.NEST_BLOCK.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.ARCHAEFRUCTUS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.APIOCRINUS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.CLADOPHLEBIS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.QUILLWORT_1.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.QUILLWORT_2.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SEIROCRINUS.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.SEIROCRINUS_PLANT.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.WEICHSELIA.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.CONIOPTERIS.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(BlockInit.AMBER_BLOCK.get(), RenderType.getTranslucent());

        RenderTypeLookup.setRenderLayer(BlockInit.ARAUCARIOXYLON_DOOR.get(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(BlockInit.ARAUCARIOXYLON_LEAVES.get(), RenderType.getCutout());

        RenderTypeLookup.setRenderLayer(BlockInit.VOIDITE_CRYSTAL.get(), RenderType.getCutout());
    }

    private static void armorModel(){
        ConcavenatorMaskItem.initArmorModel();
    }

    public static void registerModelProperties() {
        registerProperty(ItemInit.WOODEN_SPEAR.get(), new ResourceLocation("throwing"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
        registerProperty(ItemInit.STONE_SPEAR.get(), new ResourceLocation("throwing"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
        registerProperty(ItemInit.IRON_SPEAR.get(), new ResourceLocation("throwing"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
        registerProperty(ItemInit.GOLD_SPEAR.get(), new ResourceLocation("throwing"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
        registerProperty(ItemInit.DIAMOND_SPEAR.get(), new ResourceLocation("throwing"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
        registerProperty(ItemInit.NETHERITE_SPEAR.get(), new ResourceLocation("throwing"), (p_239428_0_, p_239428_1_, p_239428_2_) -> p_239428_2_ != null && p_239428_2_.isHandActive() && p_239428_2_.getActiveItemStack() == p_239428_0_ ? 1.0F : 0.0F);
    }
}
