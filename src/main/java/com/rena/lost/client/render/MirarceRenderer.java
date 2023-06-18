package com.rena.lost.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.lost.client.model.MirarceModel;
import com.rena.lost.common.entities.Mirarce;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class MirarceRenderer extends GeoEntityRenderer<Mirarce> {
    public MirarceRenderer(EntityRendererManager renderManager) {
        super(renderManager, new MirarceModel());
    }

    @Override
    public RenderType getRenderType(Mirarce animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        if(animatable.isChild()) {
            stack.scale(0.5F, 0.5F, 0.5F);
        }
        return super.getRenderType(animatable, partialTicks, stack, renderTypeBuffer, vertexBuilder, packedLightIn, textureLocation);
    }

    @Override
    public ResourceLocation getEntityTexture(Mirarce entity) {
        return getGeoModelProvider().getTextureLocation(entity);
    }
}
