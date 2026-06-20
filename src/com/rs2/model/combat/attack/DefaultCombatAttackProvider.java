/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.attack;

import com.rs2.model.Entity;
import com.rs2.model.combat.attack.CombatAttack;
import com.rs2.model.combat.attack.CombatAttackProvider;
import com.rs2.model.combat.attack.MagicCombatAttack;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.special.SpecialAttackDefinition;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;

public final class DefaultCombatAttackProvider
implements CombatAttackProvider {
    @Override
    public final CombatAttack[] createAttacks(Entity entity, Entity entity2) {
        if (entity.isPlayer()) {
            Player player = (Player)entity;
            SpecialAttackDefinition specialAttackDefinition = player.dU();
            if (player.ec() != null) {
                return new CombatAttack[]{new MagicCombatAttack(entity, entity2, player.ec())};
            }
            if (player.ed() != null && player.ee()) {
                return new CombatAttack[]{new MagicCombatAttack(entity, entity2, player.ed())};
            }
            if (specialAttackDefinition != null && player.isSpecialAttackEnabled()) {
                return new CombatAttack[]{specialAttackDefinition.createAttack(player, entity2, player.getWeaponProfile())};
            }
            return new CombatAttack[]{new WeaponCombatAttack(player, entity2, player.getWeaponProfile())};
        }
        return ((Npc)entity).getCombatDefinition().createAttacks(entity, entity2);
    }
}

