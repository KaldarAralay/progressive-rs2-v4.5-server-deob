/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.action;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class ToyHorseyUnlockEvent
extends CycleEvent {
    private final /* synthetic */ Player a;

    ToyHorseyUnlockEvent(Player player) {
        this.a = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.a.n(false);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

