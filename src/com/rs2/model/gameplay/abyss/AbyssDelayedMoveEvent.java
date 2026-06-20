/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.abyss;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class AbyssDelayedMoveEvent
extends CycleEvent {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;
    private final /* synthetic */ int c;

    AbyssDelayedMoveEvent(Player player, int n, int n2) {
        this.a = player;
        this.b = n;
        this.c = n2;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.a.moveTo(new Position(this.b, this.c, this.a.getPosition().getPlane()));
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.a.n(false);
    }
}

