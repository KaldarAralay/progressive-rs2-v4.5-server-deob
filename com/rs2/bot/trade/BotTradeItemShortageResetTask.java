/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.trade;

import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class BotTradeItemShortageResetTask
extends TickTask {
    private final /* synthetic */ Player player;

    BotTradeItemShortageResetTask(int n, Player player) {
        this.player = player;
        super(10);
    }

    @Override
    public final void execute() {
        GameplayHelper.d(this.player);
        this.stop();
    }
}

