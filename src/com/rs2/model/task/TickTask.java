/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.task;

public abstract class TickTask {
    private int a;
    private int b;
    private boolean c = true;
    private boolean d = true;

    public TickTask(int n) {
        this(n, false);
    }

    public TickTask(int n, boolean bl) {
        this.a = n;
        this.b = n;
        this.d = bl;
    }

    public final int getIntervalTicks() {
        return this.b;
    }

    public final int getRemainingTicks() {
        return this.a;
    }

    public final void setIntervalTicks(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Tick amount must be positive.");
        }
        this.b = n;
    }

    public final void setRemainingTicks(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Tick amount must be positive.");
        }
        this.a = n;
    }

    public final boolean isActive() {
        return this.c;
    }

    public void stop() {
        this.c = false;
    }

    public abstract void execute();

    public final void tick() {
        TickTask tickTask = this;
        if (tickTask.d) {
            this.execute();
            this.d = false;
            return;
        }
        if (this.a-- <= 1) {
            tickTask = this;
            if (tickTask.c) {
                this.execute();
            }
            this.a = this.b;
        }
    }
}

