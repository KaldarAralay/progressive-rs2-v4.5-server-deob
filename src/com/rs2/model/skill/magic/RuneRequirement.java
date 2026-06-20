/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.magic;

import com.rs2.model.combat.requirement.SpellRuneCostRequirement;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.skill.magic.SpellDefinition;

final class RuneRequirement
extends SpellRuneCostRequirement {
    RuneRequirement(MagicSpellAction magicSpellAction, SpellDefinition spellDefinition) {
        super(spellDefinition);
    }

    public final String a() {
        return "You do not have the runes required!";
    }
}

