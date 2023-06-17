package com.rena.lost.common.entities;

import com.rena.lost.common.entities.ia.DakosaurusAttackGoal;
import com.rena.lost.common.entities.ia.DakosaurusJumpGoal;
import com.rena.lost.common.entities.ia.DakosaurusPathNavigator;
import com.rena.lost.core.init.EntityInit;
import com.rena.lost.core.init.ItemInit;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.DolphinLookController;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.passive.WaterMobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.IAnimationTickable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.List;

public class Dakosaurus extends WaterMobEntity implements IAnimatable, IAnimationTickable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public int jumpCooldown;
    public Dakosaurus(EntityType<? extends WaterMobEntity> type, World p_i48565_2_) {
        super(type, p_i48565_2_);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.moveController = new MoveHelperController(this);
        this.lookController = new DolphinLookController(this, 10);
    }

    public Dakosaurus(World world) {
        this(EntityInit.DAKOSAURUS.get(), world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new BreatheAirGoal(this));
        this.goalSelector.addGoal(1, new DakosaurusAttackGoal(this, 1.2D, false));
        this.goalSelector.addGoal(3, new RandomSwimmingGoal(this, 1.0D, 10));
        this.goalSelector.addGoal(4, new LookRandomlyGoal(this));
        this.goalSelector.addGoal(5, new LookAtGoal(this, PlayerEntity.class, 6.0F));
        this.goalSelector.addGoal(5, new DakosaurusJumpGoal(this, 10));
        this.targetSelector.addGoal(1, (new HurtByTargetGoal(this)));
        this.targetSelector.addGoal(2, (new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true)));
        this.targetSelector.addGoal(3, (new NearestAttackableTargetGoal<>(this, MobEntity.class, false)));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 35.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, (double) 1.2F)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 3.0D);
    }

    @Override
    public void tick() {
        super.tick();
        if (jumpCooldown > 0) {
            jumpCooldown--;
            this.rotationPitch = (float) -((float) this.getMotion().y * (double) (180F / (float) Math.PI));
        }

    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new DakosaurusPathNavigator(this, worldIn);
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
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean attacked = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), (float) ((int) this.getAttributeValue(Attributes.ATTACK_DAMAGE)));
        LivingEntity prey = this.getAttackTarget();
        if (prey != null) {
            double dist = this.getDistance(prey);
            if (attacked) {
                this.applyEnchantments(this, entityIn);
                if (prey instanceof WaterMobEntity) {
                    this.faceEntity(prey, 30, 30);
                    prey.attackEntityFrom(DamageSource.causeMobDamage(this), (float) this.getAttributeValue(Attributes.ATTACK_DAMAGE));
                }
                if (dist < 2D && this.rand.nextFloat() < 0.3F) {
                    this.entityDropItem(new ItemStack(ItemInit.DAKOSAURUS_TOOTH.get()));
                }
            }
        }
        return attacked;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.DROWN || source == DamageSource.IN_WALL || source == DamageSource.FALLING_BLOCK || super.isInvulnerableTo(source);
    }

    public boolean shouldUseJumpAttack(LivingEntity attackTarget) {
        if (attackTarget.isInWater()) {
            BlockPos up = attackTarget.getPosition().up();
            return world.getFluidState(up.up()).isEmpty() && world.getFluidState(up.up(2)).isEmpty() && this.jumpCooldown == 0;
        } else {
            return this.jumpCooldown == 0;
        }
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(10);
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
        data.addAnimationController(new AnimationController<>(this, "attack_controller", 0, this::attackPredicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        if (this.isInWater()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("swim", ILoopType.EDefaultLoopTypes.LOOP));
        } else if (this.isOnGround()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("beached", ILoopType.EDefaultLoopTypes.LOOP));
        }
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.isSwingInProgress && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack"));
            this.isSwingInProgress = false;
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

    static class MoveHelperController extends MovementController {
        private final Dakosaurus dakosaurus;
        public MoveHelperController(Dakosaurus dakosaurus) {
            super(dakosaurus);
            this.dakosaurus = dakosaurus;
        }

        @Override
        public void tick() {
            if (this.dakosaurus.isInWater()) {
                this.dakosaurus.setMotion(this.dakosaurus.getMotion().add(0.0D, 0.005D, 0.0D));
            }

            if (this.action == MovementController.Action.MOVE_TO && !this.dakosaurus.getNavigator().noPath()) {
                double d0 = this.posX - this.dakosaurus.getPosX();
                double d1 = this.posY - this.dakosaurus.getPosY();
                double d2 = this.posZ - this.dakosaurus.getPosZ();
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                if (d3 < (double)2.5000003E-7F) {
                    this.mob.setMoveForward(0.0F);
                } else {
                    float f = (float)(MathHelper.atan2(d2, d0) * (double)(180F / (float)Math.PI)) - 90.0F;
                    this.dakosaurus.rotationYaw = this.limitAngle(this.dakosaurus.rotationYaw, f, 10.0F);
                    this.dakosaurus.renderYawOffset = this.dakosaurus.rotationYaw;
                    this.dakosaurus.rotationYawHead = this.dakosaurus.rotationYaw;
                    float f1 = (float)(this.speed * this.dakosaurus.getAttributeValue(Attributes.MOVEMENT_SPEED));
                    if (this.dakosaurus.isInWater()) {
                        this.dakosaurus.setAIMoveSpeed(f1 * 0.02F);
                        float f2 = -((float)(MathHelper.atan2(d1, (double)MathHelper.sqrt(d0 * d0 + d2 * d2)) * (double)(180F / (float)Math.PI)));
                        f2 = MathHelper.clamp(MathHelper.wrapDegrees(f2), -85.0F, 85.0F);
                        this.dakosaurus.rotationPitch = this.limitAngle(this.dakosaurus.rotationPitch, f2, 5.0F);
                        float f3 = MathHelper.cos(this.dakosaurus.rotationPitch * ((float)Math.PI / 180F));
                        float f4 = MathHelper.sin(this.dakosaurus.rotationPitch * ((float)Math.PI / 180F));
                        this.dakosaurus.moveForward = f3 * f1;
                        this.dakosaurus.moveVertical = -f4 * f1;
                    } else {
                        this.dakosaurus.setAIMoveSpeed(f1 * 0.1F);
                    }

                }
            } else {
                this.dakosaurus.setAIMoveSpeed(0.0F);
                this.dakosaurus.setMoveStrafing(0.0F);
                this.dakosaurus.setMoveVertical(0.0F);
                this.dakosaurus.setMoveForward(0.0F);
            }
        }
    }
}
