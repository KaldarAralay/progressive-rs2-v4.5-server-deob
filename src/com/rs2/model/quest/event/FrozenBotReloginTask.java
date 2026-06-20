/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.event;

import com.rs2.ServerSettings;
import com.rs2.bot.BotPlayer;
import com.rs2.model.quest.event.FrozenBotRelogScanTask;
import com.rs2.model.task.TickTask;
import java.util.ArrayList;

final class FrozenBotReloginTask
extends TickTask {
    private final /* synthetic */ ArrayList a;

    FrozenBotReloginTask(FrozenBotRelogScanTask frozenBotRelogScanTask, int n, ArrayList arrayList) {
        this.a = arrayList;
        super(30);
    }

    @Override
    public final void execute() {
        for (String string : this.a) {
            int n = ServerSettings.skillingBotsEnabled ? 0 : 4;
            BotPlayer.createNamedBot(string, "zxcvbn", n);
        }
        this.stop();
    }
}

