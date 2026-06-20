/*
 * Decompiled with CFR 0.152.
 */
package com.rs2;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.bot.BotPlayer;
import com.rs2.model.task.TickTask;

final class BotLoginBatchTask
extends TickTask {
    private final /* synthetic */ int a;

    BotLoginBatchTask(int n, int n2) {
        this.a = n2;
        super(n);
    }

    @Override
    public final void execute() {
        int n = Server.h();
        int n2 = 1 + Server.j * n;
        n = Server.j + Server.j * n;
        if (Server.i() < n) {
            n = Server.i();
        }
        while (n2 <= n) {
            if (n2 > 0 && n2 < ServerSettings.botLoginIdLimit) {
                BotPlayer.createBotFromPool(n2, "zxcvbn", Server.c());
                if (n2 == Server.i()) {
                    this.stop();
                    return;
                }
            }
            ++n2;
        }
        if (this.a == Server.h()) {
            this.stop();
            return;
        }
        Server.a(Server.h() + 1);
    }
}

