package com.rena.lost.common.entities.ia;

import com.rena.lost.common.entities.Pelecanimimus;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import software.bernie.shadowed.eliotlash.mclib.math.functions.utility.Random;

import java.util.EnumSet;
import java.util.stream.Stream;

public class PelecanimimusFishingGoal extends Goal {

    //this defines the cooldown +- random Offset
    protected static final int FISHING_COOLDOWN = 15000, RANDOM_OFFSET = 500;

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
    public boolean shouldContinueExecuting() {
        return targetPos != null && this.entity.fishTimer == 0 && (!entity.isTamed() || entity.fishingCondition());
    }

    @Override
    public boolean shouldExecute() {
        if (entity.isStartingFishing() && this.entity.getRNG().nextInt(30) == 0 && !entity.isChild()) {
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

    @Override
    public void resetTask() {
        targetPos = null;
        waterPos = null;
        navigateTime = 0;
        entity.setStartingFishing(false);
        this.entity.getNavigator().clearPath();
        this.entity.setFishing(false);
        idleTime = 0;
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
                    if (idleTime > 100 && this.entity.isFishing()) {
                        fish();
                    }
                } else {
                    PlayerEntity owner = (PlayerEntity) this.entity.getOwner();
                    if (idleTime > 25) {
                        this.entity.setFishing(true);
                    }
                    if (owner != null && owner.fishingBobber != null) {
                        if (idleTime > 100 && this.entity.isFishing()) {
                            if (this.entity.getRNG().nextInt(3) == 0) {
                                fish();
                            }
                        }
                    } else {
                        this.targetPos = null;
                    }
                }
            } else {
                navigateTime++;
                entity.getNavigator().tryMoveToXYZ(waterPos.getX(), waterPos.getY(), waterPos.getZ(), 1.25D);
            }
            if (navigateTime > 3600) {
                this.targetPos = null;
            }
        }
    }

    public void fish() {
        this.entity.playSound(SoundEvents.ENTITY_GENERIC_SPLASH, 0.7F, 0.5F + entity.getRNG().nextFloat());
        if (!this.entity.isTamed())
            this.entity.fishTimer = FISHING_COOLDOWN + entity.getRNG().nextInt(2 * RANDOM_OFFSET) - RANDOM_OFFSET;
        this.entity.spawnFishItem();
        this.targetPos = null;
    }


    public BlockPos generateTarget() {
        if (!this.entity.isTamed()) {
            int range = 32;
            for (int i = 0; i < 15; i++) {
                BlockPos blockpos1 = this.entity.getPosition().add(this.entity.getRNG().nextInt(range) - range / 2, 3, this.entity.getRNG().nextInt(range) - range / 2);
                while (this.entity.world.isAirBlock(blockpos1) && blockpos1.getY() > 1) {
                    blockpos1 = blockpos1.down();
                }
                if (isConnectedToLand(blockpos1)) {
                    return blockpos1;
                }

            }
        } else {
            LivingEntity owner = this.entity.getOwner();
            int range = 5;
            for (int x = -range; x <= range; x++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos current = owner.getPosition().add(x, 3, z);
                    while (this.entity.world.isAirBlock(current) && current.getY() > 1) {
                        current = current.down();
                    }
                    if (isConnectedToLand(current)) {
                        return current;
                    }
                }
            }
        }

        return null;
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


