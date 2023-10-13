package com.rena.lost.common.fluid;

import net.minecraft.block.IBeaconBeamColorProvider;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.DyeColor;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class ResinFluid extends ForgeFlowingFluid implements IBeaconBeamColorProvider {

    protected ResinFluid(Properties properties) {
        super(properties);
    }

    @Override
    public DyeColor getColor() {
        return null;
    }

    @Override
    public boolean isSource(FluidState state) {
        return false;
    }

    @Override
    public int getLevel(FluidState state) {
        return 0;
    }
}
