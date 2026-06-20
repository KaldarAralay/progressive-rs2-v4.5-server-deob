/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.npc.combat;

import com.rs2.model.Entity;
import com.rs2.model.combat.AttackBonusType;
import com.rs2.model.combat.AttackXpMode;
import com.rs2.model.combat.attack.BaseCombatAttack;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.npc.NpcDefinition;
import com.rs2.model.npc.combat.NpcCombatDefinition;

final class NpcDefinitionMeleeCombatDefinition
extends NpcCombatDefinition {
    private final /* synthetic */ NpcDefinition a;

    NpcDefinitionMeleeCombatDefinition(NpcDefinition npcDefinition) {
        this.a = npcDefinition;
    }

    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        return new CombatAttack[]{BaseCombatAttack.a(entity, entity2, AttackXpMode.MELEE_ACCURATE, AttackBonusType.STAB, NpcDefinition.getDefaultMaxHit(this.a), NpcDefinition.getDefaultAttackDelay(this.a), NpcDefinition.getDefaultAttackAnimationId(this.a))};
    }
}

