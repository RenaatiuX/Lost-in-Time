package com.rena.lost.common.entities;

import com.rena.lost.common.blocks.SahonachelysEggBlock;
import com.rena.lost.common.entities.util.ISemiAquatic;
import com.rena.lost.core.init.BlockInit;
import com.rena.lost.core.init.EntityInit;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.controller.MovementController;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.item.ExperienceOrbEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.*;
import net.minecraft.stats.Stats;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Direction;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.*;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.IForgeShearable;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class Sahonachelys extends AnimalEntity implements ISemiAquatic, IAnimatable, IAnimationTickable, IForgeShearable {
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    private static final DataParameter<Integer> MOSS = EntityDataManager.createKey(Sahonachelys.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> HAS_EGG = EntityDataManager.createKey(Sahonachelys.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_DIGGING = EntityDataManager.createKey(Sahonachelys.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> TRAVELLING = EntityDataManager.createKey(Sahonachelys.class, DataSerializers.BOOLEAN);
    private static final DataParameter<BlockPos> TRAVEL_POS = EntityDataManager.createKey(Sahonachelys.class, DataSerializers.BLOCK_POS);
    private static final DataParameter<Boolean> PRESS_BUTTON = EntityDataManager.createKey(Sahonachelys.class, DataSerializers.BOOLEAN);

    private int isDigging;
    private int mossTime = 0;

    public Sahonachelys(EntityType<? extends AnimalEntity> type, World worldIn) {
        super(type, worldIn);
        this.setPathPriority(PathNodeType.WATER, 0.0F);
        this.moveController = new Sahonachelys.MoveHelperController(this);
        stepHeight = 1F;
    }

    public Sahonachelys(World world) {
        this(EntityInit.SAHONACHELYS.get(), world);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new SahonachelysAttackGoal());
        this.goalSelector.addGoal(2, new MateGoal(this, 1.0D));
        this.goalSelector.addGoal(2, new LayEggGoal(this, 1.0D));
        this.goalSelector.addGoal(3, new PlayerTemptGoal(this, 1.1D, Ingredient.fromItems(Items.FERN, Items.SEAGRASS)));
        this.goalSelector.addGoal(4, new GoToWaterGoal(this, 1.0D));
        this.goalSelector.addGoal(5, new PressButtonGoal(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.addGoal(7, new TravelGoal(this, 1.0D));
        this.goalSelector.addGoal(8, new WanderGoal(this, 1.0D, 100));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute createAttributes() {
        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 10.0D)
                .createMutableAttribute(Attributes.FOLLOW_RANGE, 16.0D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25F);
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.WATER;
    }

    @Override
    public float getRenderScale() {
        return this.isChild() ? 0.5F : 1.0F;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return stack.getItem() == Items.FERN || stack.getItem() == Items.SEAGRASS;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(MOSS, 0);
        this.dataManager.register(HAS_EGG, Boolean.FALSE);
        this.dataManager.register(IS_DIGGING, Boolean.FALSE);
        this.dataManager.register(PRESS_BUTTON, Boolean.FALSE);
        this.dataManager.register(TRAVEL_POS, BlockPos.ZERO);
        this.dataManager.register(TRAVELLING, false);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            mossTime++;
            if (this.isInWater()) {
                if (mossTime > 12000) {
                    mossTime = 0;
                    this.setMoss(Math.min(10, this.getMoss() + 1));
                }
            }
        }
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        this.setMoss(rand.nextInt(10));
        this.setTravelPos(BlockPos.ZERO);
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        return new Sahonachelys.Navigator(this, worldIn);
    }

    public int getMoss() {
        return this.dataManager.get(MOSS);
    }

    public void setMoss(int moss) {
        this.dataManager.set(MOSS, moss);
    }

    public boolean hasEgg() {
        return this.dataManager.get(HAS_EGG);
    }

    private void setHasEgg(boolean hasEgg) {
        this.dataManager.set(HAS_EGG, hasEgg);
    }

    public boolean isDigging() {
        return this.dataManager.get(IS_DIGGING);
    }

    private void setDigging(boolean isDigging) {
        this.isDigging = isDigging ? 1 : 0;
        this.dataManager.set(IS_DIGGING, isDigging);
    }

    public boolean isPressingButton() {
        return this.dataManager.get(PRESS_BUTTON);
    }

    public void setPressingButton(boolean isPressingButton) {
        this.dataManager.set(PRESS_BUTTON, isPressingButton);
    }

    private void setTravelPos(BlockPos position) {
        this.dataManager.set(TRAVEL_POS, position);
    }

    private BlockPos getTravelPos() {
        return this.dataManager.get(TRAVEL_POS);
    }

    private boolean isTravelling() {
        return this.dataManager.get(TRAVELLING);
    }

    private void setTravelling(boolean isTravelling) {
        this.dataManager.set(TRAVELLING, isTravelling);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("MossLevel", this.getMoss());
        compound.putInt("MossTime", this.mossTime);
        compound.putBoolean("HasEgg", this.hasEgg());
        compound.putBoolean("isPressingButton", this.isPressingButton());
        compound.putInt("TravelPosX", this.getTravelPos().getX());
        compound.putInt("TravelPosY", this.getTravelPos().getY());
        compound.putInt("TravelPosZ", this.getTravelPos().getZ());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setMoss(compound.getInt("MossLevel"));
        this.mossTime = compound.getInt("MossTime");
        this.setHasEgg(compound.getBoolean("HasEgg"));
        this.setPressingButton(compound.getBoolean("isPressingButton"));
        int l = compound.getInt("TravelPosX");
        int i1 = compound.getInt("TravelPosY");
        int j1 = compound.getInt("TravelPosZ");
        this.setTravelPos(new BlockPos(l, i1, j1));
    }

    @Override
    public boolean shouldEnterWater() {
        return true;
    }

    @Override
    public boolean shouldLeaveWater() {
        return true;
    }

    @Override
    public boolean shouldStopMoving() {
        return false;
    }

    @Override
    public int getWaterSearchRange() {
        return 10;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    @Override
    public boolean isPushedByWater() {
        return false;
    }

    @Override
    public boolean canFallInLove() {
        return super.canFallInLove() && !this.hasEgg();
    }

    @Override
    public void travel(Vector3d travelVector) {
        if (this.isServerWorld() && this.isInWater()) {
            this.moveRelative(0.1F, travelVector);
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
    public boolean canBeLeashedTo(PlayerEntity player) {
        return false;
    }

    @Nullable
    @Override
    public AgeableEntity createChild(ServerWorld world, AgeableEntity mate) {
        return EntityInit.SAHONACHELYS.get().create(world);
    }

    @Override
    public float getBlockPathWeight(BlockPos pos, IWorldReader worldIn) {
        if (worldIn.getFluidState(pos).isTagged(FluidTags.WATER)) {
            return 10.0F;
        } else {
            return SahonachelysEggBlock.hasProperHabitat(worldIn, pos) ? 10.0F : worldIn.getBrightness(pos) - 0.5F;
        }
    }

    @Override
    public boolean isInvulnerableTo(DamageSource source) {
        return source == DamageSource.DROWN || source == DamageSource.IN_WALL || source == DamageSource.FALLING_BLOCK || super.isInvulnerableTo(source);
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.isAlive() && this.isDigging() && this.isDigging >= 1 && this.isDigging % 5 == 0) {
            BlockPos blockpos = this.getPosition();
            if (SahonachelysEggBlock.hasProperHabitat(this.world, blockpos)) {
                this.world.playEvent(2001, blockpos, Block.getStateId(Blocks.DIRT.getDefaultState()));
                this.world.playEvent(2001, blockpos, Block.getStateId(BlockInit.MUD.get().getDefaultState()));
            }
        }
    }

    @Override
    public boolean isShearable(@Nonnull ItemStack item, World world, BlockPos pos) {
        return this.isAlive() && this.getMoss() > 0;
    }

    @Nonnull
    @Override
    public List<ItemStack> onSheared(@Nullable PlayerEntity player, @Nonnull ItemStack item, World world, BlockPos pos, int fortune) {
        world.playMovingSound(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, player == null ? SoundCategory.BLOCKS : SoundCategory.PLAYERS, 1.0F, 1.0F);
        if (!world.isRemote()) {
            this.setMoss(0);
            return Collections.singletonList(new ItemStack(Items.SEAGRASS));
        }
        return java.util.Collections.emptyList();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.setResetSpeedInTicks(10);
        data.addAnimationController(new AnimationController<>(this, "controller", 10, this::predicate));
        data.addAnimationController(new AnimationController<>(this, "attack_controller", 0, this::attackPredicate));
    }

    private <E extends IAnimatable> PlayState predicate(AnimationEvent<E> event) {
        Vector3d prevPosVector = new Vector3d(this.prevPosX, this.prevPosY, this.prevPosZ);
        if (this.isInWaterOrBubbleColumn()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("swim", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        } else if (prevPosVector.subtract(getPositionVec()).length() >= 0.001) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation("walk", ILoopType.EDefaultLoopTypes.LOOP));
            return PlayState.CONTINUE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation("idle", ILoopType.EDefaultLoopTypes.LOOP));
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState attackPredicate(AnimationEvent<E> event) {
        if (this.isSwingInProgress && event.getController().getAnimationState().equals(AnimationState.Stopped)) {
            event.getController().markNeedsReload();
            event.getController().setAnimation(new AnimationBuilder().addAnimation("attack"));
            this.isSwingInProgress = false;
        } else if (this.isPressingButton()) {
            //TODO need to fix this don't know why it wont work
            event.getController().setAnimation(new AnimationBuilder().addAnimation("buttonpush", ILoopType.EDefaultLoopTypes.PLAY_ONCE));
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

    static class LayEggGoal extends MoveToBlockGoal {
        private final Sahonachelys sahonachelys;

        LayEggGoal(Sahonachelys sahonachelys, double speedIn) {
            super(sahonachelys, speedIn, 16);
            this.sahonachelys = sahonachelys;
        }

        @Override
        public boolean shouldExecute() {
            return this.sahonachelys.hasEgg() && super.shouldExecute();
        }

        @Override
        public boolean shouldContinueExecuting() {
            return super.shouldContinueExecuting() && this.sahonachelys.hasEgg();
        }

        @Override
        public void tick() {
            super.tick();
            BlockPos blockpos = this.sahonachelys.getPosition();
            if (!this.sahonachelys.isInWater() && this.getIsAboveDestination()) {
                if (this.sahonachelys.isDigging < 1) {
                    this.sahonachelys.setDigging(true);
                } else if (this.sahonachelys.isDigging > 200) {
                    World world = this.sahonachelys.world;
                    world.playSound((PlayerEntity) null, blockpos, SoundEvents.ENTITY_TURTLE_LAY_EGG, SoundCategory.BLOCKS, 0.3F, 0.9F + world.rand.nextFloat() * 0.2F);
                    world.setBlockState(this.destinationBlock.up(), Blocks.TURTLE_EGG.getDefaultState().with(TurtleEggBlock.EGGS, this.sahonachelys.rand.nextInt(4) + 1), 3);
                    this.sahonachelys.setHasEgg(false);
                    this.sahonachelys.setDigging(false);
                    this.sahonachelys.setInLove(600);
                }

                if (this.sahonachelys.isDigging()) {
                    this.sahonachelys.isDigging++;
                }
            }
        }

        @Override
        protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
            return worldIn.isAirBlock(pos.up()) && SahonachelysEggBlock.isProperHabitat(worldIn, pos);
        }
    }

    static class MateGoal extends BreedGoal {
        private final Sahonachelys sahonachelys;

        MateGoal(Sahonachelys sahonachelys, double speedIn) {
            super(sahonachelys, speedIn);
            this.sahonachelys = sahonachelys;
        }

        @Override
        public boolean shouldExecute() {
            return super.shouldExecute() && !this.sahonachelys.hasEgg();
        }

        @Override
        protected void spawnBaby() {
            ServerPlayerEntity serverplayerentity = this.animal.getLoveCause();
            if (serverplayerentity == null && this.targetMate.getLoveCause() != null) {
                serverplayerentity = this.targetMate.getLoveCause();
            }

            if (serverplayerentity != null) {
                serverplayerentity.addStat(Stats.ANIMALS_BRED);
                CriteriaTriggers.BRED_ANIMALS.trigger(serverplayerentity, this.animal, this.targetMate, (AgeableEntity) null);
            }

            this.sahonachelys.setHasEgg(true);
            this.animal.resetInLove();
            this.targetMate.resetInLove();
            Random random = this.animal.getRNG();
            if (this.world.getGameRules().getBoolean(GameRules.DO_MOB_LOOT)) {
                this.world.addEntity(new ExperienceOrbEntity(this.world, this.animal.getPosX(), this.animal.getPosY(), this.animal.getPosZ(), random.nextInt(7) + 1));
            }
        }
    }

    static class PressButtonGoal extends MoveToBlockGoal {

        private final Sahonachelys sahonachelys;
        private final Random random;
        private final List<BlockPos> buttonPositions;
        private int timeUntilNextPress;

        PressButtonGoal(Sahonachelys sahonachelys, double speedIn) {
            super(sahonachelys, speedIn, 10);
            this.sahonachelys = sahonachelys;
            this.random = new Random();
            this.buttonPositions = new ArrayList<>();
            this.timeUntilNextPress = 0;
        }

        @Override
        public boolean shouldExecute() {
            if (this.timeUntilNextPress > 0) {
                this.timeUntilNextPress--;
                return false;
            }

            // Comprueba si hay botones dentro del radio de 10 bloques
            findButtons();
            if (!buttonPositions.isEmpty()) {
                // Selecciona un botón al azar de la lista
                int index = random.nextInt(buttonPositions.size());
                this.destinationBlock = buttonPositions.get(index);
                sahonachelys.setPressingButton(true);
                return true;
            }

            this.timeUntilNextPress = 200; // Espera 10 segundos antes de buscar otro botón
            return false;
        }

        @Override
        public void tick() {
            if (this.destinationBlock != null) {
                double distanceSq = this.sahonachelys.getDistanceSq(this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + 0.5, this.destinationBlock.getZ() + 0.5);
                if (distanceSq <= 2.0) {
                    Direction entityFacing = this.sahonachelys.getHorizontalFacing();
                    Direction buttonFacing = getButtonFacing(this.destinationBlock);
                    if (entityFacing == buttonFacing) {
                        BlockState buttonState = this.sahonachelys.world.getBlockState(this.destinationBlock);
                        AbstractButtonBlock button = (AbstractButtonBlock) buttonState.getBlock();

                        // Presiona el botón cambiando el estado del bloque
                        button.powerBlock(buttonState, this.sahonachelys.world, this.destinationBlock);

                        this.destinationBlock = null;
                        this.timeUntilNextPress = 200; // Espera 10 segundos antes de buscar otro botón
                        sahonachelys.setPressingButton(false);
                    }
                } else {
                    this.sahonachelys.getNavigator().tryMoveToXYZ(this.destinationBlock.getX() + 0.5, this.destinationBlock.getY() + 0.5, this.destinationBlock.getZ() + 0.5, this.movementSpeed);
                }
            }
        }

        @Override
        protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
            sahonachelys.setPressingButton(true); // Establece que la entidad está presionando un botón
            return !sahonachelys.isPressingButton();
        }

        private void findButtons() {
            this.buttonPositions.clear();
            BlockPos entityPos = new BlockPos(this.sahonachelys.getPosition());
            int searchRange = 10;

            for (int x = -searchRange; x <= searchRange; x++) {
                for (int y = -searchRange; y <= searchRange; y++) {
                    for (int z = -searchRange; z <= searchRange; z++) {
                        BlockPos buttonPos = entityPos.add(x, y, z);
                        BlockState blockState = this.sahonachelys.world.getBlockState(buttonPos);
                        if (blockState.getBlock() instanceof AbstractButtonBlock) {
                            this.buttonPositions.add(buttonPos);
                        }
                    }
                }
            }
        }
        private Direction getButtonFacing(BlockPos buttonPos) {
            BlockState blockState = this.sahonachelys.world.getBlockState(buttonPos);
            if (blockState.getBlock() instanceof AbstractButtonBlock) {
                return blockState.get(AbstractButtonBlock.HORIZONTAL_FACING);
            }
            return Direction.NORTH;
        }
    }

    static class PlayerTemptGoal extends Goal {
        private static final EntityPredicate field_220834_a = (new EntityPredicate()).setDistance(10.0D).allowFriendlyFire().allowInvulnerable();
        private final Sahonachelys sahonachelys;
        private final double speed;
        private PlayerEntity tempter;
        private int cooldown;
        private final Ingredient temptItems;

        PlayerTemptGoal(Sahonachelys sahonachelys, double speedIn, Ingredient temptItem) {
            this.sahonachelys = sahonachelys;
            this.speed = speedIn;
            this.temptItems = temptItem;
            this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        }

        public boolean shouldExecute() {
            if (this.cooldown > 0) {
                --this.cooldown;
                return false;
            } else {
                this.tempter = this.sahonachelys.world.getClosestPlayer(field_220834_a, this.sahonachelys);
                if (this.tempter == null) {
                    return false;
                } else {
                    return this.isTemptedBy(this.tempter.getHeldItemMainhand()) || this.isTemptedBy(this.tempter.getHeldItemOffhand());
                }
            }
        }

        private boolean isTemptedBy(ItemStack p_203131_1_) {
            return this.temptItems.test(p_203131_1_);
        }

        public boolean shouldContinueExecuting() {
            return this.shouldExecute();
        }

        public void resetTask() {
            this.tempter = null;
            this.sahonachelys.getNavigator().clearPath();
            this.cooldown = 100;
        }

        public void tick() {
            this.sahonachelys.getLookController().setLookPositionWithEntity(this.tempter, (float) (this.sahonachelys.getHorizontalFaceSpeed() + 20), (float) this.sahonachelys.getVerticalFaceSpeed());
            if (this.sahonachelys.getDistanceSq(this.tempter) < 6.25D) {
                this.sahonachelys.getNavigator().clearPath();
            } else {
                this.sahonachelys.getNavigator().tryMoveToEntityLiving(this.tempter, this.speed);
            }
        }
    }

    static class GoToWaterGoal extends MoveToBlockGoal {
        private final Sahonachelys sahonachelys;

        private GoToWaterGoal(Sahonachelys sahonachelys, double speedIn) {
            super(sahonachelys, sahonachelys.isChild() ? 2.0D : speedIn, 24);
            this.sahonachelys = sahonachelys;
            this.field_203112_e = -1;
        }

        public boolean shouldContinueExecuting() {
            return !this.sahonachelys.isInWater() && this.timeoutCounter <= 1200 && this.shouldMoveTo(this.sahonachelys.world, this.destinationBlock);
        }

        public boolean shouldExecute() {
            if (this.sahonachelys.isChild() && !this.sahonachelys.isInWater()) {
                return super.shouldExecute();
            } else {
                return !this.sahonachelys.isInWater() && !this.sahonachelys.hasEgg() && super.shouldExecute();
            }
        }

        public boolean shouldMove() {
            return this.timeoutCounter % 160 == 0;
        }

        protected boolean shouldMoveTo(IWorldReader worldIn, BlockPos pos) {
            return worldIn.getBlockState(pos).matchesBlock(Blocks.WATER);
        }
    }

    static class TravelGoal extends Goal {
        private final Sahonachelys sahonachelys;
        private final double speed;
        private boolean field_203139_c;

        TravelGoal(Sahonachelys sahonachelys, double speedIn) {
            this.sahonachelys = sahonachelys;
            this.speed = speedIn;
        }

        public boolean shouldExecute() {
            return !this.sahonachelys.hasEgg() && this.sahonachelys.isInWater();
        }

        public void startExecuting() {
            int i = 512;
            int j = 4;
            Random random = this.sahonachelys.rand;
            int k = random.nextInt(1025) - 512;
            int l = random.nextInt(9) - 4;
            int i1 = random.nextInt(1025) - 512;
            if ((double) l + this.sahonachelys.getPosY() > (double) (this.sahonachelys.world.getSeaLevel() - 1)) {
                l = 0;
            }

            BlockPos blockpos = new BlockPos((double) k + this.sahonachelys.getPosX(), (double) l + this.sahonachelys.getPosY(), (double) i1 + this.sahonachelys.getPosZ());
            this.sahonachelys.setTravelPos(blockpos);
            this.sahonachelys.setTravelling(true);
            this.field_203139_c = false;
        }

        public void tick() {
            if (this.sahonachelys.getNavigator().noPath()) {
                Vector3d vector3d = Vector3d.copyCenteredHorizontally(this.sahonachelys.getTravelPos());
                Vector3d vector3d1 = RandomPositionGenerator.findRandomTargetTowardsScaled(this.sahonachelys, 16, 3, vector3d, (double) ((float) Math.PI / 10F));
                if (vector3d1 == null) {
                    vector3d1 = RandomPositionGenerator.findRandomTargetBlockTowards(this.sahonachelys, 8, 7, vector3d);
                }

                if (vector3d1 != null) {
                    int i = MathHelper.floor(vector3d1.x);
                    int j = MathHelper.floor(vector3d1.z);
                    int k = 34;
                    if (!this.sahonachelys.world.isAreaLoaded(i - 34, 0, j - 34, i + 34, 0, j + 34)) {
                        vector3d1 = null;
                    }
                }

                if (vector3d1 == null) {
                    this.field_203139_c = true;
                    return;
                }

                this.sahonachelys.getNavigator().tryMoveToXYZ(vector3d1.x, vector3d1.y, vector3d1.z, this.speed);
            }

        }

        public boolean shouldContinueExecuting() {
            return !this.sahonachelys.getNavigator().noPath() && !this.field_203139_c && !this.sahonachelys.isInLove() && !this.sahonachelys.hasEgg();
        }

        public void resetTask() {
            this.sahonachelys.setTravelling(false);
            super.resetTask();
        }
    }

    class SahonachelysAttackGoal extends MeleeAttackGoal {
        public SahonachelysAttackGoal() {
            super(Sahonachelys.this, 1.0D, false);
        }

        @Override
        public boolean shouldExecute() {
            LivingEntity target = Sahonachelys.this.getAttackTarget();
            if (target instanceof PlayerEntity) {
                double distanceSq = Sahonachelys.this.getDistanceSq(target.getPosX(), target.getPosY(), target.getPosZ());
                return distanceSq <= 4.0 && super.shouldExecute();
            }
            return false;
        }
    }

    static class WanderGoal extends RandomWalkingGoal {
        private final Sahonachelys sahonachelys;

        private WanderGoal(Sahonachelys sahonachelys, double speedIn, int chance) {
            super(sahonachelys, speedIn, chance);
            this.sahonachelys = sahonachelys;
        }

        public boolean shouldExecute() {
            return !this.creature.isInWater() && !this.sahonachelys.hasEgg() && super.shouldExecute();
        }
    }

    static class Navigator extends SwimmerPathNavigator {
        Navigator(Sahonachelys sahonachelys, World worldIn) {
            super(sahonachelys, worldIn);
        }

        protected boolean canNavigate() {
            return true;
        }

        protected PathFinder getPathFinder(int p_179679_1_) {
            this.nodeProcessor = new WalkAndSwimNodeProcessor();
            return new PathFinder(this.nodeProcessor, p_179679_1_);
        }

        public boolean canEntityStandOnPos(BlockPos pos) {
            if (this.entity instanceof Sahonachelys) {
                Sahonachelys sahonachelys = (Sahonachelys) this.entity;
                if (sahonachelys.isTravelling()) {
                    return this.world.getBlockState(pos).matchesBlock(Blocks.WATER);
                }
            }

            return !this.world.getBlockState(pos.down()).isAir();
        }
    }

    static class MoveHelperController extends MovementController {
        private final Sahonachelys sahonachelys;

        MoveHelperController(Sahonachelys sahonachelys) {
            super(sahonachelys);
            this.sahonachelys = sahonachelys;
        }

        private void updateSpeed() {
            if (this.sahonachelys.isInWater()) {
                this.sahonachelys.setMotion(this.sahonachelys.getMotion().add(0.0D, 0.005D, 0.0D));
                if (this.sahonachelys.isChild()) {
                    this.sahonachelys.setAIMoveSpeed(Math.max(this.sahonachelys.getAIMoveSpeed() / 3.0F, 0.06F));
                } else {
                    this.sahonachelys.setAIMoveSpeed(Math.max(this.sahonachelys.getAIMoveSpeed() / 2.0F, 0.08F));
                }
            } else if (this.sahonachelys.onGround) {
                this.sahonachelys.setAIMoveSpeed(Math.max(this.sahonachelys.getAIMoveSpeed() / 2.0F, 0.06F));
            }
        }

        public void tick() {
            this.updateSpeed();
            if (this.action == MovementController.Action.MOVE_TO && !this.sahonachelys.getNavigator().noPath()) {
                double d0 = this.posX - this.sahonachelys.getPosX();
                double d1 = this.posY - this.sahonachelys.getPosY();
                double d2 = this.posZ - this.sahonachelys.getPosZ();
                double d3 = (double) MathHelper.sqrt(d0 * d0 + d1 * d1 + d2 * d2);
                d1 = d1 / d3;
                float f = (float) (MathHelper.atan2(d2, d0) * (double) (180F / (float) Math.PI)) - 90.0F;
                this.sahonachelys.rotationYaw = this.limitAngle(this.sahonachelys.rotationYaw, f, 90.0F);
                this.sahonachelys.renderYawOffset = this.sahonachelys.rotationYaw;
                float f1 = (float) (this.speed * this.sahonachelys.getAttributeValue(Attributes.MOVEMENT_SPEED));
                this.sahonachelys.setAIMoveSpeed(MathHelper.lerp(0.125F, this.sahonachelys.getAIMoveSpeed(), f1));
                this.sahonachelys.setMotion(this.sahonachelys.getMotion().add(0.0D, (double) this.sahonachelys.getAIMoveSpeed() * d1 * 0.1D, 0.0D));
            } else {
                this.sahonachelys.setAIMoveSpeed(0.0F);
            }
        }
    }
}
