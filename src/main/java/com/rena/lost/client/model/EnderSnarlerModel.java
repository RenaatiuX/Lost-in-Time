package com.rena.lost.client.model;

import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.EnderSnarler;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class EnderSnarlerModel extends AnimatedTickingGeoModel<EnderSnarler> {
    @Override
    public ResourceLocation getModelLocation(EnderSnarler object) {
        return new ResourceLocation(LostInTime.MOD_ID, "geo/ender_snarler.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(EnderSnarler object) {
        return new ResourceLocation(LostInTime.MOD_ID, "textures/entity/ender_snarler.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(EnderSnarler animatable) {
        return new ResourceLocation(LostInTime.MOD_ID, "animations/ender_snarler.json");
    }
}
