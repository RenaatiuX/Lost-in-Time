package com.rena.lost.common.entities.ia;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.AvoidEntityGoal;

public class MirarceAvoidEntityGoal extends AvoidEntityGoal<LivingEntity> {
    public MirarceAvoidEntityGoal(CreatureEntity entityIn, Class<LivingEntity> classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
        super(entityIn, classToAvoidIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
    }

    @Override
    public boolean shouldExecute() {
        return this.entity.isSprinting() && super.shouldExecute();
    }

    @Override
    public boolean shouldContinueExecuting() {
        return this.entity.isSprinting() && super.shouldContinueExecuting();
    }
}
