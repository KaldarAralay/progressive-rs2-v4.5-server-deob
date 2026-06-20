/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.effect.CombatEffectTask;

public class MovementLockEffect
extends CombatEffect {
    private int durationTicks;

    public MovementLockEffect(int n) {
        this.durationTicks = n;
    }

    @Override
    public void onAfterApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
    }

    @Override
    public final boolean canApplyTo(Entity entity) {
        return entity.getMovementLockImmunityTimer().hasElapsed();
    }

    @Override
    public final void onApply(CombatAction object, CombatEffectTask combatEffectTask) {
        object = ((CombatAction)object).getTarget();
        ((Entity)object).getMovementQueue().clear();
        ((Entity)object).getMovementLockTimer().setDelayTicks(this.durationTicks);
        ((Entity)object).getMovementLockTimer().reset();
        ((Entity)object).getMovementLockImmunityTimer().setDelayTicks(this.durationTicks + 6);
        ((Entity)object).getMovementLockImmunityTimer().reset();
    }

    @Override
    public final CombatEffectTask createTask(Entity entity, Entity entity2) {
        return null;
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof MovementLockEffect;
    }

    public final int getDurationTicks() {
        return this.durationTicks;
    }
}

