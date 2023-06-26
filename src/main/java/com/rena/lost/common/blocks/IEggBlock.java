package com.rena.lost.common.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public interface IEggBlock {

    /**
     * this will be called when the egg hatches and the baby will be spawned
     */
    void spawnEntity(BlockState state, ServerWorld worldIn, BlockPos pos, Random random);

    /**
     * this defines if the egg can grow
     */
    boolean canGrow(ServerWorld worldIn, Random random);
}
