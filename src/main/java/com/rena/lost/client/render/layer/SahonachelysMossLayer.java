package com.rena.lost.client.render.layer;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.Sahonachelys;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.GeckoLib;
import software.bernie.geckolib3.renderers.geo.GeoLayerRenderer;
import software.bernie.geckolib3.renderers.geo.IGeoRenderer;

public class SahonachelysMossLayer extends GeoLayerRenderer<Sahonachelys> {
    private static final ResourceLocation LAYER = new ResourceLocation(LostInTime.MOD_ID, "textures/entity/sahonachelys_seagrass.png");
    private static final ResourceLocation MODEL = new ResourceLocation(LostInTime.MOD_ID, "geo/sahonachelys.geo.json");

    public SahonachelysMossLayer(IGeoRenderer entityRendererIn) {
        super(entityRendererIn);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, Sahonachelys entityLivingBaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(entityLivingBaseIn.hasMoss() && !entityLivingBaseIn.isChild()){
            RenderType renderType = RenderType.getEntityCutoutNoCull(LAYER);
            matrixStackIn.push();
            this.getRenderer().render(this.getEntityModel().getModel(MODEL), entityLivingBaseIn, partialTicks, renderType, matrixStackIn, bufferIn,
                    bufferIn.getBuffer(renderType), packedLightIn, OverlayTexture.NO_OVERLAY, 1f, 1f, 1f, 1f);
            matrixStackIn.pop();
        }
    }
}
