package com.rena.lost.common.blocks;

import net.minecraft.block.Block;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.properties.BlockStateProperties;

public class BucketBlock extends Block {
    public static final IntegerProperty LEVEL = BlockStateProperties.LEVEL_0_3;
    public BucketBlock(Properties properties) {
        super(properties);
        this.setDefaultState(this.stateContainer.getBaseState().with(LEVEL, 0));
    }
}
