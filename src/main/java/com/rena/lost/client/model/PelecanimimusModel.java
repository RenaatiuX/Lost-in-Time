package com.rena.lost.client.model;

import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.Pelecanimimus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class PelecanimimusModel extends AnimatedTickingGeoModel<Pelecanimimus> {
    @Override
    public ResourceLocation getModelLocation(Pelecanimimus object) {
        return new ResourceLocation(LostInTime.MOD_ID, "geo/pelecanimimus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Pelecanimimus object) {
        return new ResourceLocation(LostInTime.MOD_ID, "textures/entity/pelecanimimus.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Pelecanimimus animatable) {
        return new ResourceLocation(LostInTime.MOD_ID, "animations/pelecanimimus.json");
    }
}
