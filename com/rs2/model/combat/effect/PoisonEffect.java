/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.effect.CombatEffectTask;
import com.rs2.model.combat.effect.PoisonDamageTask;
import com.rs2.model.player.Player;

public class PoisonEffect
extends CombatEffect {
    private double initialDamage;
    private boolean notifyTarget;

    public PoisonEffect(double d) {
        this.initialDamage = d;
        this.notifyTarget = true;
    }

    public PoisonEffect(double d, boolean bl) {
        this.initialDamage = d;
        this.notifyTarget = false;
    }

    @Override
    public final boolean canApplyTo(Entity entity) {
        return entity.getPoisonImmunityTimer().hasElapsed();
    }

    @Override
    public boolean equals(Object object) {
        return object instanceof PoisonEffect;
    }

    @Override
    public final /* synthetic */ void onApply(CombatAction object, CombatEffectTask object2) {
        PoisonDamageTask poisonDamageTask = (PoisonDamageTask)object2;
        object2 = object;
        object = this;
        if (((Entity)(object2 = ((CombatAction)object2).getTarget())).isPlayer() && ((PoisonEffect)object).notifyTarget) {
            Player player = (Player)object2;
            player.packetSender.sendGameMessage("You've been poisoned.");
        }
        ((Entity)object2).setPoisonDamage(((PoisonEffect)object).initialDamage);
        poisonDamageTask.execute();
    }

    @Override
    public final /* synthetic */ CombatEffectTask createTask(Entity object, Entity entity) {
        Entity entity2 = entity;
        entity = object;
        object = this;
        return new PoisonDamageTask((PoisonEffect)object, (PoisonEffect)object, entity, entity2);
    }

    static /* synthetic */ double getInitialDamage(PoisonEffect poisonEffect) {
        return poisonEffect.initialDamage;
    }
}

