package com.rena.lost.client.model;

import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.Dakosaurus;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.model.AnimatedTickingGeoModel;
import software.bernie.geckolib3.model.provider.data.EntityModelData;

import javax.annotation.Nullable;
import java.util.List;

import static net.minecraft.entity.Entity.horizontalMag;

public class DakosaurusModel extends AnimatedTickingGeoModel<Dakosaurus> {
    @Override
    public ResourceLocation getModelLocation(Dakosaurus object) {
        return new ResourceLocation(LostInTime.MOD_ID, "geo/dakosaurus.geo.json");
    }

    @Override
    public ResourceLocation getTextureLocation(Dakosaurus object) {
        return new ResourceLocation(LostInTime.MOD_ID, "textures/entity/dakosaurus.png");
    }

    @Override
    public ResourceLocation getAnimationFileLocation(Dakosaurus animatable) {
        return new ResourceLocation(LostInTime.MOD_ID, "animations/dakosaurus.json");
    }
}
