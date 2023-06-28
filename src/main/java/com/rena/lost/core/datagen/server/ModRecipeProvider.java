package com.rena.lost.core.datagen.server;

import com.rena.lost.core.init.BlockInit;
import com.rena.lost.core.init.ItemInit;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        recipes(consumer);
    }

    private void recipes(Consumer<IFinishedRecipe> consumer) {
        ShapedRecipeBuilder.shapedRecipe(ItemInit.ADOBE_BRICK.get(), 8)
                .patternLine("DDD")
                .patternLine("DGD")
                .patternLine("DDD")
                .key('D', Ingredient.fromItems(Items.DIRT, Items.COARSE_DIRT, BlockInit.MUD.get()))
                .key('G', Ingredient.fromItems(Items.GRASS, Items.TALL_GRASS)).addCriterion("has_item",
                        InventoryChangeTrigger.Instance.forItems(Items.GRASS, Items.TALL_GRASS)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.ADOBE_BRICKS.get())
                .key('u', Ingredient.fromItems(ItemInit.ADOBE_BRICK.get()))
                .patternLine("uuu")
                .patternLine("uuu")
                .patternLine("uuu")
                .addCriterion("hasItem", hasItem(ItemInit.ADOBE_BRICK.get())).build(consumer);

    }
}
