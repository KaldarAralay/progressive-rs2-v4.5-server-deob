/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.task.TickTask;

final class DeathCleanupTask
extends TickTask {
    private final /* synthetic */ Entity a;
    private final /* synthetic */ Entity b;

    DeathCleanupTask(int n, Entity entity, Entity entity2) {
        this.a = entity;
        this.b = entity2;
        super(n);
    }

    @Override
    public final void execute() {
        CombatManager.finishDeath(this.a, this.b, true);
        this.stop();
    }
}

