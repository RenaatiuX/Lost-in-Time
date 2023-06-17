package com.rena.lost.common.entities;

import com.rena.lost.common.entities.ia.PelecanimimusFishingGoal;
import com.rena.lost.common.entities.ia.PelecanimimusWadeSwimming;
import com.rena.lost.core.init.EntityInit;
import com.sun.org.apache.xpath.internal.operations.Bool;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import javax.annotation.Nullable;

public class Pelecanimimus extends TameableEntity implements IAnimatable, IAnimationTickable {
    private static final DataParameter<Boolean> FISHING = EntityDataManager.createKey(Pelecanimimus.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public int fishTimer = 15000;
    private boolean startinFishing;

    public Pelecanimimus(EntityType<? extends TameableEntity> type, World worldIn) {
        super(type, worldIn);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.setPathPriority(PathNodeType.WATER_BORDER, 0.0F);
    }

    public Pelecanimimus(World worldIn) {
        super(EntityInit.PELECANIMIMUS.get(), worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new PelecanimimusWadeSwimming(this));
        this.goalSelector.addGoal(1, new PelecanimimusFishingGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromTag(ItemTags.FISHES), false));
        this.goalSelector.addGoal(4, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(5, new RandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(7, new LookRandomlyGoal(this));

    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem().isIn(ItemTags.FISHES);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(FISHING, false);
    }

    public boolean isFishing() {
        return this.dataManager.get(FISHING);
    }

    public void setFishing(boolean fishing) {
        this.dataManager.set(FISHING, fishing);
    }

    public boolean isStartinFishing() {
        return startinFishing;
    }

    public void setStartinFishing(boolean startinFishing) {
        this.startinFishing = startinFishing;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isInWater()) {
            stepHeight = 1.2F;
        } else {
            stepHeight = 0.6F;
        }
        if (!world.isRemote) {
            if (fishTimer > 0) {
                fishTimer--;
            }
        }
    }

    public void spawnFishItem() {
        Item fishItem = ItemTags.FISHES.getRandomElement(this.rand);
        ItemStack fishItemStack = new ItemStack(fishItem);
        if (!this.world.isRemote) {
            this.entityDropItem(fishItemStack);
        }
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("FishingTimer", this.fishTimer);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        this.fishTimer = compound.getInt("FishingTimer");
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return EntityInit.PELECANIMIMUS.get().create(world);
    }

    public void resetFishingTimer() {
        this.fishTimer = Math.max(1200 + rand.nextInt(1200), 200);
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isFishing()) {
            event.getController().setAnimation(new AnimationBuilder().playOnce("fish.start").playOnce("idle.fish").playOnce("fish.catch"));
            return PlayState.CONTINUE;
        }
        if (event.isMoving()) {
            if (this.isSprinting()) {
                event.getController().setAnimation(new AnimationBuilder().loop("run"));
            } else {
                event.getController().setAnimation(new AnimationBuilder().loop("walk"));
            }
        } else {
            event.getController().setAnimation(new AnimationBuilder().loop("idle"));
        }
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return this.ticksExisted;
    }
}
