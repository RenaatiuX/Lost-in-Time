package com.rena.lost.common.tileentities;

import com.rena.lost.core.init.TileEntityInit;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;

import java.util.List;

public class TranquilizerTileEntity extends TileEntity implements ITickableTileEntity {
    public TranquilizerTileEntity(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public TranquilizerTileEntity() {
        this(TileEntityInit.TRANQUILIZER.get());
    }

    @Override
    public void tick() {
        if (this.world != null && !this.world.isRemote) {
            BlockPos rangeStart = pos.add(-4, -4, -4);

            AxisAlignedBB range = new AxisAlignedBB(rangeStart, rangeStart.add(7, 7, 7));

            List<LivingEntity> entities = this.world.getEntitiesWithinAABB(LivingEntity.class, range);

            for (LivingEntity entity : entities) {
                if (entity instanceof CreatureEntity) {
                    CreatureEntity creature = (CreatureEntity) entity;
                    creature.setAttackTarget(null);
                }
            }
        }
    }
}
