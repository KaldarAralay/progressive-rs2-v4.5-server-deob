/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat.effect;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatAction;
import com.rs2.model.combat.effect.CombatEffect;
import com.rs2.model.combat.effect.CombatEffectTask;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;

public final class StatDrainEffect
extends CombatEffect {
    private int skillId;
    private int fixedDrainAmount;
    private double drainFraction;

    public StatDrainEffect(int n, double d) {
        this.skillId = n;
        this.drainFraction = d;
    }

    public StatDrainEffect(int n, int n2) {
        this.skillId = n;
        this.fixedDrainAmount = n2;
    }

    public final int getSkillId() {
        return this.skillId;
    }

    @Override
    public final void onAfterApply(CombatAction combatAction, CombatEffectTask combatEffectTask) {
    }

    @Override
    public final boolean canApplyTo(Entity entity) {
        return true;
    }

    @Override
    public final void onApply(CombatAction object, CombatEffectTask object2) {
        if (((Entity)(object = ((CombatAction)object).getTarget())).isPlayer()) {
            object2 = (Player)object;
            if (this.skillId != -1) {
                int n = this.calculateDrainAmount(this.getBaseLevelForSkill((Entity)object));
                int[] nArray = ((Player)object2).getSkillManager().getCurrentLevels();
                int n2 = this.skillId;
                nArray[n2] = nArray[n2] - n;
                if (((Player)object2).getSkillManager().getCurrentLevels()[this.skillId] < 0) {
                    ((Player)object2).getSkillManager().getCurrentLevels()[this.skillId] = 0;
                }
                ((Player)object2).getSkillManager().refreshSkill(this.skillId);
                return;
            }
            int n = 0;
            while (n < ((Player)object2).getSkillManager().getCurrentLevels().length - 1) {
                int n3 = this.calculateDrainAmount(StatDrainEffect.getBaseLevel((Entity)object, n));
                int[] nArray = ((Player)object2).getSkillManager().getCurrentLevels();
                int n4 = n;
                nArray[n4] = nArray[n4] - n3;
                if (((Player)object2).getSkillManager().getCurrentLevels()[n] < 0) {
                    ((Player)object2).getSkillManager().getCurrentLevels()[n] = 0;
                }
                ++n;
            }
            ((Player)object2).getSkillManager().refreshAllSkills();
            return;
        }
        object2 = (Npc)object;
        if (this.skillId != -1) {
            int n = this.calculateDrainAmount(this.getBaseLevelForSkill((Entity)object));
            if (this.skillId == 0) {
                int n5 = ((Npc)object2).getCurrentAttackLevel();
                int n6 = n5 - n;
                if (n6 < 0) {
                    n6 = 0;
                }
                ((Npc)object2).setCurrentAttackLevel(n6);
                return;
            }
            if (this.skillId == 2) {
                int n7 = ((Npc)object2).getCurrentStrengthLevel();
                int n8 = n7 - n;
                if (n8 < 0) {
                    n8 = 0;
                }
                ((Npc)object2).setCurrentStrengthLevel(n8);
                return;
            }
            if (this.skillId == 1) {
                int n9 = ((Npc)object2).getCurrentDefenceLevel();
                int n10 = n9 - n;
                if (n10 < 0) {
                    n10 = 0;
                }
                ((Npc)object2).setCurrentDefenceLevel(n10);
                return;
            }
            if (this.skillId == 6) {
                int n11 = ((Npc)object2).getCurrentMagicLevel();
                int n12 = n11 - n;
                if (n12 < 0) {
                    n12 = 0;
                }
                ((Npc)object2).setCurrentMagicLevel(n12);
                return;
            }
            if (this.skillId == 4) {
                int n13 = ((Npc)object2).getCurrentRangedLevel();
                int n14 = n13 - n;
                if (n14 < 0) {
                    n14 = 0;
                }
                ((Npc)object2).setCurrentRangedLevel(n14);
            }
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

    private int getBaseLevelForSkill(Entity entity) {
        return StatDrainEffect.getBaseLevel(entity, this.skillId);
    }

    private static int getBaseLevel(Entity entity, int n) {
        if (entity.isPlayer()) {
            entity = (Player)entity;
            return ((Player)entity).getSkillManager().getBaseLevel(n);
        }
        entity = (Npc)entity;
        if (n == 0) {
            return ((Npc)entity).getBaseAttackLevel();
        }
        if (n == 2) {
            return ((Npc)entity).getBaseStrengthLevel();
        }
        if (n == 1) {
            return ((Npc)entity).getBaseDefenceLevel();
        }
        if (n == 6) {
            return ((Npc)entity).getBaseMagicLevel();
        }
        if (n == 4) {
            return ((Npc)entity).getBaseRangedLevel();
        }
        return 0;
    }

    private int calculateDrainAmount(int n) {
        if (this.fixedDrainAmount > 0) {
            return this.fixedDrainAmount;
        }
        return (int)Math.ceil((double)n * this.drainFraction);
    }
}

