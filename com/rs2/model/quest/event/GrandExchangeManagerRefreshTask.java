/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.event;

import com.rs2.model.player.GrandExchangeManager;
import com.rs2.model.quest.event.ServerMaintenanceEventHook;
import com.rs2.model.task.TickTask;

final class GrandExchangeManagerRefreshTask
extends TickTask {
    GrandExchangeManagerRefreshTask(ServerMaintenanceEventHook serverMaintenanceEventHook, int n) {
        super(1000);
    }

    @Override
    public final void execute() {
        GrandExchangeManager.rollInstantPriceFluctuation();
    }
}

