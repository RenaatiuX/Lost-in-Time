package com.rena.lost.core.datagen.server;

import com.rena.lost.LostInTime;
import com.rena.lost.core.init.FluidInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.FluidTagsProvider;
import net.minecraft.data.TagsProvider;
import net.minecraft.fluid.Fluid;
import net.minecraft.tags.FluidTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

import javax.annotation.Nullable;

public class ModFluidTags extends FluidTagsProvider {
    public ModFluidTags(DataGenerator generatorIn, @Nullable ExistingFileHelper existingFileHelper) {
        super(generatorIn, LostInTime.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        TagsProvider.Builder<Fluid> builder = getOrCreateBuilder(FluidTags.WATER);
        FluidInit.FLUIDS.getEntries().stream().map(RegistryObject::get).forEach(builder::add);
    }
}
