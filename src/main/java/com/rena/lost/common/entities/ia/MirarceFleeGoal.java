package com.rena.lost.common.entities.ia;

import com.rena.lost.common.entities.Mirarce;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.gen.Heightmap;

import java.util.EnumSet;

public class MirarceFleeGoal extends Goal {

    private Mirarce mirarce;
    private BlockPos currentTarget = null;
    private int executionTime = 0;

    public MirarceFleeGoal(Mirarce mirarce) {
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
        this.mirarce = mirarce;
    }

    @Override
    public void resetTask() {
        currentTarget = null;
        executionTime = 0;
        mirarce.setFlying(false);
    }

    @Override
    public boolean shouldContinueExecuting() {
        return mirarce.isFlying() && (executionTime < 15 || !mirarce.isOnGround());
    }

    @Override
    public boolean shouldExecute() {
        return mirarce.panicTicks > 0 && mirarce.isOnGround();
    }

    @Override
    public void startExecuting() {
        if (mirarce.isOnGround()) {
            mirarce.setFlying(true);
        }
    }

    @Override
    public void tick() {
        executionTime++;
        if (currentTarget == null) {
            if (mirarce.panicTicks == 0) {
                currentTarget = getBlockGrounding(mirarce.getPositionVec());
            } else {
                currentTarget = getBlockInViewAway(mirarce.getPositionVec());
            }
        }
        if (currentTarget != null) {
            mirarce.getNavigator().tryMoveToXYZ(currentTarget.getX() + 0.5F, currentTarget.getY() + 0.5F, currentTarget.getZ() + 0.5F, 1F);
            if (this.mirarce.getDistanceSq(Vector3d.copyCentered(currentTarget)) < 4) {
                currentTarget = null;
            }
        }
        if (mirarce.panicTicks == 0 && (mirarce.isOnGround() || !mirarce.world.isAirBlock(mirarce.getPosition().down()))) {
            resetTask();
            mirarce.setFlying(false);
        }
    }

    public BlockPos getBlockInViewAway(Vector3d fleePos) {
        float radius = 0.75F * (0.7F * 6) * -3 - mirarce.getRNG().nextInt(24);
        float neg = mirarce.getRNG().nextBoolean() ? 1 : -1;
        float renderYawOffset = mirarce.renderYawOffset;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (mirarce.getRNG().nextFloat() * neg);
        double extraX = radius * MathHelper.sin((float) (Math.PI + angle));
        double extraZ = radius * MathHelper.cos(angle);
        BlockPos radialPos = new BlockPos(fleePos.getX() + extraX, 0, fleePos.getZ() + extraZ);
        BlockPos ground = mirarce.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, radialPos);
        int distFromGround = (int) mirarce.getPosY() - ground.getY();
        int flightHeight = 4 + mirarce.getRNG().nextInt(10);
        BlockPos newPos = radialPos.up(distFromGround > 8 ? flightHeight : (int) mirarce.getPosY() + mirarce.getRNG().nextInt(6) + 1);
        if (!mirarce.isTargetBlocked(Vector3d.copyCentered(newPos)) && mirarce.getDistanceSq(Vector3d.copyCentered(newPos)) > 6) {
            return newPos;
        }
        return null;
    }

    public BlockPos getBlockGrounding(Vector3d fleePos) {
        float radius = 0.75F * (0.7F * 6) * -3 - mirarce.getRNG().nextInt(24);
        float neg = mirarce.getRNG().nextBoolean() ? 1 : -1;
        float renderYawOffset = mirarce.renderYawOffset;
        float angle = (0.01745329251F * renderYawOffset) + 3.15F + (mirarce.getRNG().nextFloat() * neg);
        double extraX = radius * MathHelper.sin((float) (Math.PI + angle));
        double extraZ = radius * MathHelper.cos(angle);
        BlockPos radialPos = new BlockPos(fleePos.getX() + extraX, 0, fleePos.getZ() + extraZ);
        BlockPos ground = mirarce.world.getHeight(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, radialPos);
        if (!mirarce.isTargetBlocked(Vector3d.copyCentered(ground.up()))) {
            return ground;
        }
        return null;
    }
}
