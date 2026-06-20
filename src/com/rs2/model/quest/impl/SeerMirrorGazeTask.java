/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ScorpionCatcherQuest;
import com.rs2.model.quest.impl.SeerMirrorHairSmoothTask;
import com.rs2.model.quest.impl.SeerMirrorResultDialogueTask;
import com.rs2.model.task.TickTask;

final class SeerMirrorGazeTask
extends TickTask {
    private TickTask b;
    final TickTask a;
    private final /* synthetic */ Player c;

    SeerMirrorGazeTask(ScorpionCatcherQuest scorpionCatcherQuest, int n, Player player) {
        this.c = player;
        super(4);
        this.b = new SeerMirrorHairSmoothTask(this, 4, player);
        this.a = new SeerMirrorResultDialogueTask(this, 4, player);
    }

    @Override
    public final void execute() {
        Player player = this.c;
        player.packetSender.sendGameMessage("The seer gazes into the mirror");
        World.getTaskScheduler().schedule(this.b);
        this.stop();
    }
}

