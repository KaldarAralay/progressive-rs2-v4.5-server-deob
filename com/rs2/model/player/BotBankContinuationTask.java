/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class BotBankContinuationTask
extends TickTask {
    private final /* synthetic */ Player a;

    BotBankContinuationTask(int n, Player player) {
        this.a = player;
        super(n);
    }

    @Override
    public final void execute() {
        if (this.a.botMode != 4) {
            GameplayHelper.startNextBotTask(this.a);
        } else {
            GameplayHelper.c(this.a);
        }
        this.stop();
    }
}

