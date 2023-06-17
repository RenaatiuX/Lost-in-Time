package com.rena.lost.client.model;

import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.Mirarce;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class MirarceModel extends AnimatedTickingGeoModel<Mirarce> {
    @Override
    public ResourceLocation getModelLocation(Mirarce object) {
        return new ResourceLocation(LostInTime.MOD_ID, "geo/mirarce.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Mirarce object) {
        return new ResourceLocation(LostInTime.MOD_ID, "textures/entity/mirarce.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Mirarce animatable) {
        return new ResourceLocation(LostInTime.MOD_ID, "animations/mirarce.json");
    }
}
