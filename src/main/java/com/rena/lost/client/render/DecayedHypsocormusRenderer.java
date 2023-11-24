package com.rena.lost.client.render;

import com.rena.lost.client.model.DecayedHypsocormusModel;
import com.rena.lost.common.entities.DecayedHypsocormus;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DecayedHypsocormusRenderer extends GeoEntityRenderer<DecayedHypsocormus> {
    public DecayedHypsocormusRenderer(EntityRendererManager renderManager) {
        super(renderManager, new DecayedHypsocormusModel());
    }

    @Override
    public ResourceLocation getEntityTexture(DecayedHypsocormus entity) {
        return getGeoModelProvider().getTextureLocation(entity);
    }
}
