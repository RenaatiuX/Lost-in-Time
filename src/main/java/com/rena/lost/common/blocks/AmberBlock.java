package com.rena.lost.common.blocks;

import com.rena.lost.common.tileentities.AmberTe;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class AmberBlock extends Block {
    public AmberBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AmberTe();
    }

    @Override
    public VoxelShape getRayTraceShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote) {
            TileEntity tileEntity = world.getTileEntity(pos);
            if (tileEntity instanceof AmberTe) {
                AmberTe amberTe = (AmberTe) tileEntity;
                ItemStack heldItem = player.getHeldItem(hand);

                if (heldItem.isEmpty()) {
                    // Si la mano principal está vacía, retirar el item
                    ItemStack storedItem = amberTe.getStoredItem();
                    if (!storedItem.isEmpty()) {
                        amberTe.setStoredItem(ItemStack.EMPTY);
                        player.setHeldItem(hand, storedItem);
                    }
                } else {
                    // Si se tiene un item en la mano principal, colocarlo en el AmberTe
                    if (amberTe.getStoredItem().isEmpty()) {
                        ItemStack copiedItem = heldItem.copy();
                        copiedItem.setCount(1);
                        amberTe.setStoredItem(copiedItem);
                        heldItem.shrink(1);
                    }
                }
            }
        }

        return ActionResultType.SUCCESS;
    }


    @Override
    public float getAmbientOcclusionLightValue(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return 1.0F;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader reader, BlockPos pos) {
        return true;
    }

    @Override
    public boolean isSideInvisible(BlockState state, BlockState adjacentBlockState, Direction side) {
        return adjacentBlockState.matchesBlock(this) ? true : super.isSideInvisible(state, adjacentBlockState, side);
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }
}
