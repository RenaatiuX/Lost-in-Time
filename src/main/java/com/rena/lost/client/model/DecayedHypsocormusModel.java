package com.rena.lost.client.model;

import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.DecayedHypsocormus;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class DecayedHypsocormusModel extends AnimatedTickingGeoModel<DecayedHypsocormus> {
    @Override
    public ResourceLocation getModelLocation(DecayedHypsocormus object) {
        return new ResourceLocation(LostInTime.MOD_ID, "geo/decayed_hypsocormus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(DecayedHypsocormus object) {
        return new ResourceLocation(LostInTime.MOD_ID, "textures/entity/decayed_hypsocormus.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(DecayedHypsocormus animatable) {
        return new ResourceLocation(LostInTime.MOD_ID, "animations/decayed_hypsocormus.json");
    }
}
