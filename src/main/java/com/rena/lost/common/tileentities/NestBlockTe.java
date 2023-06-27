package com.rena.lost.common.tileentities;

import com.rena.lost.common.blocks.util.IEggBlock;
import com.rena.lost.core.init.TileEntityInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NestBlockTe extends TileEntity {


    protected ItemStackHandler inventory = new ItemStackHandler(6) {
        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() instanceof BlockItem && ((BlockItem) stack.getItem()).getBlock() instanceof IEggBlock;
        }
    };

    protected List<Integer> hatchingStates = new ArrayList<>(6);

    public NestBlockTe() {
        super(TileEntityInit.NEST_TE.get());
        for (int i = 0; i < this.inventory.getSlots(); i++) {
            hatchingStates.add(0);
        }
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        readItems(nbt);

        if (nbt.contains("hatchingStates")) {
            CompoundNBT hatchingData = nbt.getCompound("hatchingStates");
            for (int i = 0; hatchingData.contains("hatching" + i); i++) {
                hatchingStates.add(hatchingData.getInt("hatching" + i));
            }
        }


    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        CompoundNBT nbt = super.write(compound);
        nbt = writeItems(nbt);


        CompoundNBT hatchingData = new CompoundNBT();
        for (int i = 0; i < hatchingStates.size(); i++) {
            hatchingData.putInt("hatching" + i, hatchingStates.get(i));
        }
        nbt.put("hatchingStates", hatchingData);

        return nbt;
    }

    public void randomTick(ServerWorld world, Random rand) {
        if (!world.isRemote) {
            for (int i = 0; i < this.inventory.getSlots(); i++) {
                ItemStack stack = this.inventory.getStackInSlot(i);
                if (stack.getItem() instanceof BlockItem) {
                    Block block = ((BlockItem) stack.getItem()).getBlock();
                    if (block instanceof IEggBlock) {

                        if (((IEggBlock) block).canGrow(world, rand)) {
                            this.hatchingStates.set(i, this.hatchingStates.get(i) + 1);
                            System.out.println("test");
                        }
                        if (this.hatchingStates.get(i) >= 2) {
                            resetHatching(world, (IEggBlock) block, i, rand);
                            continue;
                        }
                        if (rand.nextInt(10) <= 9) {
                            if (((IEggBlock) block).canGrow(world, rand)) {
                                this.hatchingStates.set(i, this.hatchingStates.get(i) + 1);
                                System.out.println("test");
                            }
                            if (this.hatchingStates.get(i) >= 2) {
                                resetHatching(world, (IEggBlock) block, i, rand);
                                continue;
                            }
                        }
                    }
                }
            }
        }

    }

    protected CompoundNBT writeItems(CompoundNBT nbt) {
        nbt.put("inventory", this.inventory.serializeNBT());
        return nbt;
    }

    protected void readItems(CompoundNBT nbt) {
        this.inventory.deserializeNBT(nbt.getCompound("inventory"));
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getPos(), 0, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
       this.read(getBlockState(), pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        CompoundNBT nbt = super.getUpdateTag();
        nbt = writeItems(nbt);
        System.out.println("this get executed");
        return nbt;
    }

    public ItemStackHandler getInventory() {
        return inventory;
    }

    public List<Integer> getHatchingStates() {
        return hatchingStates;
    }

    protected void resetHatching(ServerWorld world, IEggBlock egg, int index, Random rand) {
        egg.spawnEntity(getBlockState(), world, getPos(), rand);
        this.hatchingStates.set(index, 0);
        this.inventory.setStackInSlot(index, ItemStack.EMPTY);
        this.world.notifyBlockUpdate(this.getPos(), this.getBlockState(), this.getBlockState(), 3);
    }
}
