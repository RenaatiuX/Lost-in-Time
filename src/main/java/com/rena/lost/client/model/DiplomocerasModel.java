package com.rena.lost.client.model;

import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.Diplomoceras;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class DiplomocerasModel extends AnimatedTickingGeoModel<Diplomoceras> {
    @Override
    public ResourceLocation getModelLocation(Diplomoceras object) {
        return new ResourceLocation(LostInTime.MOD_ID, "geo/diplomoceras.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Diplomoceras object) {
        return new ResourceLocation(LostInTime.MOD_ID, "textures/entity/diplomoceras.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Diplomoceras animatable) {
        return new ResourceLocation(LostInTime.MOD_ID, "animations/diplomoceras.json");
    }
}
