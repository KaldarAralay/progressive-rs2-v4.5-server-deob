/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill;

import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;

final class RunEnergyRestoreTask
extends TickTask {
    private /* synthetic */ SkillManager a;

    RunEnergyRestoreTask(SkillManager skillManager, int n) {
        this.a = skillManager;
        super(50);
    }

    @Override
    public final void execute() {
        if (SkillManager.getPlayer(this.a) == null) {
            this.stop();
            return;
        }
        if (!SkillManager.getPlayer((SkillManager)this.a).el || SkillManager.getPlayer(this.a).isDead()) {
            this.stop();
            return;
        }
        if (SkillManager.getPlayer(this.a).getSpecialEnergy() < 100) {
            SkillManager.getPlayer(this.a).setSpecialEnergy(SkillManager.getPlayer(this.a).getSpecialEnergy() + 10);
            SkillManager.getPlayer(this.a).refreshSpecialAttackWidgets();
        }
        if (!this.a.isRunEnergyBelowMaximum()) {
            this.stop();
        }
    }
}

