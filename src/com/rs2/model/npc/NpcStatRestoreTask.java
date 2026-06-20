/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.npc;

import com.rs2.model.npc.Npc;
import com.rs2.model.task.TickTask;

final class NpcStatRestoreTask
extends TickTask {
    private /* synthetic */ Npc a;
    private final /* synthetic */ Npc b;
    private final /* synthetic */ int c;

    NpcStatRestoreTask(Npc npc, int n, Npc npc2, int n2) {
        this.a = npc;
        this.b = npc2;
        this.c = n2;
        super(100);
    }

    @Override
    public final void execute() {
        if (this.b.isDead()) {
            this.stop();
            return;
        }
        if (this.a.getCurrentLevelForSkill(this.c) != this.a.getBaseLevelForSkill(this.c)) {
            if (this.a.getCurrentLevelForSkill(this.c) > this.a.getBaseLevelForSkill(this.c)) {
                this.a.adjustCurrentLevel(this.c, -1);
            } else {
                this.a.adjustCurrentLevel(this.c, 1);
            }
        }
        if (!this.a.isStatModified(this.c)) {
            this.stop();
        }
    }
}

