/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class DaeroPuzzleCompletionTeleportTask
extends TickTask {
    private final /* synthetic */ Player a;

    DaeroPuzzleCompletionTeleportTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = player;
        super(5);
    }

    @Override
    public final void execute() {
        this.a.moveTo(new Position(this.a.getPosition().getX() + 64, this.a.getPosition().getY(), 0));
        this.a.setActionLocked(false);
        Player player = this.a;
        player.packetSender.closeInterfaces();
        this.stop();
    }
}

