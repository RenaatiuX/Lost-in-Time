package com.rena.lost.client.render;

import com.rena.lost.client.model.TepexichthysModel;
import com.rena.lost.common.entities.Tepexichthys;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class TepexichthysRenderer extends GeoEntityRenderer<Tepexichthys> {
    public TepexichthysRenderer(EntityRendererManager renderManager) {
        super(renderManager, new TepexichthysModel());
    }

    @Override
    public ResourceLocation getEntityTexture(Tepexichthys entity) {
        return getGeoModelProvider().getTextureLocation(entity);
    }
}
