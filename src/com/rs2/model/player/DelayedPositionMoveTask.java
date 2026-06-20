/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class DelayedPositionMoveTask
extends CycleEvent {
    private /* synthetic */ Player a;
    private final /* synthetic */ Position b;

    DelayedPositionMoveTask(Player player, Position position) {
        this.a = player;
        this.b = position;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.a.moveTo(this.b);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.a.n(false);
    }
}

