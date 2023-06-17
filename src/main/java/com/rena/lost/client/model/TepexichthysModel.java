package com.rena.lost.client.model;

import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.Tepexichthys;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class TepexichthysModel extends AnimatedTickingGeoModel<Tepexichthys> {
    @Override
    public ResourceLocation getModelLocation(Tepexichthys object) {
        return new ResourceLocation(LostInTime.MOD_ID, "geo/tepexichthys.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Tepexichthys object) {
        return new ResourceLocation(LostInTime.MOD_ID, "textures/entity/tepexichthys.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Tepexichthys animatable) {
        return new ResourceLocation(LostInTime.MOD_ID, "animations/tepexichthys.json");
    }
}
