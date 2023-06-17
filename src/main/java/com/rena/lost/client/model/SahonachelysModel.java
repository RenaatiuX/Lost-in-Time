package com.rena.lost.client.model;

import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.Sahonachelys;
import net.minecraft.util.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;

public class SahonachelysModel extends AnimatedTickingGeoModel<Sahonachelys> {
    @Override
    public ResourceLocation getModelLocation(Sahonachelys object) {
        return new ResourceLocation(LostInTime.MOD_ID, "geo/sahonachelys.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Sahonachelys object) {
        return new ResourceLocation(LostInTime.MOD_ID, "textures/entity/sahonachelys.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Sahonachelys animatable) {
        return new ResourceLocation(LostInTime.MOD_ID, "animations/sahonachelys.json");
    }
}
