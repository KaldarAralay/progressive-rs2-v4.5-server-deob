/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.Entity;
import com.rs2.model.EntityUpdateState;
import com.rs2.model.World;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.LyrePerformanceStartTask;
import com.rs2.model.task.TickTask;

final class LyrePerformanceHeckleTask
extends TickTask {
    private /* synthetic */ LyrePerformanceStartTask a;
    private final /* synthetic */ int b;
    private final /* synthetic */ int c;
    private final /* synthetic */ Player d;
    private final /* synthetic */ int e;

    LyrePerformanceHeckleTask(LyrePerformanceStartTask lyrePerformanceStartTask, int n, int n2, int n3, Player player, int n4) {
        this.a = lyrePerformanceStartTask;
        this.b = n2;
        this.c = n3;
        this.d = player;
        this.e = n4;
        super(4);
    }

    @Override
    public final void execute() {
        Object object = this.a;
        object = Npc.findByDefinitionId(((LyrePerformanceStartTask)object).d.d[this.b]);
        if (object != null) {
            EntityUpdateState entityUpdateState = ((Entity)object).getUpdateState();
            object = this.a;
            entityUpdateState.setForcedText(((LyrePerformanceStartTask)object).d.c[this.c]);
        }
        object = this.a;
        this.d.getUpdateState().setForcedText(((LyrePerformanceStartTask)object).d.b[this.e][3]);
        World.getTaskScheduler().schedule(this.a.c);
        this.stop();
    }
}

