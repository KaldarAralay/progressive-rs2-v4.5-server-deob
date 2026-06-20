/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import java.util.ArrayList;

final class RetryUnreachableObjectTask
extends TickTask {
    private /* synthetic */ Player a;
    private final /* synthetic */ Player b;
    private final /* synthetic */ ArrayList targetIds;

    RetryUnreachableObjectTask(Player player, int n, Player player2, ArrayList arrayList) {
        this.a = player;
        this.b = player2;
        this.targetIds = arrayList;
        super(10);
    }

    @Override
    public final void execute() {
        if (this.b.isDead() || !this.b.isRegistered()) {
            this.stop();
            return;
        }
        this.a.interactWithBotObjectTargets(this.targetIds);
        this.stop();
    }
}

