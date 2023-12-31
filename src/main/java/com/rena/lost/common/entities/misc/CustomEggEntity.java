package com.rena.lost.common.entities.misc;

import com.rena.lost.core.init.EntityInit;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.projectile.ProjectileItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.EntityRayTraceResult;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

import java.util.function.Supplier;

public class CustomEggEntity extends ProjectileItemEntity {

    protected Supplier<EntityType<? extends AnimalEntity>> entityTypeSupplier;

    public CustomEggEntity(EntityType<? extends CustomEggEntity> type, World worldIn) {
        super(type, worldIn);
    }

    public CustomEggEntity(World worldIn, double x, double y, double z) {
        super(EntityInit.CUSTOM_EGG.get(), x, y, z, worldIn);
    }

    public CustomEggEntity(World worldIn, LivingEntity throwerIn, Item item, Supplier<EntityType<? extends AnimalEntity>> entityTypeSupplier) {
        super(EntityInit.CUSTOM_EGG.get(), throwerIn, worldIn);
        setItem(item.getDefaultInstance());
        this.entityTypeSupplier = entityTypeSupplier;
    }

    public CustomEggEntity(World worldIn, double x, double y, double z, Item item, Supplier<EntityType<? extends AnimalEntity>> entityTypeSupplier) {
        super(EntityInit.CUSTOM_EGG.get(), x, y, z, worldIn);
        setItem(item.getDefaultInstance());
        this.entityTypeSupplier = entityTypeSupplier;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 3) {
            for(int i = 0; i < 8; ++i) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, this.getItem()), this.getPosX(), this.getPosY(), this.getPosZ(), ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D, ((double)this.rand.nextFloat() - 0.5D) * 0.08D);
            }
        }
    }

    @Override
    protected void onEntityHit(EntityRayTraceResult result) {
        super.onEntityHit(result);
        result.getEntity().attackEntityFrom(DamageSource.causeThrownDamage(this, this.getShooter()), 0.0F);
    }

    @Override
    protected void onImpact(RayTraceResult result) {
        super.onImpact(result);
        if (!this.world.isRemote) {
            if (this.rand.nextInt(8) == 0) {
                int i = 1;
                if (this.rand.nextInt(32) == 0) {
                    i = 4;
                }

                for(int j = 0; j < i; ++j) {
                    AgeableEntity ageable = entityTypeSupplier.get().create(world);
                    ageable.setGrowingAge(-24000);
                    ageable.setLocationAndAngles(this.getPosX(), this.getPosY(), this.getPosZ(), this.rotationYaw, 0.0F);
                    this.world.addEntity(ageable);
                }
            }

            this.world.setEntityState(this, (byte)3);
            this.remove();
        }
    }

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }

    @Override
    protected Item getDefaultItem() {
        return Items.GRASS;
    }
}
