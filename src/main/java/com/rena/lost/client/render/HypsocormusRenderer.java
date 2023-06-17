package com.rena.lost.client.render;

import com.rena.lost.client.model.HypsocormusModel;
import com.rena.lost.common.entities.Hypsocormus;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class HypsocormusRenderer extends GeoEntityRenderer<Hypsocormus> {
    public HypsocormusRenderer(EntityRendererManager renderManager) {
        super(renderManager, new HypsocormusModel());
    }

    @Override
    public ResourceLocation getEntityTexture(Hypsocormus entity) {
        return getGeoModelProvider().getTextureLocation(entity);
    }
}
