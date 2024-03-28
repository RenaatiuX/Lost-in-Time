package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import com.rena.lost.common.world.surfacebuilder.biomesurface.MarshSurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class SurfaceBuilderInit {

    public static final DeferredRegister<SurfaceBuilder<?>> SURFACE_BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, LostInTime.MOD_ID);
    public static final SurfaceBuilder<SurfaceBuilderConfig> MARSH_SB = new MarshSurfaceBuilder(SurfaceBuilderConfig.CODEC);
    public static final RegistryObject<SurfaceBuilder<SurfaceBuilderConfig>> MARSH = SURFACE_BUILDERS
            .register("marsh", () -> MARSH_SB);

}
