package com.rena.lost.common.entities.ia;

import com.rena.lost.common.entities.Dakosaurus;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;

public class DakosaurusAttackGoal extends MeleeAttackGoal {

    public DakosaurusAttackGoal(Dakosaurus dakosaurus, double speedIn, boolean useLongMemory) {
        super(dakosaurus, speedIn, useLongMemory);
    }

    @Override
    public boolean shouldExecute() {
        if(this.attacker.getAttackTarget() == null || ((Dakosaurus)this.attacker).shouldUseJumpAttack(this.attacker.getAttackTarget())){
            return false;
        }
        return super.shouldExecute();
    }
}
