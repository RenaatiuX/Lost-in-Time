package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import com.rena.lost.client.ClientISTERProvider;
import com.rena.lost.common.group.LostItemGroup;
import com.rena.lost.common.items.*;
import com.rena.lost.common.items.util.LostArmorMaterial;
import com.rena.lost.common.items.util.LostItemTier;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemTier;
import net.minecraft.item.SwordItem;
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
    public static final RegistryObject<Item> MIRARCE_EGG = ITEMS.register("mirarce_egg",
            () -> new CustomEggItem(new Item.Properties().group(LostItemGroup.LOST_TAB), EntityInit.MIRARCE::get));
    public static final RegistryObject<Item> PELECANIMIMUS_EGG = ITEMS.register("pelecanimimus_egg",
            () -> new CustomEggItem(new Item.Properties().group(LostItemGroup.LOST_TAB), EntityInit.PELECANIMIMUS::get));
    public static final RegistryObject<Item> ADOBE_BRICK = ITEMS.register("adobe_brick",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> BROWN_CLAY_BALL = ITEMS.register("brown_clay_ball",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> CONCAVENATOR_HUMP = ITEMS.register("concavenator_hump",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> MUD_BALL = ITEMS.register("mud_ball",
            ()-> new MudBallItem(new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> VOIDITE_CRYSTAL = ITEMS.register("voidite_crystal",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> VOIDITE_FRAGMENT = ITEMS.register("voidite_fragment",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> VOIDITE_ORB = ITEMS.register("voidite_orb",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> DUCKWEED = ITEMS.register("duckweed",
            () -> new DuckWeedItem(BlockInit.DUCKWEED.get(), new Item.Properties().group(LostItemGroup.LOST_TAB)));

    //Food
    public static final RegistryObject<Item> RAW_PELECANIMIMUS_MEAT = ITEMS.register("raw_pelecanimimus_meat",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB).food(FoodInit.RAW_PELECANIMIMUS_MEAT)));
    public static final RegistryObject<Item> COOKED_PELECANIMIMUS_MEAT = ITEMS.register("cooked_pelecanimimus_meat",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB).food(FoodInit.COOKED_PELECANIMIMUS_MEAT)));
    public static final RegistryObject<Item> RAW_DAKOSAURUS_MEAT = ITEMS.register("raw_dakosaurus_meat",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB).food(FoodInit.RAW_DAKOSAURUS_MEAT)));
    public static final RegistryObject<Item> COOKED_DAKOSAURUS_MEAT = ITEMS.register("cooked_dakosaurus_meat",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB).food(FoodInit.COOKED_DAKOSAURUS_MEAT)));
    public static final RegistryObject<Item> HYPSOCORMUS = ITEMS.register("hypsocormus",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB).food(FoodInit.HYPSOCORMUS)));
    public static final RegistryObject<Item> COOKED_HYPSOCORMUS = ITEMS.register("cooked_hypsocormus",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB).food(FoodInit.COOKED_HYPSOCORMUS)));
    public static final RegistryObject<Item> TEPEXICHTHYS = ITEMS.register("tepexichthys",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB).food(FoodInit.TEPEXICHTHYS)));
    public static final RegistryObject<Item> COOKED_TEPEXICHTHYS = ITEMS.register("cooked_tepexichthys",
            ()-> new Item(new Item.Properties().group(LostItemGroup.LOST_TAB).food(FoodInit.COOKED_TEPEXICHTHYS)));

    //Armor
    public static final RegistryObject<Item> CONCAVENATOR_MASK =ITEMS.register("concavenator_mask",
            ()-> new ConcavenatorMaskItem(LostArmorMaterial.CONCAVENATOR, EquipmentSlotType.HEAD, new Item.Properties().group(LostItemGroup.LOST_TAB)));

    //Weapons
    public static final RegistryObject<Item> WOODEN_SPEAR_INVENTORY = ITEMS.register("wooden_spear_inventory",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> STONE_SPEAR_INVENTORY = ITEMS.register("stone_spear_inventory",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> IRON_SPEAR_INVENTORY = ITEMS.register("iron_spear_inventory",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GOLD_SPEAR_INVENTORY = ITEMS.register("golden_spear_inventory",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_SPEAR_INVENTORY = ITEMS.register("diamond_spear_inventory",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> NETHERITE_SPEAR_INVENTORY = ITEMS.register("netherite_spear_inventory",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WOODEN_SPEAR = ITEMS.register("wooden_spear",
            () -> new SpearItem(ItemTier.WOOD, new Item.Properties().group(LostItemGroup.LOST_TAB).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("wooden_spear"))));
    public static final RegistryObject<Item> STONE_SPEAR = ITEMS.register("stone_spear",
            () -> new SpearItem(ItemTier.STONE, new Item.Properties().group(LostItemGroup.LOST_TAB).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("stone_spear"))));
    public static final RegistryObject<Item> IRON_SPEAR = ITEMS.register("iron_spear",
            () -> new SpearItem(ItemTier.IRON, new Item.Properties().group(LostItemGroup.LOST_TAB).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("iron_spear"))));
    public static final RegistryObject<Item> GOLD_SPEAR = ITEMS.register("golden_spear",
            () -> new SpearItem(ItemTier.GOLD, new Item.Properties().group(LostItemGroup.LOST_TAB).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("golden_spear"))));
    public static final RegistryObject<Item> DIAMOND_SPEAR = ITEMS.register("diamond_spear",
            () -> new SpearItem(ItemTier.DIAMOND, new Item.Properties().group(LostItemGroup.LOST_TAB).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("diamond_spear"))));
    public static final RegistryObject<Item> NETHERITE_SPEAR = ITEMS.register("netherite_spear",
            () -> new SpearItem(ItemTier.NETHERITE, new Item.Properties().group(LostItemGroup.LOST_TAB).setISTER(
                    () -> () -> ClientISTERProvider.bakeSpearISTER("netherite_spear"))));
    public static final RegistryObject<Item> TOOTH_MACE = ITEMS.register("tooth_mace",
            () -> new SwordItem(LostItemTier.TOOTH_MACE, 3, -3.0F, new Item.Properties().group(LostItemGroup.LOST_TAB)));
    public static final RegistryObject<Item> WOODEN_MACE = ITEMS.register("wooden_mace",
            () -> new SwordItem(LostItemTier.WOODEN_MACE, 3, -3.0F, new Item.Properties().group(LostItemGroup.LOST_TAB)));


}
