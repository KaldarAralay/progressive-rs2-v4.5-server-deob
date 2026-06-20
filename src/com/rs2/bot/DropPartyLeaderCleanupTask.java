/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class DropPartyLeaderCleanupTask
extends TickTask {
    private final /* synthetic */ Player leader;

    DropPartyLeaderCleanupTask(int n, Player player) {
        this.leader = player;
        super(10);
    }

    @Override
    public final void execute() {
        if (this.leader.isDead() || !this.leader.bW()) {
            this.stop();
            return;
        }
        this.leader.moveTo(new Position(9999, 9999, 0));
        this.stop();
    }
}

