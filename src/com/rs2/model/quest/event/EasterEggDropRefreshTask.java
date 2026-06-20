/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.event;

import com.rs2.model.quest.event.EasterEggDropEventHook;
import com.rs2.model.task.TickTask;

final class EasterEggDropRefreshTask
extends TickTask {
    private /* synthetic */ EasterEggDropEventHook a;

    EasterEggDropRefreshTask(EasterEggDropEventHook easterEggDropEventHook, int n) {
        this.a = easterEggDropEventHook;
        super(n);
    }

    @Override
    public final void execute() {
        EasterEggDropEventHook.a(this.a);
    }
}

