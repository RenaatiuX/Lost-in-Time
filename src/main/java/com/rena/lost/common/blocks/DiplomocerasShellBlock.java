package com.rena.lost.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;
import java.util.stream.Stream;

public class DiplomocerasShellBlock extends HorizontalBlock {
    public static final DirectionProperty FACING = HorizontalBlock.HORIZONTAL_FACING;
    private static final VoxelShape SHAPES_N = Stream.of(
            Block.makeCuboidShape(5, 0, -6.0000000000000036, 11, 6, 21),
            Block.makeCuboidShape(6, 6, -6.0000000000000036, 10, 14, -1.0000000000000036),
            Block.makeCuboidShape(6, 11, -1.0000000000000036, 10, 14, 21),
            Block.makeCuboidShape(8, 7, 12, 8, 11, 21)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    private static final VoxelShape SHAPE_W = Stream.of(
            Block.makeCuboidShape(-6.000000000000002, 0, 4.999999999999998, 21, 6, 10.999999999999998),
            Block.makeCuboidShape(-6.000000000000002, 6, 5.999999999999998, -1.0000000000000018, 14, 9.999999999999998),
            Block.makeCuboidShape(-1.0000000000000018, 11, 5.999999999999998, 21, 14, 9.999999999999998),
            Block.makeCuboidShape(12.000000000000002, 7, 7.999999999999998, 21, 11, 7.999999999999998)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    private static final VoxelShape SHAPE_S = Stream.of(
            Block.makeCuboidShape(5, 0, -5.0000000000000036, 11, 6, 22),
            Block.makeCuboidShape(6, 6, 17, 10, 14, 22),
            Block.makeCuboidShape(6, 11, -5.0000000000000036, 10, 14, 17),
            Block.makeCuboidShape(8, 7, -5.0000000000000036, 8, 11, 3.9999999999999964)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    private static final VoxelShape SHAPE_E = Stream.of(
            Block.makeCuboidShape(-5.000000000000002, 0, 4.999999999999998, 22, 6, 10.999999999999998),
            Block.makeCuboidShape(17, 6, 5.999999999999998, 22, 14, 9.999999999999998),
            Block.makeCuboidShape(-5.000000000000002, 11, 5.999999999999998, 17, 14, 9.999999999999998),
            Block.makeCuboidShape(-5.000000000000002, 7, 7.999999999999998, 3.9999999999999982, 11, 7.999999999999998)
    ).reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public DiplomocerasShellBlock(Properties builder) {
        super(builder);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        switch (state.get(FACING)){
            case WEST:
                return SHAPE_W;
            case SOUTH:
                return SHAPE_S;
            case EAST:
                return SHAPE_E;
            default:
                return SHAPES_N;
        }
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(FACING, context.getPlacementHorizontalFacing().getOpposite());
    }
}
