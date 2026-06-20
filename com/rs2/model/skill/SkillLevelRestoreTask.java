/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill;

import com.rs2.model.skill.SkillManager;
import com.rs2.model.task.TickTask;

final class SkillLevelRestoreTask
extends TickTask {
    private /* synthetic */ SkillManager a;
    private final /* synthetic */ int b;

    SkillLevelRestoreTask(SkillManager skillManager, int n, int n2) {
        this.a = skillManager;
        this.b = n2;
        super(n);
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
        if (SkillManager.getCurrentLevels(this.a)[this.b] != SkillManager.getLevelForExperience(this.a.getExperience()[this.b])) {
            if (SkillManager.getCurrentLevels(this.a)[this.b] > SkillManager.getLevelForExperience(this.a.getExperience()[this.b])) {
                int[] nArray = SkillManager.getCurrentLevels(this.a);
                int n = this.b;
                nArray[n] = nArray[n] - 1;
            } else {
                int[] nArray = SkillManager.getCurrentLevels(this.a);
                int n = this.b;
                nArray[n] = nArray[n] + 1;
            }
            this.a.refreshSkill(this.b);
        }
        if (!this.a.isLevelModified(this.b)) {
            this.stop();
        }
    }
}

