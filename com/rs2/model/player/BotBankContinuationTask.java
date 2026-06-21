/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class BotBankContinuationTask
extends TickTask {
    private final /* synthetic */ Player bot;

    BotBankContinuationTask(int n, Player player) {
        this.bot = player;
        super(n);
    }

    @Override
    public final void execute() {
        if (this.bot.botMode != 4) {
            GameplayHelper.startNextBotTask(this.bot);
        } else {
            GameplayHelper.selectAndStartNextProgressiveBotTask(this.bot);
        }
        this.stop();
    }
}

