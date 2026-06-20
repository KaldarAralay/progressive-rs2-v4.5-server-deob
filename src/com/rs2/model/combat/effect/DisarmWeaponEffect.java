/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.effect.CombatEffectTask;
import com.rs2.model.player.Player;

public final class DisarmWeaponEffect
extends CombatEffect {
    @Override
    public final void onAfterApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
    }

    @Override
    public final boolean canApplyTo(Entity entity) {
        return true;
    }

    @Override
    public final void onApply(CombatAction object, CombatEffectTask combatEffectTask) {
        if (((Entity)(object = ((CombatAction)object).getTarget())).isPlayer()) {
            object = (Player)object;
            ((Player)object).getEquipmentManager().unequipSlot(3);
        }
    }

    @Override
    public final CombatEffectTask createTask(Entity entity, Entity entity2) {
        return null;
    }

    @Override
    public final boolean equals(Object object) {
        return false;
    }
}

