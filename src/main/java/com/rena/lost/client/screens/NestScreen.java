package com.rena.lost.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.lost.LostInTime;
import com.rena.lost.common.container.NestTeContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class NestScreen extends ContainerScreen<NestTeContainer> {


    public static final ResourceLocation TEXTURE = new ResourceLocation(LostInTime.MOD_ID, "textures/gui/nest.png");

    public NestScreen(NestTeContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int x, int y) {
        this.font.drawText(matrixStack, this.title, (float)this.titleX, (float)this.titleY, 4210752);
        this.font.drawText(matrixStack, this.playerInventory.getDisplayName(), (float)this.playerInventoryTitleX, (float)this.playerInventoryTitleY - 32, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(MatrixStack matrixStack, float partialTicks, int x, int y) {
        this.minecraft.textureManager.bindTexture(TEXTURE);
        int middleX = (this.width - this.xSize) / 2;
        int middleY = (this.height - this.ySize) / 2;
        this.blit(matrixStack, middleX, middleY, 0, 0, 176, 133);
    }
}
