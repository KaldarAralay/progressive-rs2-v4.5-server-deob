/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.event;

import com.rs2.model.quest.event.HalloweenMaskDropEventHook;
import com.rs2.model.task.TickTask;

final class HalloweenMaskDropRefreshTask
extends TickTask {
    private /* synthetic */ HalloweenMaskDropEventHook a;

    HalloweenMaskDropRefreshTask(HalloweenMaskDropEventHook halloweenMaskDropEventHook, int n) {
        this.a = halloweenMaskDropEventHook;
        super(n);
    }

    @Override
    public final void execute() {
        HalloweenMaskDropEventHook.a(this.a);
    }
}

