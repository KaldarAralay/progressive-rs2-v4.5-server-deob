/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.requirement;

import com.rs2.model.combat.attack.MagicCombatAttack;
import com.rs2.model.combat.requirement.SkillLevelRequirement;

final class MagicCombatLevelRequirement
extends SkillLevelRequirement {
    MagicCombatLevelRequirement(MagicCombatAttack magicCombatAttack, int n, int n2) {
        super(6, n2);
    }

    @Override
    public final String getFailureMessage() {
        return "Your level in magic is not high enough!";
    }
}

