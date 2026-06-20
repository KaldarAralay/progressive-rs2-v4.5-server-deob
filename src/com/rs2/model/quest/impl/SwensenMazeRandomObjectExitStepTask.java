/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.FremennikTrialsQuest;
import com.rs2.model.task.TickTask;

final class SwensenMazeRandomObjectExitStepTask
extends TickTask {
    private final /* synthetic */ int a;
    private final /* synthetic */ Player b;
    private final /* synthetic */ int c;

    SwensenMazeRandomObjectExitStepTask(FremennikTrialsQuest fremennikTrialsQuest, int n, int n2, Player player, int n3) {
        this.a = n2;
        this.b = player;
        this.c = n3;
        super(1);
    }

    @Override
    public final void execute() {
        Player player;
        if (this.a != 0) {
            player = this.b;
            player.packetSender.queueRelativeMovementStep(0, this.a < 0 ? 1 : -1, true);
        }
        if (this.c != 0) {
            player = this.b;
            player.packetSender.queueRelativeMovementStep(this.c < 0 ? 1 : -1, 0, true);
        }
        this.stop();
    }
}

