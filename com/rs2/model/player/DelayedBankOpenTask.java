/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.player.BankManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class DelayedBankOpenTask
extends TickTask {
    private final /* synthetic */ Player a;

    DelayedBankOpenTask(int n, Player player) {
        this.a = player;
        super(n);
    }

    @Override
    public final void execute() {
        BankManager.openBank(this.a, this.a);
        this.stop();
    }
}

