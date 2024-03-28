package com.rena.lost.common.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;

import java.util.Random;

public class DecayEffect extends Effect {
    public DecayEffect(EffectType typeIn, int liquidColorIn) {
        super(typeIn, liquidColorIn);
    }

    @Override
    public void performEffect(LivingEntity entityLivingBaseIn, int amplifier) {
        Random random = new Random();
        double randomValue = random.nextDouble();

        double stopMovementProbability = 0.2;
        if (randomValue < stopMovementProbability) {
            entityLivingBaseIn.setMotion(0, 0, 0);
        }
    }

    @Override
    public boolean isReady(int duration, int amplifier) {
        return true;
    }
}
