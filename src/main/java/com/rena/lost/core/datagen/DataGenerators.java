package com.rena.lost.core.datagen;

import com.rena.lost.LostInTime;
import com.rena.lost.core.datagen.server.ModBlockTagsProvider;
import com.rena.lost.core.datagen.server.ModItemTagsProvider;
import com.rena.lost.core.datagen.server.ModLootTableProvider;
import com.rena.lost.core.datagen.server.ModRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

import java.io.IOException;

@Mod.EventBusSubscriber(modid = LostInTime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();

        if (event.includeServer())
            gatherServerData(gen, helper);
        try {
            gen.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void gatherServerData(DataGenerator gen, ExistingFileHelper helper) {
        gen.addProvider(new ModLootTableProvider(gen));
        gen.addProvider(new ModRecipeProvider(gen));
        ModBlockTagsProvider blockTags = new ModBlockTagsProvider(gen, helper);
        gen.addProvider(blockTags);
        gen.addProvider(new ModItemTagsProvider(gen, blockTags, helper));
    }
}
