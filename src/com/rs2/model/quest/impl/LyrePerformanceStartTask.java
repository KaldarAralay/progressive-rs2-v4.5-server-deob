/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.FremennikTrialsQuest;
import com.rs2.model.quest.impl.LyrePerformanceFinishDialogueTask;
import com.rs2.model.quest.impl.LyrePerformanceHeckleTask;
import com.rs2.model.quest.impl.LyrePerformanceSecondLineTask;
import com.rs2.model.quest.impl.LyrePerformanceThirdLineTask;
import com.rs2.model.task.TickTask;

final class LyrePerformanceStartTask
extends TickTask {
    private TickTask e;
    final TickTask a;
    final TickTask b;
    final TickTask c;
    final /* synthetic */ FremennikTrialsQuest d;
    private final /* synthetic */ int f;
    private final /* synthetic */ Player g;

    LyrePerformanceStartTask(FremennikTrialsQuest fremennikTrialsQuest, int n, int n2, Player player, int n3, int n4) {
        this.d = fremennikTrialsQuest;
        this.f = n2;
        this.g = player;
        super(2);
        this.e = new LyrePerformanceSecondLineTask(this, 4, player, n2);
        this.a = new LyrePerformanceThirdLineTask(this, 4, player, n2);
        this.b = new LyrePerformanceHeckleTask(this, 4, n3, n4, player, n2);
        this.c = new LyrePerformanceFinishDialogueTask(this, 4, player);
    }

    @Override
    public final void execute() {
        String string = this.d.b[this.f][0].replaceAll("PLAYERNAME", this.g.getUsername());
        this.g.getUpdateState().setForcedText(string);
        World.getTaskScheduler().schedule(this.e);
        this.stop();
    }
}

