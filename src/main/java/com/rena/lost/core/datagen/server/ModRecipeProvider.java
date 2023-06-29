package com.rena.lost.core.datagen.server;

import com.rena.lost.LostInTime;
import com.rena.lost.core.init.BlockInit;
import com.rena.lost.core.init.ItemInit;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.block.Blocks;
import net.minecraft.data.*;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.ItemTags;

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
                .key('G', Ingredient.fromItems(Items.GRASS, Items.TALL_GRASS)).addCriterion("hasItem",
                        InventoryChangeTrigger.Instance.forItems(Items.GRASS, Items.TALL_GRASS)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.ADOBE_BRICKS.get())
                .key('u', Ingredient.fromItems(ItemInit.ADOBE_BRICK.get()))
                .patternLine("uu")
                .patternLine("uu")
                .addCriterion("hasItem", hasItem(ItemInit.ADOBE_BRICK.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.ADOBE_SLAB.get(), 6)
                .key('#', Ingredient.fromItems(BlockInit.ADOBE_BRICKS.get()))
                .patternLine("###")
                .addCriterion("hasItem", hasItem(BlockInit.ADOBE_BRICKS.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.ADOBE_STAIRS.get(), 3)
                .key('#', Ingredient.fromItems(BlockInit.ADOBE_BRICKS.get()))
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("hasItem", hasItem(BlockInit.ADOBE_BRICKS.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.ADOBE_WALL.get(), 6)
                .key('#', Ingredient.fromItems(BlockInit.ADOBE_BRICKS.get()))
                .patternLine("###")
                .patternLine("###")
                .addCriterion("hasItem", hasItem(BlockInit.ADOBE_BRICKS.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.BROWN_CLAY.get())
                .key('#', Ingredient.fromItems(ItemInit.BROWN_CLAY_BALL.get()))
                .patternLine("##")
                .patternLine("##")
                .addCriterion("hasItem", hasItem(ItemInit.BROWN_CLAY_BALL.get())).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.BROWN_CLAY_BALL.get(), 4)
                .addIngredient(BlockInit.BROWN_CLAY.get())
                .addCriterion("hasItem", hasItem(BlockInit.BROWN_CLAY.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.MUD.get())
                .key('#', Ingredient.fromItems(ItemInit.MUD_BALL.get()))
                .patternLine("##")
                .patternLine("##")
                .addCriterion("hasItem", hasItem(ItemInit.MUD_BALL.get())).build(consumer);

        ShapelessRecipeBuilder.shapelessRecipe(ItemInit.MUD_BALL.get(), 4)
                .addIngredient(BlockInit.MUD.get())
                .addCriterion("hasItem", hasItem(BlockInit.MUD.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.POLISHED_PURPLE_BRICKS.get())
                .key('#', Ingredient.fromItems(BlockInit.PURPLE_BRICKS.get()))
                .patternLine("##")
                .patternLine("##")
                .addCriterion("hasItem", hasItem(BlockInit.PURPLE_BRICKS.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.PURPLE_BRICKS.get(), 8)
                .key('D', Ingredient.fromItems(Blocks.STONE_BRICKS))
                .key('G', Ingredient.fromItems(ItemInit.VOIDITE_FRAGMENT.get()))
                .patternLine("DDD")
                .patternLine("DGD")
                .patternLine("DDD")
                .addCriterion("hasItem", InventoryChangeTrigger.Instance.forItems(Blocks.STONE_BRICKS, ItemInit.VOIDITE_FRAGMENT.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.PURPLE_BRICKS_SLAB.get(), 6)
                .key('#', Ingredient.fromItems(BlockInit.PURPLE_BRICKS.get()))
                .patternLine("###")
                .addCriterion("hasItem", hasItem(BlockInit.PURPLE_BRICKS.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.PURPLE_BRICKS_STAIRS.get(), 3)
                .key('#', Ingredient.fromItems(BlockInit.PURPLE_BRICKS.get()))
                .patternLine("#  ")
                .patternLine("## ")
                .patternLine("###")
                .addCriterion("hasItem", hasItem(BlockInit.PURPLE_BRICKS.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.PURPLE_BRICKS_PILLAR.get(), 2)
                .key('#', Ingredient.fromItems(BlockInit.PURPLE_BRICKS.get()))
                .patternLine("#")
                .patternLine("#")
                .addCriterion("hasItem", hasItem(BlockInit.PURPLE_BRICKS.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemInit.WOODEN_SPEAR.get())
                .key('#', ItemTags.PLANKS)
                .key('X', Items.STICK)
                .patternLine("#")
                .patternLine("X")
                .patternLine("X")
                .addCriterion("hasItem", hasItem(ItemTags.PLANKS)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemInit.STONE_SPEAR.get())
                .key('#', Blocks.COBBLESTONE)
                .key('X', Items.STICK)
                .patternLine("#")
                .patternLine("X")
                .patternLine("X")
                .addCriterion("hasItem", hasItem(Blocks.COBBLESTONE)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemInit.IRON_SPEAR.get())
                .key('#', Items.IRON_INGOT)
                .key('X', Items.STICK)
                .patternLine("#")
                .patternLine("X")
                .patternLine("X")
                .addCriterion("hasItem", hasItem(Items.IRON_INGOT)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemInit.GOLD_SPEAR.get())
                .key('#', Items.GOLD_INGOT)
                .key('X', Items.STICK)
                .patternLine("#")
                .patternLine("X")
                .patternLine("X")
                .addCriterion("hasItem", hasItem(Items.GOLD_INGOT)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemInit.DIAMOND_SPEAR.get())
                .key('#', Items.DIAMOND)
                .key('X', Items.STICK)
                .patternLine("#")
                .patternLine("X")
                .patternLine("X")
                .addCriterion("hasItem", hasItem(Items.DIAMOND)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemInit.NETHERITE_SPEAR.get())
                .key('#', Items.NETHERITE_INGOT)
                .key('X', Items.STICK)
                .patternLine("#")
                .patternLine("X")
                .patternLine("X")
                .addCriterion("hasItem", hasItem(Items.NETHERITE_INGOT)).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(BlockInit.VOIDITE_BLOCK.get())
                .key('#', Ingredient.fromItems(ItemInit.VOIDITE_ORB.get()))
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("hasItem", hasItem(ItemInit.VOIDITE_ORB.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemInit.VOIDITE_CRYSTAL.get())
                .key('#', Ingredient.fromItems(ItemInit.VOIDITE_FRAGMENT.get()))
                .patternLine("###")
                .patternLine("###")
                .patternLine("###")
                .addCriterion("hasItem", hasItem(ItemInit.VOIDITE_FRAGMENT.get())).build(consumer);

        ShapedRecipeBuilder.shapedRecipe(ItemInit.VOIDITE_ORB.get())
                .key('D', Ingredient.fromItems(ItemInit.VOIDITE_CRYSTAL.get()))
                .key('G', Ingredient.fromItems(Items.DIAMOND))
                .patternLine("DDD")
                .patternLine("DGD")
                .patternLine("DDD")
                .addCriterion("hasItem", hasItem(ItemInit.VOIDITE_CRYSTAL.get())).build(consumer);

        //Cooking
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.RAW_DAKOSAURUS_MEAT.get()), ItemInit.COOKED_DAKOSAURUS_MEAT.get(), 0.35F, 200)
                .addCriterion("hasItem", hasItem(ItemInit.RAW_DAKOSAURUS_MEAT.get())).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.HYPSOCORMUS.get()), ItemInit.COOKED_HYPSOCORMUS.get(), 0.35F, 200)
                .addCriterion("hasItem", hasItem(ItemInit.HYPSOCORMUS.get())).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.RAW_PELECANIMIMUS_MEAT.get()), ItemInit.COOKED_PELECANIMIMUS_MEAT.get(), 0.35F, 200)
                .addCriterion("hasItem", hasItem(ItemInit.RAW_PELECANIMIMUS_MEAT.get())).build(consumer);
        CookingRecipeBuilder.smeltingRecipe(Ingredient.fromItems(ItemInit.TEPEXICHTHYS.get()), ItemInit.COOKED_TEPEXICHTHYS.get(), 0.35F, 200)
                .addCriterion("hasItem", hasItem(ItemInit.TEPEXICHTHYS.get())).build(consumer);

        cookingRecipesForMethod(consumer, "smoking", IRecipeSerializer.SMOKING, 100);
        cookingRecipesForMethod(consumer, "campfire_cooking", IRecipeSerializer.CAMPFIRE_COOKING, 600);

    }

    private void cookingRecipesForMethod(Consumer<IFinishedRecipe> recipeConsumer, String recipeConsumerIn, CookingRecipeSerializer<?> cookingMethod, int serializerIn) {
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(ItemInit.RAW_DAKOSAURUS_MEAT.get()),
                        ItemInit.COOKED_DAKOSAURUS_MEAT.get(), 0.35F, serializerIn, cookingMethod)
                .addCriterion("hasItem", hasItem(ItemInit.RAW_DAKOSAURUS_MEAT.get())).build(recipeConsumer, LostInTime.modLoc("cooked_dakosaurus_meat_from_" + recipeConsumerIn));
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(ItemInit.HYPSOCORMUS.get()),
                        ItemInit.COOKED_HYPSOCORMUS.get(), 0.35F, serializerIn, cookingMethod)
                .addCriterion("hasItem", hasItem(ItemInit.HYPSOCORMUS.get())).build(recipeConsumer, LostInTime.modLoc("cooked_hypsocormus_meat_from_" + recipeConsumerIn));
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(ItemInit.RAW_PELECANIMIMUS_MEAT.get()),
                        ItemInit.COOKED_PELECANIMIMUS_MEAT.get(), 0.35F, serializerIn, cookingMethod)
                .addCriterion("hasItem", hasItem(ItemInit.RAW_PELECANIMIMUS_MEAT.get())).build(recipeConsumer, LostInTime.modLoc("cooked_pelecanimimus_meat_from_" + recipeConsumerIn));
        CookingRecipeBuilder.cookingRecipe(Ingredient.fromItems(ItemInit.TEPEXICHTHYS.get()),
                        ItemInit.COOKED_TEPEXICHTHYS.get(), 0.35F, serializerIn, cookingMethod)
                .addCriterion("hasItem", hasItem(ItemInit.TEPEXICHTHYS.get())).build(recipeConsumer, LostInTime.modLoc("cooked_tepexichthys_meat_from_" + recipeConsumerIn));
    }
}
