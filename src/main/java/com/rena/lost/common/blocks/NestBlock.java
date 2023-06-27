package com.rena.lost.common.blocks;

import com.rena.lost.LostInTime;
import com.rena.lost.common.container.NestTeContainer;
import com.rena.lost.common.tileentities.NestBlockTe;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.SimpleNamedContainerProvider;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.stream.Stream;

public class NestBlock extends Block {

    private static final VoxelShape SHAPE = Stream.of(
            Block.makeCuboidShape(2, 0, 2, 14, 3, 14),
            Block.makeCuboidShape(2, 3, 2, 14, 6, 2),
            Block.makeCuboidShape(2, 3, 14, 14, 6, 14),
            Block.makeCuboidShape(2, 3, 2, 2, 6, 14),
            Block.makeCuboidShape(14, 3, 2, 14, 6, 14))
            .reduce((v1, v2) -> VoxelShapes.combineAndSimplify(v1, v2, IBooleanFunction.OR)).get();

    public NestBlock() {
        super(AbstractBlock.Properties.create(Material.ORGANIC).notSolid().hardnessAndResistance(3f).tickRandomly());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new NestBlockTe();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof NestBlockTe) {
                NestBlockTe nest = (NestBlockTe) te;
                NetworkHooks.openGui((ServerPlayerEntity) player,
                        new SimpleNamedContainerProvider((id, playerIn, p) -> new NestTeContainer(id, playerIn, nest), new TranslationTextComponent("container." + LostInTime.MOD_ID + ".nest")),
                        pos);
                return ActionResultType.SUCCESS;
            }
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        if (!worldIn.isRemote && !state.matchesBlock(newState.getBlock())) {
            NestBlockTe te = (NestBlockTe) worldIn.getTileEntity(pos);
            for (int i = 0; i < te.getInventory().getSlots(); i++) {
                if (!te.getInventory().getStackInSlot(i).isEmpty())
                    Block.spawnAsEntity(worldIn, pos, te.getInventory().getStackInSlot(i));
            }
        }
        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (te instanceof NestBlockTe) {
                NestBlockTe nest = (NestBlockTe) te;
                nest.randomTick(worldIn, random);
            }
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }
}
