package com.rena.lost.common.entities.ia;

import com.rena.lost.common.entities.Pelecanimimus;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

import java.util.EnumSet;
import java.util.Random;

public class PelecanimimusFishingGoal extends Goal {
    private final Pelecanimimus entity;
    private BlockPos waterPos = null;
    private BlockPos targetPos = null;
    private Direction[] HORIZONTALS = new Direction[]{Direction.NORTH, Direction.SOUTH, Direction.WEST, Direction.EAST};
    private int navigateTime = 0;
    private int idleTime = 0;

    public PelecanimimusFishingGoal(Pelecanimimus entity) {
        this.setMutexFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
        this.entity = entity;
    }

    @Override
    public void resetTask() {
        targetPos = null;
        waterPos = null;
        navigateTime = 0;
        this.entity.getNavigator().clearPath();
        this.entity.setFishing(false);
    }

    @Override
    public void tick() {
        if (targetPos != null && waterPos != null) {
            double dist = entity.getDistanceSq(Vector3d.copyCentered(waterPos));
            if (dist <= 1F) {
                navigateTime = 0;
                double d0 = waterPos.getX() + 0.5D - entity.getPosX();
                double d2 = waterPos.getZ() + 0.5D - entity.getPosZ();
                float yaw = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                entity.rotationYaw = yaw;
                entity.rotationYawHead = yaw;
                entity.renderYawOffset = yaw;
                entity.getNavigator().clearPath();
                idleTime++;
                if (!entity.isTamed()) {
                    if (idleTime > 25) {
                        this.entity.setFishing(true);
                    }
                    if (idleTime > 45 && this.entity.isFishing()) {
                        this.entity.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 0.7F, 0.5F + entity.getRNG().nextFloat());
                        this.entity.resetFishingTimer();
                        this.entity.spawnFishItem();
                        this.resetTask();
                    }
                } else {
                    if (idleTime > 25) {
                        this.entity.setFishing(true);
                    }
                    PlayerEntity owner = (PlayerEntity) this.entity.getOwner();
                    if (owner != null && owner.isHandActive() && owner.getActiveItemStack().getItem() == Items.FISHING_ROD) {
                        BlockPos ownerPos = owner.getPosition();
                        double distanceSq = this.entity.getDistanceSq(ownerPos.getX(), ownerPos.getY(), ownerPos.getZ());
                        if (distanceSq <= 25) { // 5 * 5 = 25
                            Random random = new Random();
                            if (idleTime > 45 && this.entity.isFishing()) {
                                if (random.nextInt(3) == 0) {
                                    this.entity.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 0.7F, 0.5F + entity.getRNG().nextFloat());
                                    this.entity.resetFishingTimer();
                                    this.entity.spawnFishItem();
                                    this.resetTask();
                                }
                            }
                        }
                    }
                }
            } else {
                navigateTime++;
                entity.getNavigator().tryMoveToXYZ(waterPos.getX(), waterPos.getY(), waterPos.getZ(), 1.2D);
            }
            if (navigateTime > 3600) {
                this.resetTask();
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting() {
        return targetPos != null && this.entity.fishTimer == 0;
    }

    @Override
    public boolean shouldExecute() {
        if (this.entity.fishTimer == 0 && this.entity.getRNG().nextInt(30) == 0) {
            if (entity.isInWater()) {
                waterPos = entity.getPosition();
                targetPos = waterPos;
                return true;
            } else {
                waterPos = generateTarget();
                if (waterPos != null) {
                    targetPos = getLandPos(waterPos);
                    return targetPos != null;
                }
            }

        }
        return false;
    }

    public BlockPos generateTarget() {
        BlockPos blockpos = null;
        Random random = new Random();
        int range = 32;
        for (int i = 0; i < 15; i++) {
            BlockPos blockpos1 = this.entity.getPosition().add(random.nextInt(range) - range / 2, 3, random.nextInt(range) - range / 2);
            while (this.entity.world.isAirBlock(blockpos1) && blockpos1.getY() > 1) {
                blockpos1 = blockpos1.down();
            }
            if (isConnectedToLand(blockpos1)) {
                blockpos = blockpos1;
            }
        }
        return blockpos;
    }

    public boolean isConnectedToLand(BlockPos pos) {
        if (this.entity.world.getFluidState(pos).isTagged(FluidTags.WATER)) {
            for (Direction dir : HORIZONTALS) {
                BlockPos offsetPos = pos.offset(dir);
                if (this.entity.world.getFluidState(offsetPos).isEmpty() && this.entity.world.getFluidState(offsetPos.up()).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    public BlockPos getLandPos(BlockPos pos) {
        if (this.entity.world.getFluidState(pos).isTagged(FluidTags.WATER)) {
            for (Direction dir : HORIZONTALS) {
                BlockPos offsetPos = pos.offset(dir);
                if (this.entity.world.getFluidState(offsetPos).isEmpty() && this.entity.world.getFluidState(offsetPos.up()).isEmpty()) {
                    return offsetPos;
                }
            }
        }
        return null;
    }
}


