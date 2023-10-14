package com.rena.lost.core.init;

import com.rena.lost.LostInTime;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class FluidInit {

    public static final DeferredRegister<Fluid> FLUIDS = DeferredRegister.create(ForgeRegistries.FLUIDS, LostInTime.MOD_ID);
    public static final ResourceLocation WATER_STILL_RL = new ResourceLocation("block/water_still");
    public static final ResourceLocation WATER_FLOWING_RL = new ResourceLocation("block/water_flow");
    public static final ResourceLocation WATER_OVERLAY_RL = new ResourceLocation("block/water_overlay");



    public static final RegistryObject<FlowingFluid> RESIN_FLUID = FLUIDS.register("resin_fluid", () -> new ForgeFlowingFluid.Source(FluidInit.RESIN_PROPERITES));
    public static final RegistryObject<FlowingFluid> RESIN_FLOWING = FLUIDS.register("resin_flowing", () -> new ForgeFlowingFluid.Flowing(FluidInit.RESIN_PROPERITES));

    public static final ForgeFlowingFluid.Properties RESIN_PROPERITES = new ForgeFlowingFluid.Properties(() -> RESIN_FLUID.get(), () -> RESIN_FLOWING.get(), FluidAttributes.builder(WATER_STILL_RL, WATER_FLOWING_RL).density(3000).viscosity(6000).overlay(WATER_OVERLAY_RL).color(0xbffed0d0)).levelDecreasePerBlock(3).slopeFindDistance(2).block(() -> FluidInit.RESIN_BLOCK.get()).bucket(ItemInit.RESIN_BUCKET);

    public static final RegistryObject<FlowingFluidBlock> RESIN_BLOCK = BlockInit.BLOCKS.register("resin", () -> new FlowingFluidBlock(RESIN_FLOWING, AbstractBlock.Properties.create(Material.WATER).doesNotBlockMovement().noDrops().hardnessAndResistance(100f)));


}
