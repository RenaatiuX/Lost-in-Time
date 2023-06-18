package com.rena.lost.common.entities;

import com.rena.lost.common.entities.ia.*;
import com.rena.lost.core.init.EntityInit;
import com.rena.lost.core.init.ItemInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
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
import java.util.List;

public class Mirarce extends AnimalEntity implements IAnimatable, IAnimationTickable {
    private static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(Mirarce.class, DataSerializers.BOOLEAN);
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public int panicTicks = 0;
    public float prevFlyProgress;
    public float flyProgress;
    private boolean isLandNavigator;
    public int timeUntilNextEgg = this.rand.nextInt(6000) + 6000;

    public Mirarce(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        switchNavigator(false);
    }

    public Mirarce(World worldIn) {
        this(EntityInit.MIRARCE.get(), worldIn);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(1, new MirarceFleeGoal(this));
        this.goalSelector.addGoal(2, new BreedGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new TemptGoal(this, 1.25D, Ingredient.fromItems(Items.KELP), false));
        this.goalSelector.addGoal(4, new MirarceAvoidEntityGoal<>(this, LivingEntity.class, 5.0F, 2.2D, 2.2D));
        this.goalSelector.addGoal(5, new FollowParentGoal(this, 1.25D));
        this.goalSelector.addGoal(6, new WaterAvoidingRandomWalkingGoal(this, 1.0D));
        this.goalSelector.addGoal(7, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    public boolean isTargetBlocked(Vector3d target) {
        Vector3d Vector3d = new Vector3d(this.getPosX(), this.getPosYEye(), this.getPosZ());
        return this.world.rayTraceBlocks(new RayTraceContext(Vector3d, target, RayTraceContext.BlockMode.COLLIDER, RayTraceContext.FluidMode.NONE, this)).getType() != RayTraceResult.Type.MISS;
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 7.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.2D);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.KELP;
    }

    private void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveController = new MovementController(this);
            this.navigator = new GroundPathNavigatorWide(this, world);
            this.isLandNavigator = true;
        } else {
            this.moveController = new FlightMoveController(this, 0.7F);
            this.navigator = new DirectPathNavigator(this, world);
            this.isLandNavigator = false;
        }
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(FLYING, false);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putBoolean("Flying", this.isFlying());
        compound.putInt("PanicTimer", this.panicTicks);
        compound.putInt("EggLayTime", this.timeUntilNextEgg);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setFlying(compound.getBoolean("Flying"));
        this.panicTicks = compound.getInt("PanicTimer");
        if (compound.contains("EggLayTime")) {
            this.timeUntilNextEgg = compound.getInt("EggLayTime");
        }

    }

    public boolean isFlying() {
        return this.dataManager.get(FLYING);
    }

    public void setFlying(boolean flying) {
        this.dataManager.set(FLYING, flying);
    }

    @Override
    public void tick() {
        super.tick();
        prevFlyProgress = flyProgress;
        if (isFlying() && flyProgress < 5F) {
            flyProgress++;
        }
        if (!isFlying() && flyProgress > 0F) {
            flyProgress--;
        }
        if (panicTicks > 0) {
            panicTicks--;
        }
        if (panicTicks == 0 && this.getRevengeTarget() != null) {
            this.setRevengeTarget(null);
        }
        if (!world.isRemote) {
            if (isFlying() && this.isLandNavigator) {
                switchNavigator(false);
            }
            if (!isFlying() && !this.isLandNavigator) {
                switchNavigator(true);
            }
            if (this.panicTicks > 0 && !this.isFlying()) {
                if (this.onGround || this.isInWater()) {
                    this.setFlying(false);
                }
            }
            if (isFlying()) {
                this.setNoGravity(true);
            } else {
                this.setNoGravity(false);
            }
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote && this.isAlive() && !this.isChild() && --this.timeUntilNextEgg <= 0) {
            this.playSound(SoundEvents.ENTITY_CHICKEN_EGG, 1.0F, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F);
            this.entityDropItem(ItemInit.MIRARCE_EGG.get());
            this.timeUntilNextEgg = this.rand.nextInt(6000) + 6000;
        }
    }

    @Override
    protected void updateAITasks() {
        if (this.getMoveHelper().isUpdating()) {
            this.setSprinting(this.getMoveHelper().getSpeed() >= 1.5D);
        } else {
            this.setSprinting(false);
        }
        super.updateAITasks();
    }

    @Override
    public boolean attackEntityFrom(DamageSource source, float amount) {
        boolean lastHurt = super.attackEntityFrom(source, amount);
        if (lastHurt) {
            int ticks = 100 + this.rand.nextInt(100);
            this.panicTicks = ticks;
            List<? extends Mirarce> mirarces = this.world.getEntitiesWithinAABB(Mirarce.class, this.getBoundingBox().grow(8.0D, 4.0D, 8.0D));
            for (Mirarce mirarce : mirarces) {
                mirarce.panicTicks = ticks;
            }
        }
        return lastHurt;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return EntityInit.MIRARCE.get().create(world);
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        Vector3d prevPosVector = new Vector3d(this.prevPosX, this.prevPosY, this.prevPosZ);
        double distMoved = prevPosVector.subtract(getPositionVec()).length();
        if (!this.isFlying() && distMoved > 0.09) {
            event.getController().setAnimation(new AnimationBuilder().loop("run"));
            event.getController().setAnimationSpeed(1.5D);
            return PlayState.CONTINUE;
        } else if (!this.isFlying() && distMoved < 0.09 && event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().loop("walk"));
            return PlayState.CONTINUE;
        }
        if (this.isFlying()) {
            event.getController().setAnimation(new AnimationBuilder().loop("fly"));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().loop("idle"));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(10);
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
    }

    @Override
    public AnimationFactory getFactory() {
        return this.factory;
    }

    @Override
    public int tickTimer() {
        return ticksExisted;
    }
}
