package com.rena.lost.common.tileentities;

import com.rena.lost.core.init.TileEntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class AmberTe extends TileEntity {
    private ItemStack storedItem = ItemStack.EMPTY;

    public AmberTe() {
        super(TileEntityInit.AMBER_TE.get());
    }

    public ItemStack getStoredItem() {
        return storedItem;
    }

    public void setStoredItem(ItemStack itemStack) {
        if (itemStack.isEmpty()) {
            storedItem = ItemStack.EMPTY;
        } else {
            storedItem = itemStack.copy();
            storedItem.setCount(1);
        }
        markDirty();
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        compound.put("StoredItem", storedItem.write(new CompoundNBT()));
        return compound;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        storedItem = ItemStack.read(nbt.getCompound("StoredItem"));
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        this.read(state, tag);
    }

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(getBlockState(), pkt.getNbtCompound());
    }

    @Override
    public void remove() {
        super.remove();
        if (!world.isRemote && !storedItem.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, storedItem);
            world.addEntity(itemEntity);
        }
    }
}
