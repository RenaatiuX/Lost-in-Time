package com.rena.lost.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.rena.lost.common.tileentities.NestBlockTe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.client.model.data.EmptyModelData;
import net.minecraftforge.items.ItemStackHandler;

public class NestTeRenderer extends TileEntityRenderer<NestBlockTe> {
    public NestTeRenderer(TileEntityRendererDispatcher rendererDispatcherIn) {
        super(rendererDispatcherIn);
    }

    @Override
    public void render(NestBlockTe tileEntityIn, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        ItemStackHandler handler = tileEntityIn.getInventory();
        if (!handler.getStackInSlot(0).isEmpty()){
            drawEgg(matrixStackIn, bufferIn, combinedOverlayIn, tileEntityIn, 0, 0.05f, 0.4f, -0.1f);
        }
        if (!handler.getStackInSlot(1).isEmpty()){
            drawEgg(matrixStackIn, bufferIn, combinedOverlayIn, tileEntityIn, 1, 0.05f, 0.4f, 0.4f);
        }
        if (!handler.getStackInSlot(2).isEmpty()){
            drawEgg(matrixStackIn, bufferIn, combinedOverlayIn, tileEntityIn, 2, 0.05f, 0.4f, 0.9f);
        }
        if (!handler.getStackInSlot(3).isEmpty()){
            drawEgg(matrixStackIn, bufferIn, combinedOverlayIn, tileEntityIn, 3, .65f, 0.4f, -0.1f);
        }
        if (!handler.getStackInSlot(4).isEmpty()){
            drawEgg(matrixStackIn, bufferIn, combinedOverlayIn, tileEntityIn, 4, .65f, 0.4f, 0.4f);
        }
        if (!handler.getStackInSlot(5).isEmpty()){
            drawEgg(matrixStackIn, bufferIn, combinedOverlayIn, tileEntityIn, 5, .65f, 0.4f, 0.9f);
        }

    }

    private void drawEgg(MatrixStack stack, IRenderTypeBuffer buffer, int combinedOverlayIn, NestBlockTe te, int index, float xOffset, float yOffset, float zOffset) {
        ItemStack itemStack = te.getInventory().getStackInSlot(index);
        Block eggBlock = ((BlockItem)itemStack.getItem()).getBlock();
        BlockState egg = eggBlock.getDefaultState().with(BlockStateProperties.HATCH_0_2, te.getHatchingStates().get(index));
        Minecraft mc = Minecraft.getInstance();
        stack.push();
        stack.scale(0.5f, 0.5f, 0.5f);
        stack.translate(xOffset, yOffset, zOffset);
        mc.getBlockRendererDispatcher().renderBlock(egg, stack, buffer, 200, combinedOverlayIn, EmptyModelData.INSTANCE);
        stack.pop();
    }
}
