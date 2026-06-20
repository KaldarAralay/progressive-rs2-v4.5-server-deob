/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model;

import com.rs2.ServerSettings;
import com.rs2.bot.BotPlayer;
import com.rs2.model.task.TickTask;

final class BotReloginTask
extends TickTask {
    private final /* synthetic */ String a;

    BotReloginTask(int n, String string) {
        this.a = string;
        super(30);
    }

    @Override
    public final void execute() {
        int n = ServerSettings.skillingBotsEnabled ? 0 : 4;
        BotPlayer.createNamedBot(this.a, "zxcvbn", n);
        this.stop();
    }
}

