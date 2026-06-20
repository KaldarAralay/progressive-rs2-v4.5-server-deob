/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class DelayedBotLevelReplyTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ String b;

    DelayedBotLevelReplyTask(Player player, int n, Player player2, String string) {
        this.a = player2;
        this.b = string;
        super(n);
    }

    @Override
    public final void execute() {
        if (this.a.isDead() || !this.a.isRegistered()) {
            this.stop();
            return;
        }
        this.a.queuePublicChatMessage(this.b);
        this.stop();
    }
}

