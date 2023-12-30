package com.rena.lost.common.tileentities;

import com.rena.lost.core.init.TileEntityInit;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntityType;

public class LostSignTe extends SignTileEntity {

    public LostSignTe() {
        super();
    }

    @Override
    public TileEntityType<?> getType() {
        return TileEntityInit.SIGN_TE.get();
    }
}
