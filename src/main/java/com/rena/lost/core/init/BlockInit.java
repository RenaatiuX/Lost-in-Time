package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import com.rena.lost.common.blocks.*;
import com.rena.lost.common.blocks.trees.AraucarioxylonTree;
import com.rena.lost.common.group.LostItemGroup;
import com.rena.lost.common.world.gen.tree.ModTreeSpawners;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.OakTree;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

public class BlockInit {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, LostInTime.MOD_ID);

    public static final RegistryObject<Block> NEST_BLOCK = register("nest",
            NestBlock::new, LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> DARK_OAK_BUCKET = register("dark_oak_bucket",
            BlockInit::createResinBucketBlock, LostItemGroup.LOST_TAB);

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
    public static final RegistryObject<Block> AMBER_BLOCK = register("amber_block",
            () -> new AmberBlock(AbstractBlock.Properties.create(Material.CLAY, MaterialColor.ADOBE)
                    .setRequiresTool().hardnessAndResistance(2.0F, 3.0F)
                    .notSolid().setOpaque(BlockInit::isntSolid).setSuffocates(BlockInit::isntSolid).setBlocksVision(BlockInit::isntSolid)
                    .harvestTool(ToolType.PICKAXE).sound(SoundType.BONE)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> ARAUCARIOXYLON_SAPLING = register("araucarioxylon_sapling",
            () -> new BaseSaplingBlock(ModTreeSpawners.ARAUCARIOXYLON, AbstractBlock.Properties.from(Blocks.OAK_SAPLING)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> ARAUCARIOXYLON_PLANKS = register("araucarioxylon_planks",
            () -> new Block(AbstractBlock.Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(2.0F, 3.0F)
                    .sound(SoundType.WOOD)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> ARAUCARIOXYLON_LEAVES = register("araucarioxylon_leaves",
            () -> new LeavesBlock(AbstractBlock.Properties.create(Material.LEAVES)
                    .hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT)
                    .notSolid().setSuffocates(BlockInit::isntSolid).setBlocksVision(BlockInit::isntSolid)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> ARAUCARIOXYLON_LOG = register("araucarioxylon_log",
            () -> new RotatedPillarBlock(AbstractBlock.Properties.create(Material.WOOD)
                    .hardnessAndResistance(2.0F).sound(SoundType.WOOD)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> ARAUCARIOXYLON_DOOR = register("araucarioxylon_door",
            () -> new DoorBlock(AbstractBlock.Properties.create(Material.WOOD, ARAUCARIOXYLON_PLANKS.get().getMaterialColor())
                    .hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid()), LostItemGroup.LOST_TAB);

    public static final RegistryObject<Block> DUCKWEED = BLOCKS.register("duckweed",
            () -> new DuckWeedBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement()
                    .zeroHardnessAndResistance().sound(SoundType.LILY_PADS).notSolid()));
    public static final RegistryObject<Block> ARCHAEFRUCTUS = register("archaefructus",
            () -> new ArchaefructusBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement()
                    .zeroHardnessAndResistance().sound(SoundType.PLANT)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> APIOCRINUS = register("apiocrinus",
            () -> new ApiocrinusBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement()
                    .zeroHardnessAndResistance().sound(SoundType.PLANT)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> CLADOPHLEBIS = register("cladophlebis",
            () -> new LostFlowerPlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement()
                    .zeroHardnessAndResistance().sound(SoundType.PLANT)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> QUILLWORT_1 = register("quillwort_1",
            () -> new QuillwortBlock(AbstractBlock.Properties.create(Material.SEA_GRASS).doesNotBlockMovement()
                    .zeroHardnessAndResistance().sound(SoundType.PLANT)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> QUILLWORT_2 = register("quillwort_2",
            () -> new QuillwortBlock(AbstractBlock.Properties.create(Material.SEA_GRASS).doesNotBlockMovement()
                    .zeroHardnessAndResistance().sound(SoundType.PLANT)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> PELOURDEA = register("pelourdea",
            () -> new DoublePlantBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement()
                    .zeroHardnessAndResistance().sound(SoundType.PLANT)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> SEIROCRINUS = register("seirocrinus",
            () -> new SeirocrinusTopBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT)
                    .doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)
                    , Direction.DOWN, SeirocrinusTopBlock.SHAPE, true, 0.14D), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> SEIROCRINUS_PLANT = BLOCKS.register("seirocrinus_plant",
            () -> new SeirocrinusBlock(AbstractBlock.Properties.create(Material.OCEAN_PLANT)
                    .doesNotBlockMovement().tickRandomly().zeroHardnessAndResistance().sound(SoundType.WET_GRASS)
                    , Direction.DOWN, VoxelShapes.fullCube(), true));
    public static final RegistryObject<Block> WEICHSELIA = register("weichselia",
            () -> new TallFlowerBlock(AbstractBlock.Properties.create(Material.TALL_PLANTS).doesNotBlockMovement()
                    .zeroHardnessAndResistance().sound(SoundType.PLANT)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> ANCIENT_MOSS = register("ancient_moss",
            () -> new Block(AbstractBlock.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F)
                    .sound(SoundType.PLANT).notSolid()), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> CONIOPTERIS = register("coniopteris",
            () -> new LostFlowerPlantBlock(AbstractBlock.Properties.create(Material.PLANTS).doesNotBlockMovement()
                    .zeroHardnessAndResistance().sound(SoundType.PLANT)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> VOIDITE_CRYSTAL = register("voidite_crystal_block",
            () -> new VoiditeCrystalBlock(AbstractBlock.Properties.create(Material.GLASS).notSolid().sound(SoundType.GLASS)
                    .hardnessAndResistance(1.5F)), LostItemGroup.LOST_TAB);
    public static final RegistryObject<Block> VOIDITE_PILLAR = register("voidite_pillar",
            () -> new Block(AbstractBlock.Properties.create(Material.GLASS)
                    .setRequiresTool().hardnessAndResistance(0.8F)), LostItemGroup.LOST_TAB);
    private static boolean isntSolid(BlockState state, IBlockReader reader, BlockPos pos) {
        return false;
    }

    public static ResinBucketBlock createResinBucketBlock() {
        return new ResinBucketBlock(AbstractBlock.Properties.create(Material.WOOD)
                .hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
    }

    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, ItemGroup tab) {
        return register(name, blockSupplier, b -> new BlockItem(b, new Item.Properties().group(tab)));
    }

    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Function<Block, Item> item) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ItemInit.ITEMS.register(name, () -> item.apply(block.get()));
        return block;
    }
}
