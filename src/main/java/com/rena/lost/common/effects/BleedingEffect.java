package com.rena.lost.common.effects;

import com.rena.lost.core.init.ParticleInit;
import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class BleedingEffect extends Effect {
    public BleedingEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {

        entityLivingBaseIn.attackEntityFrom(DamageSource.MAGIC, 1.0F);

        if (entityLivingBaseIn.world.isRemote) {
            double offsetX = entityLivingBaseIn.world.rand.nextGaussian() * 0.2;
            double offsetY = entityLivingBaseIn.getHeight() - 0.1;
            double offsetZ = entityLivingBaseIn.world.rand.nextGaussian() * 0.2;
            entityLivingBaseIn.world.addParticle(ParticleInit.BLOOD_PARTICLE.get(),
                    entityLivingBaseIn.getPosX() + offsetX, entityLivingBaseIn.getPosY() + offsetY,
                    entityLivingBaseIn.getPosZ() + offsetZ,
                    0.0, 0.0, 0.0);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
