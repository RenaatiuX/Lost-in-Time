package com.rena.lost.common.items;

import com.rena.lost.LostInTime;
import com.rena.lost.client.model.ConcavenatorMaskModel;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.IArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class ConcavenatorMaskItem extends ArmorItem {

    private static final Map<EquipmentSlotType, BipedModel<?>> concavenatorMaskModel = new EnumMap<>(EquipmentSlotType.class);

    public ConcavenatorMaskItem(IArmorMaterial armorMaterial, EquipmentSlotType slotType, Properties properties) {
        super(armorMaterial, slotType, properties);
    }

    @Nullable
    @SuppressWarnings("unchecked")
    @Override
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entityLiving, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        return (A) concavenatorMaskModel.get(armorSlot);
    }

    @OnlyIn(Dist.CLIENT)
    public static void initArmorModel() {
        concavenatorMaskModel.put(EquipmentSlotType.HEAD, new ConcavenatorMaskModel(1.0F, EquipmentSlotType.HEAD));
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        return LostInTime.ARMOR_DIR + "concavenator_mask.png";
    }

    @Override
    public void onArmorTick(ItemStack stack, World world, PlayerEntity player) {
        super.onArmorTick(stack, world, player);
        if(this.slot == EquipmentSlotType.HEAD) {
            player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 100, 0));
        }
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TranslationTextComponent("tooltip.lost.concavenator_mask").mergeStyle(TextFormatting.GOLD));
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }
}
