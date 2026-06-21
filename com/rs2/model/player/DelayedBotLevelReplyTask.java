/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class DelayedBotLevelReplyTask
extends TickTask {
    private final /* synthetic */ Player bot;
    private final /* synthetic */ String replyMessage;

    DelayedBotLevelReplyTask(Player player, int n, Player player2, String string) {
        this.bot = player2;
        this.replyMessage = string;
        super(n);
    }

    @Override
    public final void execute() {
        if (this.bot.isDead() || !this.bot.isRegistered()) {
            this.stop();
            return;
        }
        this.bot.queuePublicChatMessage(this.replyMessage);
        this.stop();
    }
}

