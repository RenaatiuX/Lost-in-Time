package com.rena.lost.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import java.util.stream.Stream;

public class ResinBucketBlock extends Block {
    protected static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(13, 0, 3, 15, 14, 13),
            Block.makeCuboidShape(1, 0, 3, 3, 14, 13),
            Block.makeCuboidShape(1, 0.025, 1, 15, 0.025, 15),
            Block.makeCuboidShape(1, 0, 1, 15, 14, 3.0000000000000018),
            Block.makeCuboidShape(1, 0, 12.999999999999998, 15, 14, 15)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_3;

    public ResinBucketBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(LEVEL, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(LEVEL);
    }
}
