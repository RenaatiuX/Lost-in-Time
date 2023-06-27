package com.rena.lost.common.container;

import com.rena.lost.common.tileentities.NestBlockTe;
import com.rena.lost.core.init.ContainerInit;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

public class NestTeContainer extends Container {

    protected final NestBlockTe te;
    protected final PlayerInventory playerInv;
    protected final IWorldPosCallable canInteractWithCallable;

    public NestTeContainer(int id, PlayerInventory inventory, NestBlockTe te) {
        super(ContainerInit.NEST_CONTAINER.get(), id);
        this.te = te;
        this.playerInv = inventory;
        this.canInteractWithCallable = IWorldPosCallable.of(te.getWorld(), te.getPos());
        init();
    }

    public NestTeContainer(int id, PlayerInventory inventory, PacketBuffer buffer) {
        this(id, inventory, getClientTileEntity(inventory, buffer));
    }

    protected void init() {
        addPlayerInventory(8, 51);

        addHorizontalSlots(te.getInventory(), 0, 37, 20, 6, 18);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(this.canInteractWithCallable, playerIn, te.getBlockState().getBlock());
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity playerIn, int index) {
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        int teInvSize = te.getInventory().getSlots();
        if (slot != null && slot.getHasStack()) {
            ItemStack stack1 = slot.getStack();
            stack = stack1.copy();
            System.out.println(index);
            if (index < teInvSize && !this.mergeItemStack(stack1, teInvSize, this.inventorySlots.size(), true))
                return ItemStack.EMPTY;
            if (!this.mergeItemStack(stack1, 0, teInvSize - 1, false))
                return ItemStack.EMPTY;
            slot.onSlotChange(stack1, stack);
            if (stack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
            slot.onTake(playerIn, stack1);
        }
        return stack;
    }

    private static NestBlockTe getClientTileEntity(PlayerInventory inventory, PacketBuffer buffer) {
        return (NestBlockTe) inventory.player.world.getTileEntity(buffer.readBlockPos());
    }

    protected int addHorizontalSlots(IInventory handler, int Index, int x, int y, int amount,
                                     int distanceBetweenSlots) {
        for (int i = 0; i < amount; i++) {
            addSlot(new Slot(handler, Index, x, y));
            Index++;
            x += distanceBetweenSlots;
        }
        return Index;
    }

    protected int addHorizontalSlots(IItemHandler handler, int Index, int x, int y, int amount,
                                     int distanceBetweenSlots) {
        for (int i = 0; i < amount; i++) {
            addSlot(new SlotItemHandler(handler, Index, x, y));
            Index++;
            x += distanceBetweenSlots;
        }
        return Index;
    }

    protected int addHorizontalSlots(IItemHandler handler, int Index, int x, int y, int amount,
                                     int distanceBetweenSlots, IItemHandlerSlotProvider provider) {
        for (int i = 0; i < amount; i++) {
            addSlot(provider.createSlot(handler, Index, x, y));
            Index++;
            x += distanceBetweenSlots;
        }
        return Index;
    }

    protected int addSlotField(IInventory handler, int StartIndex, int x, int y, int horizontalAmount,
                               int horizontalDistance, int verticalAmount, int VerticalDistance) {
        for (int i = 0; i < verticalAmount; i++) {
            StartIndex = addHorizontalSlots(handler, StartIndex, x, y, horizontalAmount, horizontalDistance);
            y += VerticalDistance;
        }
        return StartIndex;
    }

    protected void addPlayerInventory(int x, int y) {
        // the Rest
        addSlotField(playerInv, 9, x, y, 9, 18, 3, 18);
        y += 58;
        // Hotbar
        addHorizontalSlots(playerInv, 0, x, y, 9, 18);
    }

    public static interface IItemHandlerSlotProvider {
        Slot createSlot(IItemHandler handler, int index, int x, int y);
    }
}
