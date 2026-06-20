/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class ArdougneZooGnomeRescueTask
extends TickTask {
    private final /* synthetic */ Player a;

    ArdougneZooGnomeRescueTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = player;
        super(5);
    }

    @Override
    public final void execute() {
        this.a.setActionLocked(false);
        this.a.moveTo(new Position(2606, 3274, 0));
        Player player = this.a;
        player.packetSender.closeInterfaces();
        this.stop();
    }
}

