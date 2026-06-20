/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.CombatType;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.effect.CombatEffectTask;
import com.rs2.model.player.Player;

public final class TeleblockEffect
extends CombatEffect {
    private int durationTicks = 500;

    public TeleblockEffect(int n) {
    }

    @Override
    public final void onAfterApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
    }

    @Override
    public final boolean canApplyTo(Entity entity) {
        return !entity.isTeleblocked();
    }

    @Override
    public final void onApply(CombatAction object, CombatEffectTask object2) {
        object2 = ((CombatAction)object).getTarget();
        ((CombatAction)object).getAttacker();
        if (((Entity)object2).isPlayer()) {
            object = (Player)object2;
            ((Player)object).packetSender.sendGameMessage("You have been teleblocked!");
        }
        if (((Entity)object2).isProtectedFrom(CombatType.MAGIC)) {
            ((Entity)object2).getTeleblockTimer().setDelayTicks(this.durationTicks / 2);
        } else {
            ((Entity)object2).getTeleblockTimer().setDelayTicks(this.durationTicks);
        }
        ((Entity)object2).getTeleblockTimer().reset();
    }

    @Override
    public final CombatEffectTask createTask(Entity entity, Entity entity2) {
        return null;
    }

    @Override
    public final boolean equals(Object object) {
        return object instanceof TeleblockEffect;
    }
}

