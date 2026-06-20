/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.net.packet.handler.PlayerInteractionPacketHandler;

final class DelayedBotTradeRequestTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ String b;
    private final /* synthetic */ Player c;

    DelayedBotTradeRequestTask(Player player, int n, Player player2, String string, Player player3) {
        this.a = player2;
        this.b = string;
        this.c = player3;
        super(n);
    }

    @Override
    public final void execute() {
        if (this.a.isDead() || !this.a.bW()) {
            this.stop();
            return;
        }
        if (this.b.contains("buy") && this.a.tradeAdvertMode == 0) {
            PlayerInteractionPacketHandler.a(this.a, this.c);
        } else if (this.b.contains("sell") && this.a.tradeAdvertMode == 1) {
            PlayerInteractionPacketHandler.a(this.a, this.c);
        }
        this.stop();
    }
}

