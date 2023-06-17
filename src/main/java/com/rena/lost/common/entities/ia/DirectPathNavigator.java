package com.rena.lost.common.entities.ia;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.pathfinding.GroundPathNavigator;
import net.minecraft.world.World;

public class DirectPathNavigator extends GroundPathNavigator {

    private MobEntity mob;

    public DirectPathNavigator(MobEntity mob, World world) {
        super(mob, world);
        this.mob = mob;
    }

    @Override
    public void tick() {
        ++this.totalTicks;
    }

    @Override
    public boolean tryMoveToXYZ(double x, double y, double z, double speedIn) {
        mob.getMoveHelper().setMoveTo(x, y, z, speedIn);
        return true;
    }

    @Override
    public boolean tryMoveToEntityLiving(Entity entityIn, double speedIn) {
        mob.getMoveHelper().setMoveTo(entityIn.getPosX(), entityIn.getPosY(), entityIn.getPosZ(), speedIn);
        return true;
    }
}
