package com.rena.lost.common.events;

import com.rena.lost.LostInTime;
import com.rena.lost.common.entities.Sahonachelys;
import com.rena.lost.core.init.BlockInit;
import com.rena.lost.core.init.ItemInit;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.living.LivingSetAttackTargetEvent;
import net.minecraftforge.event.entity.player.CriticalHitEvent;
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

}
