/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.task;

import com.rs2.model.Entity;
import com.rs2.model.task.CycleEvent;

public final class CycleEventContainer {
    private Entity a;
    private boolean b;
    private int c;
    private CycleEvent d;
    private int e;

    public CycleEventContainer(Entity entity, CycleEvent cycleEvent, int n) {
        this.a = entity;
        this.d = cycleEvent;
        this.b = true;
        this.e = 0;
        this.c = n;
    }

    public final void execute() {
        this.d.execute(this);
    }

    public final void stop() {
        this.b = false;
        this.d.onStop();
    }

    public final boolean tick() {
        if (++this.e >= this.c) {
            this.e = 0;
            return true;
        }
        return false;
    }

    public final Entity getEntity() {
        return this.a;
    }

    public final boolean isActive() {
        return this.b;
    }

    public final void setTickDelay(int n) {
        this.c = n;
    }
}

