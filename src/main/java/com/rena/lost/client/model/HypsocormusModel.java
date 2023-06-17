package com.rena.lost.client.model;

import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.Hypsocormus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class HypsocormusModel extends AnimatedTickingGeoModel<Hypsocormus> {
    @Override
    public ResourceLocation getModelLocation(Hypsocormus object) {
        return new ResourceLocation(LostInTime.MOD_ID, "geo/hypsocormus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Hypsocormus object) {
        return new ResourceLocation(LostInTime.MOD_ID, "textures/entity/hypsocormus.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Hypsocormus animatable) {
        return new ResourceLocation(LostInTime.MOD_ID, "animations/hypsocormus.json");
    }
}
