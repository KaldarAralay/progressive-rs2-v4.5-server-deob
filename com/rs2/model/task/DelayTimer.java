/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.task;

import com.rs2.model.World;

public class DelayTimer {
    private int a;
    private int b;

    public DelayTimer(int n) {
        int n2 = n;
        DelayTimer delayTimer = this;
        this.b = n2;
        delayTimer = this;
        this.a = World.tickCount;
    }

    public final int getDelayTicks() {
        return this.b;
    }

    public final void setDelayTicks(int n) {
        this.b = n;
    }

    public final void reset() {
        this.a = World.tickCount;
    }

    public final boolean hasElapsed() {
        return World.tickCount - this.a >= this.b;
    }

    public final int getRemainingTicks() {
        return this.b - (World.tickCount - this.a);
    }
}

