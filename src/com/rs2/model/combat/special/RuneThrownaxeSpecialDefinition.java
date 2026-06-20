/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.special;

import com.rs2.model.Entity;
import com.rs2.model.combat.WeaponProfile;
import com.rs2.model.combat.attack.WeaponCombatAttack;
import com.rs2.model.combat.special.RuneThrownaxeSpecialAttack;
import com.rs2.model.combat.special.SpecialAttackDefinition;
import com.rs2.model.player.Player;

final class RuneThrownaxeSpecialDefinition
extends SpecialAttackDefinition {
    RuneThrownaxeSpecialDefinition(int n2, String ... stringArray) {
    }

    @Override
    public final WeaponCombatAttack createAttack(Player object, Entity entity, WeaponProfile weaponProfile) {
        object = new RuneThrownaxeSpecialAttack(this, (Player)object, entity, weaponProfile, (Player)object, entity, weaponProfile);
        return object;
    }
}

