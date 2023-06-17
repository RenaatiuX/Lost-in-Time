package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import com.rena.lost.common.group.LostItemGroup;
import com.rena.lost.common.items.LostFishBucketItem;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemInit {

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, LostInTime.MOD_ID);

    //Spawn Egg
    public static final RegistryObject<Item> SAHONACHELYS_SPAWN_EGG = ITEMS.register("sahonachelys_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.SAHONACHELYS, 8410145, 5002772, new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> DAKOSAURUS_SPAWN_EGG = ITEMS.register("dakosaurus_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.DAKOSAURUS, 7499102, 14866359, new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> DIPLOMOCERAS_SPAWN_EGG = ITEMS.register("diplomoceras_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.DIPLOMOCERAS, 8218967, 13006895, new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> HYPSOCORMUS_SPAWN_EGG = ITEMS.register("hypsocormus_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.HYPSOCORMUS, 4282728, 5517614, new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> TEPEXICHTHYS_SPAWN_EGG = ITEMS.register("tepexichthys_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.TEPEXICHTHYS, 13461760, 7651524, new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> PELECANIMIMUS_SPAWN_EGG = ITEMS.register("pelecanimimus_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.PELECANIMIMUS, 15921906, 7493679, new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> MIRARCE_SPAWN_EGG = ITEMS.register("mirarce_spawn_egg",
            () -> new ForgeSpawnEggItem(EntityInit.MIRARCE, 7887161, 12951925, new Item.Properties().group(LostItemGroup.LOST_TAB)));
    //Items
    public static final RegistryObject<Item> DAKOSAURUS_TOOTH = ITEMS.register("dakosaurus_tooth",
            () -> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> HYPSOCORMUS_BUCKET = ITEMS.register("hypsocormus_bucket",
            ()-> new LostFishBucketItem(EntityInit.HYPSOCORMUS::get, () -> Fluids.WATER, new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> TEPEXICHTHYS_BUCKET = ITEMS.register("tepexichthys_bucket",
            ()-> new LostFishBucketItem(EntityInit.TEPEXICHTHYS::get, () -> Fluids.WATER, new Item.Properties().group(LostItemGroup.LOST_TAB)));
}
