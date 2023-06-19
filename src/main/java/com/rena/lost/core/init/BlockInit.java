package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import com.rena.lost.common.blocks.*;
import com.rena.lost.common.group.LostItemGroup;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LostInTime.MOD_ID);

    public static final RegistryObject<Block> TRANQUILIZER = register("tranquilizer",
            () -> new TranquilizerBlock(AbstractBlock.Properties.create(Material.IRON)
                    .hardnessAndResistance(0.6F).sound(SoundType.METAL).setRequiresTool()
                    .harvestLevel(1).harvestTool(ToolType.PICKAXE)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> MUD = register("mud",
            () -> new MudBlock(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.BROWN)
                    .hardnessAndResistance(0.6F).sound(SoundType.GROUND)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> DIPLOMOCERAS_SHELL = register("diplomoceras_shell",
            () -> new DiplomocerasShellBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.SAND)
                    .setRequiresTool().hardnessAndResistance(2.0F).sound(SoundType.BONE).harvestLevel(0)
                    .harvestTool(ToolType.PICKAXE).notSolid()), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> SAHONACHELYS_EGG = register("sahonachelys_egg",
            () -> new SahonachelysEggBlock(AbstractBlock.Properties.create(Material.DRAGON_EGG, MaterialColor.SAND)
                    .hardnessAndResistance(0.5F).sound(SoundType.METAL).tickRandomly().notSolid()), LostItemGroup.LOST_TAB);

    public static final RegistryObject<Block> ADOBE_BRICKS = register("adobe_bricks",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE)
                    .setRequiresTool().hardnessAndResistance(1.0F, 3.0F).harvestTool(ToolType.PICKAXE).harvestLevel(0)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> ADOBE_SLAB = register("adobe_slab",
            () -> new SlabBlock(AbstractBlock.Properties.from(BlockInit.ADOBE_BRICKS.get())), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> ADOBE_STAIRS = register("adobe_stairs",
            () -> new StairsBlock(() -> ADOBE_BRICKS.get().getDefaultState(), AbstractBlock.Properties.from(ADOBE_BRICKS.get())), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> ADOBE_WALL = register("adobe_wall",
            () -> new WallBlock(AbstractBlock.Properties.from(ADOBE_BRICKS.get())), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> BROWN_CLAY = register("brown_clay",
            () -> new Block(AbstractBlock.Properties.create(Material.CLAY, MaterialColor.BROWN)
                    .hardnessAndResistance(0.6F).sound(SoundType.GROUND)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> POLISHED_PURPLE_BRICKS = register("polished_purple_bricks",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PURPLE)
                    .setRequiresTool().hardnessAndResistance(1.5F, 6.0F)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> PURPLE_BRICKS = register("purple_bricks",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PURPLE)
                    .setRequiresTool().hardnessAndResistance(1.5F, 6.0F)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> PURPLE_BRICKS_SLAB = register("purple_bricks_slab",
            () -> new SlabBlock(AbstractBlock.Properties.from(BlockInit.PURPLE_BRICKS.get())), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> PURPLE_BRICKS_STAIRS = register("purple_bricks_stairs",
            () -> new StairsBlock(() -> PURPLE_BRICKS.get().getDefaultState(), AbstractBlock.Properties.from(PURPLE_BRICKS.get())), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> PURPLE_BRICKS_PILLAR = register("purple_bricks_pillar",
            () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.PURPLE)
                    .setRequiresTool().hardnessAndResistance(1.5F, 6.0F)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> VOIDITE_ORE = register("voidite_ore",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool()
                    .hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE)
                    .harvestLevel(2)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> VOIDITE_BLOCK = register("voidite_block",
            () -> new Block(AbstractBlock.Properties.create(Material.ROCK).setRequiresTool()
                    .hardnessAndResistance(3.0F, 3.0F).harvestTool(ToolType.PICKAXE)
                    .harvestLevel(2)), LostItemGroup.LOST_TAB);

    public static final RegistryObject<Block> DUCKWEED = BLOCKS.register("duckweed",
            () -> new DuckWeedBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement()
                    .zeroHardnessAndResistance().sound(SoundType.LILY_PADS).notSolid()));

    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, ItemGroup tab){
        return register(name, blockSupplier, b -> new BlockItem(b, new Item.Properties().group(tab)));
    }

    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Function<Block, Item> item){
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ItemInit.ITEMS.register(name,  () -> item.apply(block.get()));
        return block;
    }
}
