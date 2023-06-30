package com.rena.lost.core.tag;

import com.rena.lost.LostInTime;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;

public class LostTag {

    public static class Items{

        public static final ITag.INamedTag<Item> RAW_FISHES = mod("raw_fishes");
        public static final ITag.INamedTag<Item> COOKED_FISHES = mod("cooked_fishes");
        public static final ITag.INamedTag<Item> TOOTHS = mod("tooths");

        public static ITag.INamedTag<Item> mod(String name){
            return ItemTags.makeWrapperTag(LostInTime.modLoc(name).toString());
        }
    }

}
