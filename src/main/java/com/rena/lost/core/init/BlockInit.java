package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import com.rena.lost.common.blocks.DiplomocerasShellBlock;
import com.rena.lost.common.blocks.MudBlock;
import com.rena.lost.common.blocks.TranquilizerBlock;
import com.rena.lost.common.group.LostItemGroup;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
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
    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, ItemGroup tab){
        return register(name, blockSupplier, b -> new BlockItem(b, new Item.Properties().group(tab)));
    }

    public static final <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier, Function<Block, Item> item){
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ItemInit.ITEMS.register(name,  () -> item.apply(block.get()));
        return block;
    }
}
