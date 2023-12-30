package com.rena.lost.common.blocks;

import com.rena.lost.common.tileentities.LostSignTe;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class LostStandingSignBlock extends StandingSignBlock {
    public LostStandingSignBlock(Properties properties, WoodType type) {
        super(properties, type);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new LostSignTe();
    }
}
