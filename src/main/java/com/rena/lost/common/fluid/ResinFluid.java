package com.rena.lost.common.fluid;

import net.minecraft.block.BlockState;
import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.DyeColor;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.List;

public abstract class ResinFluid extends ForgeFlowingFluid implements IBeaconBeamColorProvider {

    protected ResinFluid(Properties properties) {
        super(properties);
    }

    @Override
    public DyeColor getColor() {
        return null;
    }

    @Override
    public void tick(World worldIn, BlockPos pos, FluidState state) {
        super.tick(worldIn, pos, state);
        if (this.isSource(state)){

        }
    }
}
