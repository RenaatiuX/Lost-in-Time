package com.rena.lost.common.events;

import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.Sahonachelys;
import com.rena.lost.core.init.BlockInit;
import com.rena.lost.core.init.EffectInit;
import com.rena.lost.core.init.FluidInit;
import com.rena.lost.core.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CauldronBlock;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Mod.EventBusSubscriber(modid = LostInTime.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ServerEvents {

    @SubscribeEvent
    public static void onApplyCriticalHit(CriticalHitEvent event) {
        if (event.getPlayer() != null && event.isVanillaCritical()) {
            PlayerEntity player = event.getPlayer();
            ItemStack heldItem = player.getHeldItemMainhand();
            if (heldItem.getItem() == ItemInit.TOOTH_MACE.get()) {
                LivingEntity target = (LivingEntity) event.getTarget();
                target.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 60, 0, false, true));
                player.getCooldownTracker().setCooldown(heldItem.getItem(), 35);
            } else if (heldItem.getItem() == ItemInit.WOODEN_MACE.get()) {
                LivingEntity target = (LivingEntity) event.getTarget();
                target.addPotionEffect(new EffectInstance(Effects.WEAKNESS, 60, 0, false, true));
                player.getCooldownTracker().setCooldown(heldItem.getItem(), 40);
            }
        }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        if (event.getState().getBlock() == BlockInit.ANCIENT_MOSS.get()) {
            ItemStack itemStack = event.getPlayer().getHeldItemMainhand();
            if (itemStack.getItem() == Items.SHEARS) {
                event.setNewSpeed(event.getOriginalSpeed() * 5.0F);
            }
        }
    }

    @SubscribeEvent
    public void onPlayerInteract(PlayerInteractEvent.RightClickBlock event) {
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        BlockState state = world.getBlockState(pos);
        PlayerEntity player = event.getPlayer();
        ItemStack heldItem = player.getHeldItem(event.getHand());

        if (state.getBlock() == Blocks.CAULDRON) {
            if (heldItem.getItem() == ItemInit.RESIN_BUCKET.get()) {
                world.setBlockState(pos, Blocks.CAULDRON.getDefaultState().with(CauldronBlock.LEVEL, 3));
                if (!player.abilities.isCreativeMode) {
                    heldItem.shrink(1);
                }
                event.setCancellationResult(ActionResultType.SUCCESS);
                event.setCanceled(true);
            }
        } else if (state.getBlock() == Blocks.CAULDRON && state.get(CauldronBlock.LEVEL) == 3) {
            if (heldItem.getItem() == Items.BUCKET) {
                world.setBlockState(pos, Blocks.CAULDRON.getDefaultState());
                if (!player.abilities.isCreativeMode) {
                    heldItem.shrink(1);
                    player.inventory.addItemStackToInventory(new ItemStack(ItemInit.RESIN_BUCKET.get()));
                }
                event.setCancellationResult(ActionResultType.SUCCESS);
                event.setCanceled(true);
            }
        }
    }

}
