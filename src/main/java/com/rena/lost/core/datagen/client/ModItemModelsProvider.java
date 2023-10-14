package com.rena.lost.core.datagen.client;


import com.rena.lost.LostInTime;
import com.rena.lost.core.init.ItemInit;
import net.minecraft.data.DataGenerator;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.RegistryObject;

public class ModItemModelsProvider extends ItemModelProvider {

    public final ModelFile generated = getExistingFile(mcLoc("item/generated"));
    public final ModelFile spawnEgg = getExistingFile(mcLoc("item/template_spawn_egg"));

    public ModItemModelsProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, LostInTime.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        ItemInit.ITEMS.getEntries().stream().map(RegistryObject::get).forEach(r -> {
            if (r instanceof SpawnEggItem){
                spawnEgg(r);
            }else{
                simple(r);
            }
        });
    }

    private void simple(Item... items) {
        for (Item item : items) {
            String name = item.getRegistryName().getPath();
            if (!existingFileHelper.exists(extendWithFolder(name.contains(":") ? new ResourceLocation(name) : new ResourceLocation(modid, name)), MODEL))
                getBuilder(name).parent(generated).texture("layer0", "item/" + name);
        }
    }

    private void simple(IItemProvider... items){
        for (IItemProvider itemProvider : items){
            simple(itemProvider.asItem());
        }
    }

    private void spawnEgg(Item... items) {
        for (Item item : items) {
            String name = item.getRegistryName().getPath();
            if (!existingFileHelper.exists(extendWithFolder(name.contains(":") ? new ResourceLocation(name) : new ResourceLocation(modid, name)), MODEL))
                getBuilder(name).parent(spawnEgg);
        }
    }

    protected ResourceLocation extendWithFolder(ResourceLocation rl) {
        if (rl.getPath().contains("/")) {
            return rl;
        }
        return new ResourceLocation(rl.getNamespace(), folder + "/" + rl.getPath());
    }
}
