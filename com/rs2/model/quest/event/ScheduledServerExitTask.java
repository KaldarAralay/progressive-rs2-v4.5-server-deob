/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.event;

import com.rs2.model.quest.event.ServerMaintenanceEventHook;
import com.rs2.model.task.TickTask;

final class ScheduledServerExitTask
extends TickTask {
    ScheduledServerExitTask(ServerMaintenanceEventHook serverMaintenanceEventHook, int n) {
        super(n);
    }

    @Override
    public final void execute() {
        System.exit(0);
    }
}

