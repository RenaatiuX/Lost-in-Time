package com.rena.lost.common.entities.ia;

import com.rena.lost.common.entities.Pelecanimimus;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.tags.FluidTags;

import java.util.EnumSet;

public class PelecanimimusWadeSwimming extends Goal {

    private final Pelecanimimus entity;

    public PelecanimimusWadeSwimming(Pelecanimimus entity) {
        this.entity = entity;
        this.setMutexFlags(EnumSet.of(Flag.JUMP));
        entity.getNavigator().setCanSwim(true);
    }

    @Override
    public boolean shouldExecute() {
        return this.entity.isInWater() && this.entity.func_233571_b_(FluidTags.WATER) > 1F  || this.entity.isInLava();
    }

    @Override
    public void tick() {
        if (this.entity.getRNG().nextFloat() < 0.8F) {
            this.entity.getJumpController().setJumping();
        }

    }
}
