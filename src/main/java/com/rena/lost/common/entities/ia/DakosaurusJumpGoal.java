package com.rena.lost.common.entities.ia;

import com.rena.lost.common.entities.Dakosaurus;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.JumpGoal;
import net.minecraft.fluid.FluidState;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;

public class DakosaurusJumpGoal extends JumpGoal {
    private static final int[] JUMP_DISTANCES = new int[]{0, 1, 4, 5, 6, 7};
    private final Dakosaurus dakosaurus;
    private final int chance;
    private boolean inWater;

    public DakosaurusJumpGoal(Dakosaurus dakosaurus, int chance) {
        this.dakosaurus = dakosaurus;
        this.chance = chance;
    }

    @Override
    public boolean shouldExecute() {
        if (this.dakosaurus.getRNG().nextInt(this.chance) != 0 || dakosaurus.getAttackTarget() != null || dakosaurus.jumpCooldown != 0) {
            return false;
        } else {
            Direction direction = this.dakosaurus.getAdjustedHorizontalFacing();
            int i = direction.getXOffset();
            int j = direction.getZOffset();
            BlockPos blockpos = this.dakosaurus.getPosition();

            for(int k : JUMP_DISTANCES) {
                if (!this.canJumpTo(blockpos, i, j, k) || !this.isAirAbove(blockpos, i, j, k)) {
                    return false;
                }
            }
            return true;
        }
    }

    private boolean canJumpTo(BlockPos pos, int dx, int dz, int scale) {
        BlockPos blockpos = pos.add(dx * scale, 0, dz * scale);
        return this.dakosaurus.world.getFluidState(blockpos).isTagged(FluidTags.WATER) && !this.dakosaurus.world.getBlockState(blockpos).getMaterial().blocksMovement();
    }

    private boolean isAirAbove(BlockPos pos, int dx, int dz, int scale) {
        return this.dakosaurus.world.getBlockState(pos.add(dx * scale, 1, dz * scale)).isAir() && this.dakosaurus.world.getBlockState(pos.add(dx * scale, 2, dz * scale)).isAir();
    }

    @Override
    public boolean shouldContinueExecuting() {
        double d0 = this.dakosaurus.getMotion().y;
        return dakosaurus.jumpCooldown > 0 && (!(d0 * d0 < (double)0.03F) || this.dakosaurus.rotationPitch == 0.0F || !(Math.abs(this.dakosaurus.rotationPitch) < 10.0F) || !this.dakosaurus.isInWater()) && !this.dakosaurus.isOnGround();
    }

    @Override
    public void startExecuting() {
        Direction direction = this.dakosaurus.getAdjustedHorizontalFacing();
        float up = 0.7F + dakosaurus.getRNG().nextFloat() * 0.8F;
        this.dakosaurus.setMotion(this.dakosaurus.getMotion().add((double) direction.getXOffset() * 0.6D, up, (double) direction.getZOffset() * 0.6D));
        this.dakosaurus.getNavigator().clearPath();
        this.dakosaurus.jumpCooldown = dakosaurus.getRNG().nextInt(256) + 256;
    }

    @Override
    public void resetTask() {
        this.dakosaurus.rotationPitch = 0.0F;
    }

    @Override
    public void tick() {
        boolean flag = this.inWater;
        if (!flag) {
            FluidState fluidstate = this.dakosaurus.world.getFluidState(this.dakosaurus.getPosition());
            this.inWater = fluidstate.isTagged(FluidTags.WATER);
        }

        if (this.inWater && !flag) {
            this.dakosaurus.playSound(SoundEvents.ENTITY_DOLPHIN_JUMP, 1.0F, 1.0F);
        }

        Vector3d vector3d = this.dakosaurus.getMotion();
        if (vector3d.y * vector3d.y < (double) 0.1F && this.dakosaurus.rotationPitch != 0.0F) {
            this.dakosaurus.rotationPitch = MathHelper.rotLerp(this.dakosaurus.rotationPitch, 0.0F, 0.2F);
        } else {
            double d0 = Math.sqrt(Entity.horizontalMag(vector3d));
            double d1 = Math.signum(-vector3d.y) * Math.acos(d0 / vector3d.length()) * (double) (180F / (float) Math.PI);
            this.dakosaurus.rotationPitch = (float) d1;
        }
    }
}
