package com.rena.lost.client.render;

import com.rena.lost.client.model.DiplomocerasModel;
import com.rena.lost.common.entities.Diplomoceras;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class DiplomocerasRenderer extends GeoEntityRenderer<Diplomoceras> {
    public DiplomocerasRenderer(EntityRendererManager renderManager) {
        super(renderManager, new DiplomocerasModel());
    }

    @Override
    public ResourceLocation getEntityTexture(Diplomoceras entity) {
        return getGeoModelProvider().getTextureLocation(entity);
    }
}
