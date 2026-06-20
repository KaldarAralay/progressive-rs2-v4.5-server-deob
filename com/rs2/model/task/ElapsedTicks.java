/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.task;

import com.rs2.model.World;

public final class ElapsedTicks {
    private int a;

    public ElapsedTicks() {
        ElapsedTicks elapsedTicks = this;
        this.a = World.b;
    }

    public final int elapsed() {
        return World.b - this.a;
    }

    public final void reset() {
        this.a = World.b;
    }
}

