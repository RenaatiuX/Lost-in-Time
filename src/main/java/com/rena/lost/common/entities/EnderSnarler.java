package com.rena.lost.common.entities;

import com.rena.lost.core.init.EntityInit;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
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

public class EnderSnarler extends MonsterEntity implements IAnimationTickable, IAnimatable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EnderSnarler.class, DataSerializers.BYTE);
    private static final DataParameter<Direction> ATTACHED_FACE = EntityDataManager.createKey(EnderSnarler.class, DataSerializers.DIRECTION);
    private static final Direction[] HORIZONTALS = new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST};
    public float attachChangeProgress = 0F;
    public float prevAttachChangeProgress = 0F;
    private Direction prevAttachDir = Direction.DOWN;
    public EnderSnarler(EntityType<? extends MonsterEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public EnderSnarler(World world) {
        super(EntityInit.ENDER_SNARLER.get(), world);
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 16.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2.0F)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new WaterAvoidingRandomWalkingGoal(this, 0.8D));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.25D, true));
        this.goalSelector.addGoal(2, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(3, new LookRandomlyGoal(this));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    @Override
    public void onKillEntity(ServerWorld world, LivingEntity killedEntity) {
        super.onKillEntity(world, killedEntity);
    }

    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new ClimberPathNavigator(this, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(CLIMBING, (byte) 0);
        this.dataManager.register(ATTACHED_FACE, Direction.DOWN);
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.dataManager.set(ATTACHED_FACE, Direction.byIndex(compound.getByte("AttachFace")));
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putByte("AttachFace", (byte) this.dataManager.get(ATTACHED_FACE).getIndex());
    }

    @Override
    public void tick() {
        super.tick();
        this.prevAttachChangeProgress = this.attachChangeProgress;
        if (attachChangeProgress > 0F) {
            attachChangeProgress -= 0.25F;
        }
        this.stepHeight = 0.5F;
        if (!this.world.isRemote) {
            this.setBesideClimbableBlock(this.collidedHorizontally || this.collidedVertically && !this.isOnGround());
            if (this.isOnGround() || this.isInWaterOrBubbleColumn() || this.isInLava()) {
                this.dataManager.set(ATTACHED_FACE, Direction.DOWN);
            } else  if (this.collidedVertically) {
                this.dataManager.set(ATTACHED_FACE, Direction.UP);
            }else {
                Direction closestDirection = Direction.DOWN;
                double closestDistance = 100;
                for (Direction dir : HORIZONTALS) {
                    BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY()), MathHelper.floor(this.getPosZ()));
                    BlockPos offsetPos = pos.offset(dir);
                    Vector3d offset = Vector3d.copyCentered(offsetPos);
                    if (closestDistance > this.getPositionVec().distanceTo(offset) && world.isDirectionSolid(offsetPos, this, dir.getOpposite())) {
                        closestDistance = this.getPositionVec().distanceTo(offset);
                        closestDirection = dir;
                    }
                }
                this.dataManager.set(ATTACHED_FACE, closestDirection);
            }
        }
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    public boolean isBesideClimbableBlock() {
        return (this.dataManager.get(CLIMBING) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean climbing) {
        byte b0 = this.dataManager.get(CLIMBING);
        if (climbing) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }

        this.dataManager.set(CLIMBING, b0);
    }

    public Direction getAttachmentFacing() {
        return this.dataManager.get(ATTACHED_FACE);
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::predicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (event.isMoving() || this.isBesideClimbableBlock()) {
            event.getController().setAnimation(new AnimationBuilder().loop("walk"));
        } else {
            event.getController().setAnimation(new AnimationBuilder().loop("idle"));
        }
        return PlayState.CONTINUE;
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
