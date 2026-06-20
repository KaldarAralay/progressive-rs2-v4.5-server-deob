/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.LyrePerformanceStartTask;
import com.rs2.model.task.TickTask;

final class LyrePerformanceSecondLineTask
extends TickTask {
    private /* synthetic */ LyrePerformanceStartTask a;
    private final /* synthetic */ Player b;
    private final /* synthetic */ int c;

    LyrePerformanceSecondLineTask(LyrePerformanceStartTask lyrePerformanceStartTask, int n, Player player, int n2) {
        this.a = lyrePerformanceStartTask;
        this.b = player;
        this.c = n2;
        super(4);
    }

    @Override
    public final void execute() {
        LyrePerformanceStartTask lyrePerformanceStartTask = this.a;
        this.b.getUpdateState().setForcedText(lyrePerformanceStartTask.d.b[this.c][1]);
        World.getTaskScheduler().schedule(this.a.a);
        this.stop();
    }
}

