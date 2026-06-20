/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.effect.CombatEffectTask;
import com.rs2.model.npc.Npc;
import com.rs2.util.GameUtil;
import com.rs2.util.path.WalkingCollisionMap;

public final class TargetRandomTeleportEffect
extends CombatEffect {
    @Override
    public final void onAfterApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
    }

    @Override
    public final boolean canApplyTo(Entity entity) {
        return true;
    }

    @Override
    public final void onApply(CombatAction object, CombatEffectTask object2) {
        int n;
        int n2;
        object2 = ((CombatAction)object).getAttacker();
        object = ((CombatAction)object).getTarget();
        if (((Entity)object2).isNpc()) {
            n2 = ((Npc)object2).getSpawnPosition().getX() - 10 + GameUtil.randomInclusive(20);
            n = ((Npc)object2).getSpawnPosition().getY() - 10 + GameUtil.randomInclusive(20);
        } else {
            n2 = ((Entity)object2).getPosition().getX() - 10 + GameUtil.randomInclusive(20);
            n = ((Entity)object2).getPosition().getY() - 10 + GameUtil.randomInclusive(20);
        }
        boolean bl = false;
        if (WalkingCollisionMap.getTileFlags(n2, n, ((Entity)object).getPosition().getPlane()) != 0) {
            bl = true;
        }
        while (bl) {
            n2 = ((Entity)object2).getPosition().getX() - 10 + GameUtil.randomInclusive(20);
            bl = WalkingCollisionMap.getTileFlags(n2, n = ((Entity)object2).getPosition().getY() - 10 + GameUtil.randomInclusive(20), ((Entity)object).getPosition().getPlane()) != 0;
        }
        if (n2 > 0) {
            ((Entity)object).moveTo(new Position(n2, n));
        }
        CombatManager.stopCombat((Entity)object);
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

