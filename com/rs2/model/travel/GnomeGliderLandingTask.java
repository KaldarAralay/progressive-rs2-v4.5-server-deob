/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.travel;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class GnomeGliderLandingTask
extends CycleEvent {
    private final /* synthetic */ Player a;

    GnomeGliderLandingTask(Player player) {
        this.a = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.a.setActionLocked(false);
        Player player = this.a;
        player.packetSender.closeInterfaces();
        player = this.a;
        player.packetSender.sendConfig(153, -1);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

