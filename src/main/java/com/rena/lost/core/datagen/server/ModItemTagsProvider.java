package com.rena.lost.core.datagen.server;

import com.rena.lost.LostInTime;
import com.rena.lost.core.tag.LostTag;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;

import javax.annotation.Nullable;

public class ModItemTagsProvider extends ItemTagsProvider {
    public ModItemTagsProvider(DataGenerator p_i244817_1_, BlockTagsProvider p_i244817_2_, @Nullable ExistingFileHelper p_i244817_4_) {
        super(p_i244817_1_, p_i244817_2_, LostInTime.MOD_ID, p_i244817_4_);
    }

    @Override
    protected void registerTags() {
        //add more food here
        getOrCreateBuilder(LostTag.Items.RAW_FISHES).add(Items.SALMON).add(Items.COD).add(Items.TROPICAL_FISH).add(Items.PUFFERFISH);
        getOrCreateBuilder(LostTag.Items.COOKED_FISHES).add(Items.COOKED_SALMON).add(Items.COOKED_COD);
    }
}
