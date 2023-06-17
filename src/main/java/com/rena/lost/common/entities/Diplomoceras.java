package com.rena.lost.common.entities;

import com.rena.lost.common.blocks.DiplomocerasShellBlock;
import com.rena.lost.common.entities.ia.AquaticMoveController;
import com.rena.lost.core.init.BlockInit;
import com.rena.lost.core.init.EntityInit;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.SwimmerPathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Diplomoceras extends WaterMobEntity implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public Diplomoceras(EntityType<? extends WaterMobEntity> type, World p_i48565_2_) {
        super(type, p_i48565_2_);
        this.moveController = new AquaticMoveController(this, 1.0F);
    }

    public Diplomoceras(World worldIn) {
        super(EntityInit.DIPLOMOCERAS.get(), worldIn);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 15.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 3;
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this) {
            @Override
            public boolean shouldExecute() {
                return !Diplomoceras.this.isOnGround() && super.shouldExecute();
            }
        });
        this.goalSelector.addGoal(4, new RandomSwimmingGoal(this, 0.8D, 10));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6.0F) {
            @Override
            public boolean shouldExecute() {
                return !Diplomoceras.this.isOnGround() && super.shouldExecute();
            }
        });
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new SwimmerPathNavigator(this, worldIn);
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(this.getAIMoveSpeed(), travelVector);
            this.move(MoverType.SELF, this.getMotion());
            this.setMotion(this.getMotion().scale(0.9D));
            if (this.getAttackTarget() == null) {
                this.setMotion(this.getMotion().add(0.0D, -0.005D, 0.0D));
            }
        } else {
            super.travel(travelVector);
        }
    }

    @Override
    public void onDeath(DamageSource cause) {
        super.onDeath(cause);
        BlockPos pos = this.getPosition();
        Direction facing = this.getAdjustedHorizontalFacing();
        BlockState blockState = BlockInit.DIPLOMOCERAS_SHELL.get().getDefaultState().with(DiplomocerasShellBlock.FACING, facing);
        if (this.isOnGround()) {
            this.world.setBlockState(pos, blockState);
        }
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().loop("swim"));
        } else {
            if (this.isOnGround()) {
                event.getController().setAnimation(new AnimationBuilder().loop("beached"));
            }
        }
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
        return this.ticksExisted;
    }
}
