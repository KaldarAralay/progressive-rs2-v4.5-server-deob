/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.event;

import com.rs2.model.quest.event.ChristmasDropEventHook;
import com.rs2.model.task.TickTask;

final class ChristmasDropRefreshTask
extends TickTask {
    private /* synthetic */ ChristmasDropEventHook a;

    ChristmasDropRefreshTask(ChristmasDropEventHook christmasDropEventHook, int n) {
        this.a = christmasDropEventHook;
        super(n);
    }

    @Override
    public final void execute() {
        ChristmasDropEventHook.a(this.a);
    }
}

