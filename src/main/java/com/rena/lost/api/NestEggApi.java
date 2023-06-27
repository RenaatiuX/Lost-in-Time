package com.rena.lost.api;

import com.rena.lost.LostInTime;
import com.rena.lost.common.blocks.util.IEggBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.IModBusEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = LostInTime.MOD_ID)
public class NestEggApi {

    @SubscribeEvent
    public static void commonSetup(FMLCommonSetupEvent event){
        FMLJavaModLoadingContext.get().getModEventBus().post(new EggRegisterEvent(registeredEggs, registeredEggItems));
    }

    protected static Map<Block, IEggBlock> registeredEggs = new HashMap<>();
    protected static Map<Item, Block> registeredEggItems = new HashMap<>();




    public static boolean isValidEgg(Block block){
        return registeredEggs.containsKey(block) || block instanceof IEggBlock;
    }

    public static boolean isValidEgg(Item item){
        if (!(item instanceof BlockItem) && !registeredEggItems.containsKey(item))
            return false;
        if (item instanceof BlockItem)
            return isValidEgg(((BlockItem)item).getBlock());
        return isValidEgg(registeredEggItems.get(item));

    }

    public static IEggBlock getEgg(Item item){
        if (!isValidEgg(item))
            throw new IllegalArgumentException("can only get the IEgg if the item is either BlockItem and the block is an egg or is registered here");
        if (item instanceof BlockItem)
            return getEgg(((BlockItem)item).getBlock());
        return getEgg(registeredEggItems.get(item));
    }

    public static IEggBlock getEgg(Block block){
        if (!isValidEgg(block))
            throw new IllegalArgumentException("can only get the IEgg if the block either impleents IEggBlock or is registered here");
        if (block instanceof IEggBlock)
            return (IEggBlock) block;
        return registeredEggs.get(block);
    }


    public static class EggRegisterEvent extends Event implements IModBusEvent{

        protected final Map<Block, IEggBlock> registeredEggs;
        protected final Map<Item, Block> registeredEggItems;

        public EggRegisterEvent(Map<Block, IEggBlock> registeredEggs, Map<Item, Block> registeredEggItems) {
            this.registeredEggs = registeredEggs;
            this.registeredEggItems = registeredEggItems;
        }

        /**
         * register your custom eggs here
         * all ur custom eggs here has to have a Blockitem and the block has to have a model
         *
         * this will override already existing entries for this block
         * @param egg
         * @param iEgg
         */
        public void registerEgg(Block egg, IEggBlock iEgg){
            registeredEggs.put(egg, iEgg);
        }

        /**
         * this will register an item to its respective block egg
         *this is useful so when u put the egg in the nest it knows what block to display
         *
         * items that got already registered will then be overridden
         * @param item
         * @param block
         */
        public void registerEggItem(Item item, Block block){
            registeredEggItems.put(item, block);
        }


    }


}
