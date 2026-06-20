/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.action;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class GodBookRecitationEvent
extends CycleEvent {
    private final /* synthetic */ Player a;
    private final /* synthetic */ String[] b;

    GodBookRecitationEvent(Player player, String[] stringArray) {
        this.a = player;
        this.b = stringArray;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.a.O == this.b.length) {
            cycleEventContainer.stop();
            return;
        }
        this.a.getUpdateState().setForcedText(this.b[this.a.O]);
        ++this.a.O;
    }

    @Override
    public final void onStop() {
        this.a.n(false);
    }
}

