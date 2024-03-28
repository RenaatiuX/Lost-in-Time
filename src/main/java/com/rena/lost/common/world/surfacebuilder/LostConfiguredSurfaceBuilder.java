package com.rena.lost.common.world.surfacebuilder;

import com.rena.lost.core.init.BlockInit;
import com.rena.lost.core.init.SurfaceBuilderInit;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilders.ConfiguredSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import java.util.function.Supplier;

public class LostConfiguredSurfaceBuilder {

    public static final Supplier<SurfaceBuilderConfig> MARSH_SB = () -> new SurfaceBuilderConfig(Blocks.COARSE_DIRT.getDefaultState(),
            Blocks.DIRT.getDefaultState(), BlockInit.MUD.get().getDefaultState());

    //Config
    public static final Supplier<ConfiguredSurfaceBuilder<SurfaceBuilderConfig>> MARSH = () -> SurfaceBuilderInit.MARSH.get()
            .func_242929_a(MARSH_SB.get());
}
