/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.FremennikTrialsQuest;
import com.rs2.model.task.TickTask;

final class SwensenMazeObject4153ExitStepTask
extends TickTask {
    private final /* synthetic */ Player a;

    SwensenMazeObject4153ExitStepTask(FremennikTrialsQuest fremennikTrialsQuest, int n, Player player) {
        this.a = player;
        super(1);
    }

    @Override
    public final void execute() {
        Player player = this.a;
        player.packetSender.queueRelativeMovementStep(0, -1, true);
        this.stop();
    }
}

