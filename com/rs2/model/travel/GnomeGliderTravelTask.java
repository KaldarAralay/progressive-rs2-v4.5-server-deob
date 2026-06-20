/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.travel;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.model.travel.GnomeGliderDestination;

final class GnomeGliderTravelTask
extends CycleEvent {
    private final /* synthetic */ Player player;
    private final /* synthetic */ GnomeGliderDestination destination;

    GnomeGliderTravelTask(Player player, GnomeGliderDestination gnomeGliderDestination) {
        this.player = player;
        this.destination = gnomeGliderDestination;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.player.moveTo(this.destination.destination);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

