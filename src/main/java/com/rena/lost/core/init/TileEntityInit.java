package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import com.rena.lost.common.tileentities.TranquilizerTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {

    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, LostInTime.MOD_ID);

    public static final RegistryObject<TileEntityType<TranquilizerTileEntity>> TRANQUILIZER = TILE_ENTITIES.register("tranquilizer",
            () -> TileEntityType.Builder.create(TranquilizerTileEntity::new, BlockInit.TRANQUILIZER.get()).build(null));

}
