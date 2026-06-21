/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot;

import com.rs2.bot.BotPlayer;
import com.rs2.model.GameplayHelper;
import com.rs2.model.task.TickTask;

final class BotTaskSelectionTask
extends TickTask {
    private final /* synthetic */ BotPlayer bot;

    BotTaskSelectionTask(BotPlayer botPlayer, int n, BotPlayer botPlayer2) {
        this.bot = botPlayer2;
        super(2);
    }

    @Override
    public final void execute() {
        GameplayHelper.startNextBotTask(this.bot);
        this.stop();
    }
}

