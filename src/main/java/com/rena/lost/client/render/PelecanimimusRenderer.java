package com.rena.lost.client.render;

import com.rena.lost.client.model.PelecanimimusModel;
import com.rena.lost.common.entities.Pelecanimimus;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class PelecanimimusRenderer extends GeoEntityRenderer<Pelecanimimus> {
    public PelecanimimusRenderer(EntityRendererManager renderManager) {
        super(renderManager, new PelecanimimusModel());
    }

    @Override
    public ResourceLocation getEntityTexture(Pelecanimimus entity) {
        return getGeoModelProvider().getTextureLocation(entity);
    }
}
