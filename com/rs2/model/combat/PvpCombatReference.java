/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat;

import com.rs2.model.Entity;
import com.rs2.model.EntityReference;
import com.rs2.model.task.DelayTimer;

public final class PvpCombatReference
extends EntityReference {
    private DelayTimer a;

    public PvpCombatReference(Entity entity, int n) {
        super(entity);
        this.a = new DelayTimer(n);
    }

    public final boolean b() {
        return this.a.hasElapsed();
    }

    public final void c() {
        this.a.reset();
    }

    public final int d() {
        return this.a.getRemainingTicks();
    }
}

