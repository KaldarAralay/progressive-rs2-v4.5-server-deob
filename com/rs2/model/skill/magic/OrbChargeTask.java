/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.magic;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class OrbChargeTask
extends CycleEvent {
    private int a;
    private final /* synthetic */ Player b;

    OrbChargeTask(int n, Player player) {
        this.b = player;
        this.a = n;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.a == 0 || !this.b.B.tryStartCast()) {
            cycleEventContainer.stop();
            return;
        }
        --this.a;
    }

    @Override
    public final void onStop() {
        this.b.aN();
    }
}

