package com.rena.lost.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.lost.client.model.PelecanimimusModel;
import com.rena.lost.common.entities.Mirarce;
import com.rena.lost.common.entities.Pelecanimimus;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class PelecanimimusRenderer extends GeoEntityRenderer<Pelecanimimus> {
    public PelecanimimusRenderer(EntityRendererManager renderManager) {
        super(renderManager, new PelecanimimusModel());
    }

    @Override
    public RenderType getRenderType(Pelecanimimus animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }

    @Override
    public ResourceLocation getEntityTexture(Pelecanimimus entity) {
        return getGeoModelProvider().getTextureLocation(entity);
    }
}
