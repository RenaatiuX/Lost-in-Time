package com.rena.lost.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.lost.client.model.DakosaurusModel;
import com.rena.lost.common.entities.Dakosaurus;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class DakosaurusRenderer extends GeoEntityRenderer<Dakosaurus> {
    public DakosaurusRenderer(EntityRendererManager renderManager) {
        super(renderManager, new DakosaurusModel());
    }

    @Override
    public RenderType getRenderType(Dakosaurus animatable, float partialTicks, MatrixStack stack, @Nullable IRenderTypeBuffer renderTypeBuffer, @Nullable IVertexBuilder vertexBuilder, int packedLightIn, ResourceLocation textureLocation) {
        return RenderType.getEntityCutoutNoCull(getTextureLocation(animatable));
    }

    @Override
    public ResourceLocation getEntityTexture(Dakosaurus entity) {
        return getGeoModelProvider().getTextureLocation(entity);
    }

}
