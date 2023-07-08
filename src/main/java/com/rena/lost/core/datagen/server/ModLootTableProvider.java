package com.rena.lost.core.datagen.server;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import com.rena.lost.core.init.BlockInit;
import com.rena.lost.core.init.EntityInit;
import com.rena.lost.core.init.ItemInit;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.data.loot.BlockLootTables;
import net.minecraft.data.loot.EntityLootTables;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.loot.*;
import net.minecraft.loot.conditions.EntityHasProperty;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.loot.functions.LootingEnchantBonus;
import net.minecraft.loot.functions.SetCount;
import net.minecraft.loot.functions.Smelt;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ModLootTableProvider extends LootTableProvider {
    public ModLootTableProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        return ImmutableList.of(Pair.of(EntityLootTable::new, LootParameterSets.ENTITY), Pair.of(BlockLootTable::new, LootParameterSets.BLOCK));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, ValidationTracker validationtracker) {
        map.forEach((id, table) -> LootTableManager.validateLootTable(validationtracker, id, table));
    }

    public static class EntityLootTable extends EntityLootTables {
        private List<EntityType<?>> knownEntities = Lists.newArrayList();
        @Override
        protected void addTables() {
            registerLootTable(EntityInit.SAHONACHELYS.get(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.FERN).acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F))))));
            registerLootTable(EntityInit.DAKOSAURUS.get(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(ItemInit.RAW_DAKOSAURUS_MEAT.get()).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F))).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, ON_FIRE))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(ItemInit.DAKOSAURUS_TOOTH.get()).acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 2.0F))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F))))));
            registerLootTable(EntityInit.DIPLOMOCERAS.get(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(BlockInit.DIPLOMOCERAS_SHELL.get()).acceptFunction(SetCount.builder(RandomValueRange.of(0.0F, 1.0F))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F))))));
            registerLootTable(EntityInit.HYPSOCORMUS.get(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(ItemInit.HYPSOCORMUS.get()).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, ON_FIRE))))));
            registerLootTable(EntityInit.TEPEXICHTHYS.get(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(ItemInit.TEPEXICHTHYS.get()).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, ON_FIRE))))));
            registerLootTable(EntityInit.MIRARCE.get(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.FEATHER).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F))))));
            registerLootTable(EntityInit.PELECANIMIMUS.get(), LootTable.builder().addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(Items.FEATHER).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 3.0F))).acceptFunction(LootingEnchantBonus.builder(RandomValueRange.of(0.0F, 1.0F))))).addLootPool(LootPool.builder().rolls(ConstantRange.of(1)).addEntry(ItemLootEntry.builder(ItemInit.RAW_PELECANIMIMUS_MEAT.get()).acceptFunction(SetCount.builder(RandomValueRange.of(1.0F, 2.0F))).acceptFunction(Smelt.func_215953_b().acceptCondition(EntityHasProperty.builder(LootContext.EntityTarget.THIS, ON_FIRE))))));
        }

        @Override
        protected Iterable<EntityType<?>> getKnownEntities() {
            return this.knownEntities;
        }

        @Override
        protected void registerLootTable(EntityType<?> type, LootTable.Builder table) {
            super.registerLootTable(type, table);
            this.knownEntities.add(type);
        }
    }

    public static class BlockLootTable extends BlockLootTables {
        private List<Block> knownBlocks = Lists.newArrayList();

        @Override
        protected void addTables() {
            this.registerDropSelfLootTable(BlockInit.ADOBE_BRICKS.get());
            this.registerLootTable(BlockInit.ADOBE_SLAB.get(), BlockLootTables::droppingSlab);
            this.registerDropSelfLootTable(BlockInit.ADOBE_STAIRS.get());
            this.registerDropSelfLootTable(BlockInit.ADOBE_WALL.get());
            this.registerDropSelfLootTable(BlockInit.AMBER_BLOCK.get());
            this.registerSilkTouch(BlockInit.SAHONACHELYS_EGG.get());
            this.registerLootTable(BlockInit.APIOCRINUS.get(), BlockLootTables::onlyWithShears);
            this.registerLootTable(BlockInit.ARCHAEFRUCTUS.get(), BlockLootTables::onlyWithShears);
            this.registerDropSelfLootTable(BlockInit.BROWN_CLAY.get());
            this.registerLootTable(BlockInit.CLADOPHLEBIS.get(), BlockLootTables::onlyWithShears);
            this.registerDropSelfLootTable(BlockInit.DIPLOMOCERAS_SHELL.get());
            this.registerLootTable(BlockInit.DUCKWEED.get(), BlockLootTables::onlyWithShears);
            this.registerDropSelfLootTable(BlockInit.MUD.get());
            this.registerDropSelfLootTable(BlockInit.NEST_BLOCK.get());
            this.registerDropSelfLootTable(BlockInit.POLISHED_PURPLE_BRICKS.get());
            this.registerDropSelfLootTable(BlockInit.PURPLE_BRICKS.get());
            this.registerLootTable(BlockInit.PURPLE_BRICKS_SLAB.get(), BlockLootTables::droppingSlab);
            this.registerDropSelfLootTable(BlockInit.PURPLE_BRICKS_STAIRS.get());
            this.registerDropSelfLootTable(BlockInit.PURPLE_BRICKS_PILLAR.get());
            this.registerLootTable(BlockInit.QUILLWORT_1.get(), BlockLootTables::onlyWithShears);
            this.registerLootTable(BlockInit.QUILLWORT_2.get(), BlockLootTables::onlyWithShears);
            this.registerDropSelfLootTable(BlockInit.VOIDITE_BLOCK.get());
            this.registerLootTable(BlockInit.VOIDITE_ORE.get(), (voidite) ->
                    droppingItemWithFortune(voidite, ItemInit.VOIDITE_FRAGMENT.get()));
            this.registerLootTable(BlockInit.WEICHSELIA.get(), BlockLootTables::onlyWithShears);
            this.registerDropSelfLootTable(BlockInit.ANCIENT_MOSS.get());
            this.registerLootTable(BlockInit.CONIOPTERIS.get(), BlockLootTables::onlyWithShears);
        }


        @Override
        protected Iterable<Block> getKnownBlocks() {
            return this.knownBlocks;
        }

        @Override
        protected void registerLootTable(Block blockIn, LootTable.Builder table) {
            super.registerLootTable(blockIn, table);
            this.knownBlocks.add(blockIn);
        }
    }
}
