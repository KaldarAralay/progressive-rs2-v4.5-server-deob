/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill;

import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;

final class SpecialEnergyRestoreTask
extends TickTask {
    private /* synthetic */ SkillManager skillManager;

    SpecialEnergyRestoreTask(SkillManager skillManager, int n) {
        this.skillManager = skillManager;
        super(50);
    }

    @Override
    public final void execute() {
        if (SkillManager.getPlayer(this.skillManager) == null) {
            this.stop();
            return;
        }
        if (!SkillManager.getPlayer((SkillManager)this.skillManager).el || SkillManager.getPlayer(this.skillManager).isDead()) {
            this.stop();
            return;
        }
        if (SkillManager.getPlayer(this.skillManager).getSpecialEnergy() < 100) {
            SkillManager.getPlayer(this.skillManager).setSpecialEnergy(SkillManager.getPlayer(this.skillManager).getSpecialEnergy() + 10);
            SkillManager.getPlayer(this.skillManager).refreshSpecialAttackWidgets();
        }
        if (!this.skillManager.isSpecialEnergyBelowMaximum()) {
            this.stop();
        }
    }
}

