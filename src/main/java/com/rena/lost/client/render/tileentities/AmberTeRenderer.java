package com.rena.lost.client.render.tileentities;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.lost.common.tileentities.AmberTe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.ItemStack;

public class AmberTeRenderer extends TileEntityRenderer<AmberTe> {
    public AmberTeRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(AmberTe tileEntity, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLight, int combinedOverlay) {
        ItemStack storedItem = tileEntity.getStoredItem();
        if (!storedItem.isEmpty()) {
            matrixStack.push();
            matrixStack.translate(0.5, 0.5, 0.5);
            Minecraft.getInstance().getItemRenderer().renderItem(storedItem, ItemCameraTransforms.TransformType.GROUND, combinedLight, combinedOverlay, matrixStack, buffer);
            matrixStack.pop();
        }
    }
}
