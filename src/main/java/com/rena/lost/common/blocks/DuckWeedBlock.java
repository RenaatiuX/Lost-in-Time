package com.rena.lost.common.blocks;

import com.rena.lost.core.init.BlockInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.BushBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.BoatEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.IForgeShearable;
import net.minecraftforge.common.PlantType;

public class DuckWeedBlock extends BushBlock implements IForgeShearable {
    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 1.5D, 16.0D);
    public DuckWeedBlock(Properties properties) {
        super(properties);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public PlantType getPlantType(IBlockReader world, BlockPos pos) {
        return this == BlockInit.DUCKWEED.get() ? PlantType.WATER : PlantType.PLAINS;
    }

    @Override
    protected boolean isValidGround(BlockState state, IBlockReader worldIn, BlockPos pos) {
        FluidState fluidstate = worldIn.getFluidState(pos);
        FluidState fluidstate1 = worldIn.getFluidState(pos.up());
        return (fluidstate.getFluid() == Fluids.WATER || state.getMaterial() == Material.ICE) && fluidstate1.getFluid() == Fluids.EMPTY;
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if (entityIn instanceof BoatEntity) {
            BoatEntity boat = (BoatEntity) entityIn;
            boat.setMotion(boat.getMotion().mul(0.5, 1, 0.5));
        }
    }
}
