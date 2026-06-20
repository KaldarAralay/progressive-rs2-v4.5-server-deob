/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.duel;

import com.rs2.model.gameplay.duel.DuelSession;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class DuelCountdownTask
extends CycleEvent {
    private int a = 3;
    private /* synthetic */ DuelSession b;

    DuelCountdownTask(DuelSession duelSession) {
        this.b = duelSession;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        DuelSession.a(this.b).getUpdateState().setForcedTextAndMarkUpdated("" + this.a);
        --this.a;
        if (this.a < 0) {
            cycleEventContainer.stop();
        }
    }

    @Override
    public final void onStop() {
        DuelSession.a(this.b).getUpdateState().setForcedTextAndMarkUpdated("FIGHT!");
        Player player = DuelSession.a(this.b);
        player.packetSender.sendMusicJingle(204, 300);
        this.b.b(true);
    }
}

