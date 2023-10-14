package com.rena.lost.common.fluid;

import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.DyeColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.ForgeFlowingFluid;

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
