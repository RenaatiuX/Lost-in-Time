package com.rena.lost.client.model;

import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.Pelecanimimus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class PelecanimimusModel extends AnimatedTickingGeoModel<Pelecanimimus> {
    @Override
    public ResourceLocation getModelLocation(Pelecanimimus object) {
        return new ResourceLocation(LostInTime.MOD_ID, object.isChild() ? "geo/pelecanimimus_baby.geo.json" : "geo/pelecanimimus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Pelecanimimus object) {
        return new ResourceLocation(LostInTime.MOD_ID, object.isChild() ? "textures/entity/pelecanimimus_baby.png" : "textures/entity/pelecanimimus.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Pelecanimimus animatable) {
        return new ResourceLocation(LostInTime.MOD_ID, animatable.isChild() ? "animations/pelecanimimus_baby.json" : "animations/pelecanimimus.json");
    }
}
