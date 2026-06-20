/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot;

import com.rs2.bot.BotPlayer;
import com.rs2.bot.combat.BotCombatLoadoutManager;
import com.rs2.model.task.TickTask;

final class BotCombatLoadoutTask
extends TickTask {
    private final /* synthetic */ BotPlayer a;

    BotCombatLoadoutTask(BotPlayer botPlayer, int n, BotPlayer botPlayer2) {
        this.a = botPlayer2;
        super(2);
    }

    @Override
    public final void execute() {
        BotCombatLoadoutManager.a(this.a);
        this.stop();
    }
}

