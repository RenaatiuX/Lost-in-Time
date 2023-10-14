package com.rena.lost.core.datagen.server;

import com.rena.lost.LostInTime;
import com.rena.lost.core.init.FluidInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.data.LanguageProvider;
import net.minecraftforge.fml.RegistryObject;

public class ModLanguageProvider extends LanguageProvider {
    public ModLanguageProvider(DataGenerator gen) {
        super(gen, LostInTime.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        //add("fluid.lost.resin_block", "Resin");
    }

    public void add(ItemGroup group, String key) {
        add(group.getGroupName().getString(), key);
    }

    public void add(RegistryObject<Biome> obj) {
        add("biome." + LostInTime.MOD_ID + "." + obj.getId().getPath(), toTitleCase(obj.getId().getPath()));
    }

    public void add(ITextComponent component, String name) {
        add(component.getString(), name);
    }


    public static String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (c == '_') {
                nextTitleCase = true;
                titleCase.append(" ");
                continue;
            }

            if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
