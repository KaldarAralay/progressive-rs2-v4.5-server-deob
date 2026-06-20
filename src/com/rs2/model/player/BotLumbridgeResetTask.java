/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class BotLumbridgeResetTask
extends TickTask {
    private /* synthetic */ Player a;
    private final /* synthetic */ Player b;

    BotLumbridgeResetTask(Player player, int n, Player player2) {
        this.a = player;
        this.b = player2;
        super(2);
    }

    @Override
    public final void execute() {
        this.a.bB = false;
        this.b.deferredBotTask = null;
        GameplayHelper.a(this.b, false);
        this.stop();
    }
}

