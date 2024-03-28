package com.rena.lost.client.events;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.rena.lost.LostInTime;
import com.rena.lost.core.init.EffectInit;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = LostInTime.MOD_ID, value = Dist.CLIENT)
public class ClientForgeEvents {

    public static final ResourceLocation DECAY_OVERLAY = LostInTime.modLoc("textures/overlay/decay_overlay.png");
    public static final ResourceLocation HEARTS = LostInTime.modLoc("textures/overlay/decay_hearts.png");
    public static final ResourceLocation BLOOD_OVERLAY = LostInTime.modLoc("textures/overlay/blood_overlay.png");
    private static final Minecraft mc = Minecraft.getInstance();
    public static final Random rand = new Random();
    protected static int healthIconsOffset;
    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.POTION_ICONS) {
            renderOverlay(event);
        }
    }

    public static void renderOverlay(RenderGameOverlayEvent event) {
        int screenWidth = mc.getMainWindow().getScaledWidth();
        int screenHeight = mc.getMainWindow().getScaledHeight();

        if (isDecayEffectActive()) {
            mc.getTextureManager().bindTexture(DECAY_OVERLAY);
            AbstractGui.blit(event.getMatrixStack(), 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
        }
        if (isBleedingEffectActive()) {
            mc.getTextureManager().bindTexture(BLOOD_OVERLAY);
            AbstractGui.blit(event.getMatrixStack(), 0, 0, 0, 0, screenWidth, screenHeight, screenWidth, screenHeight);
        }
    }

    @SubscribeEvent
    public static void onRenderGameHearts(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.HEALTH) {
            renderHearts(event);
        }
        healthIconsOffset = ForgeIngameGui.left_height;
    }

    public static void renderHearts(RenderGameOverlayEvent event) {
        int screenWidth = mc.getMainWindow().getScaledWidth() / 2 - 91;
        int screenHeight = mc.getMainWindow().getScaledHeight() - healthIconsOffset;

        PlayerEntity player = mc.player;
        if (isDecayEffectActive()) {
            event.setCanceled(true);
            renderHealth(player, mc, event.getMatrixStack(), screenWidth, screenHeight);
        }
    }

    public static void renderHealth(PlayerEntity player, Minecraft minecraft, MatrixStack matrixStack, int screenWidth, int screenHeight) {
        int ticks = minecraft.ingameGUI.getTicks();
        rand.setSeed(ticks * 312871L);

        int health = MathHelper.ceil(player.getHealth());
        float absorb = MathHelper.ceil(player.getAbsorptionAmount());
        ModifiableAttributeInstance attrMaxHealth = player.getAttribute(Attributes.MAX_HEALTH);
        float healthMax = (float) attrMaxHealth.getValue();

        int regen = -1;
        if (player.isPotionActive(Effects.REGENERATION)) regen = ticks % 25;

        int healthRows = MathHelper.ceil((healthMax + absorb) / 2.0F / 10.0F);
        int rowHeight = Math.max(10 - (healthRows - 2), 3);

        int comfortSheen = ticks % 50;
        int comfortHeartFrame = comfortSheen % 2;
        int[] textureWidth = {5, 9};

        minecraft.getTextureManager().bindTexture(HEARTS);
        RenderSystem.enableBlend();

        int totalHealth = MathHelper.ceil((healthMax + absorb) / 2.0F);
        for (int i = totalHealth - 1; i >= 0; --i) {
            int column = i % 10;
            int row = MathHelper.ceil((float) (i + 1) / 10.0F) - 1;
            int x = screenWidth + column * 8;
            int y = screenHeight - row * rowHeight;

            if (health <= 4) y += rand.nextInt(2);
            if (i == regen) y -= 2;


            if (column == comfortSheen / 2) {
                minecraft.ingameGUI.blit(matrixStack, x, y, 0, 9, textureWidth[comfortHeartFrame], 9);
            }
            if (column == (comfortSheen / 2) - 1 && comfortHeartFrame == 0) {
                minecraft.ingameGUI.blit(matrixStack, x + 5, y, 5, 9, 4, 9);
            }
        }

        RenderSystem.disableBlend();
        minecraft.getTextureManager().bindTexture(AbstractGui.GUI_ICONS_LOCATION);
    }


    private static boolean isDecayEffectActive() {
        if (mc.player != null) {
            return mc.player.isPotionActive(EffectInit.DECAY.get());
        }
        return false;
    }

    private static boolean isBleedingEffectActive() {
        if (mc.player != null) {
            return mc.player.isPotionActive(EffectInit.BLEEDING.get());
        }
        return false;
    }
}
