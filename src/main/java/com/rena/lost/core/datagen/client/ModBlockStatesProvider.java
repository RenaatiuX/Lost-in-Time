package com.rena.lost.core.datagen.client;


import com.rena.lost.LostInTime;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockStatesProvider extends BlockStateProvider {
    public ModBlockStatesProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, LostInTime.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }

    protected void block(Block... blocks){
        for (Block b : blocks){
            simpleBlock(b);
            simpleBlockItem(b, cubeAll(b));
        }
    }

    protected void blockWithoutBlockItem(Block... blocks){
        for (Block b : blocks){
            simpleBlock(b);
        }
    }
}
