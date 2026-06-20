/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.SeerMirrorGazeTask;
import com.rs2.model.task.TickTask;

final class SeerMirrorHairSmoothTask
extends TickTask {
    private /* synthetic */ SeerMirrorGazeTask a;
    private final /* synthetic */ Player b;

    SeerMirrorHairSmoothTask(SeerMirrorGazeTask seerMirrorGazeTask, int n, Player player) {
        this.a = seerMirrorGazeTask;
        this.b = player;
        super(4);
    }

    @Override
    public final void execute() {
        Player player = this.b;
        player.packetSender.sendGameMessage("The seer smoothes his hair with his hand");
        World.getTaskScheduler().schedule(this.a.a);
        this.stop();
    }
}

