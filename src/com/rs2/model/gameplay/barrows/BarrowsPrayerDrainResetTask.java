/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.barrows;

import com.rs2.model.gameplay.barrows.BarrowsPrayerDrainTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class BarrowsPrayerDrainResetTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;

    BarrowsPrayerDrainResetTask(BarrowsPrayerDrainTask barrowsPrayerDrainTask, int n, Player player, int n2) {
        this.a = player;
        this.b = n2;
        super(3);
    }

    @Override
    public final void execute() {
        Player player = this.a;
        player.packetSender.sendInterfaceModel(this.b, 200, -1);
        this.stop();
    }
}

