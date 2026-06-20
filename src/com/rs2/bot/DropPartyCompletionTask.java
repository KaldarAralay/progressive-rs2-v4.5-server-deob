/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot;

import com.rs2.bot.DropPartyBotManager;
import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class DropPartyCompletionTask
extends TickTask {
    private final /* synthetic */ Player participant;

    DropPartyCompletionTask(int n, Player player) {
        this.participant = player;
        super(10);
    }

    @Override
    public final void execute() {
        if (this.participant.isDead() || !this.participant.bW()) {
            this.stop();
            DropPartyBotManager.dropPartyActive = false;
            return;
        }
        this.participant.moveTo(new Position(9999, 9999, 0));
        DropPartyBotManager.dropPartyActive = false;
        this.stop();
    }
}

