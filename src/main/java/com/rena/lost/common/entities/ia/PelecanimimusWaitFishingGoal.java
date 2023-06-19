package com.rena.lost.common.entities.ia;

import com.rena.lost.common.entities.Pelecanimimus;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;

public class PelecanimimusWaitFishingGoal extends Goal {

    private final Pelecanimimus pelecanimimus;
    protected BlockPos targetPos = null;

    public PelecanimimusWaitFishingGoal(Pelecanimimus pelecanimimus) {
        this.pelecanimimus = pelecanimimus;
    }

    @Override
    public void startExecuting() {
        pelecanimimus.setStartingFishing(true);
    }

    @Override
    public boolean shouldExecute() {
        return !pelecanimimus.isChild() && !pelecanimimus.isFishing() && (this.pelecanimimus.isTamed() ? pelecanimimus.fishingCondition() : !hasFishingCooldown());
    }



    private boolean hasFishingCooldown() {
        return this.pelecanimimus.fishTimer > 0;
    }


}
