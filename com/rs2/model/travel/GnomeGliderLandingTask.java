/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.travel;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class GnomeGliderLandingTask
extends CycleEvent {
    private final /* synthetic */ Player player;

    public GnomeGliderLandingTask(Player player) {
        this.player = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.setActionLocked(false);
        Player player = this.player;
        player.packetSender.closeInterfaces();
        player = this.player;
        player.packetSender.sendConfig(153, -1);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

