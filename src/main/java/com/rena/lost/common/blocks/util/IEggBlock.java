package com.rena.lost.common.blocks.util;

import net.minecraft.block.BlockState;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
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

    /**
     *
     * @return the sound the egg wil make when growing older
     */
    default SoundEvent getEggAgingSound(){
        return SoundEvents.ENTITY_TURTLE_EGG_HATCH;
    }

    /**
     *
     * @return the sound that the egg will make when hatching into an entity
     */
    default SoundEvent getHatchingSound(){
        return getEggAgingSound();
    }
}
