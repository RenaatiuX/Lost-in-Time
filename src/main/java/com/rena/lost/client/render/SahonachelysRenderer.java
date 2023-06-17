package com.rena.lost.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.rena.lost.client.model.SahonachelysModel;
import com.rena.lost.client.render.layer.SahonachelysMossLayer;
import com.rena.lost.common.entities.Sahonachelys;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

import javax.annotation.Nullable;

public class SahonachelysRenderer extends GeoEntityRenderer<Sahonachelys> {
    public SahonachelysRenderer(EntityRendererManager renderManager) {
        super(renderManager, new SahonachelysModel());
        this.addLayer(new SahonachelysMossLayer(this));
        this.shadowSize = 0.5F;
    }

    @Override
    public ResourceLocation getEntityTexture(Sahonachelys entity) {
        return getGeoModelProvider().getTextureLocation(entity);
    }
}
