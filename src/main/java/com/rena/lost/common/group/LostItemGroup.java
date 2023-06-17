package com.rena.lost.common.group;

import com.rena.lost.core.init.BlockInit;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class LostItemGroup {

    public static final ItemGroup LOST_TAB = new ItemGroup("lostGroup") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(BlockInit.MUD.get());
        }
    };

}
