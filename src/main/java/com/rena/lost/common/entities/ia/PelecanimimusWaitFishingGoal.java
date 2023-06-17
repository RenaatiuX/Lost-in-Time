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
        pelecanimimus.setStartinFishing(true);
    }

    @Override
    public boolean shouldExecute() {
        return (this.pelecanimimus.isTamed() ? fishingCondition() : !hasFishingCooldown()) && !pelecanimimus.isFishing();
    }

    private boolean fishingCondition() {
        PlayerEntity owner = (PlayerEntity) pelecanimimus.getOwner();
        return owner != null && owner.isHandActive() && owner.getActiveItemStack().getItem() == Items.FISHING_ROD;
    }

    private boolean hasFishingCooldown() {
        return this.pelecanimimus.fishTimer > 0;
    }


}
