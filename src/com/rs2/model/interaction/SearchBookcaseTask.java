/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.interaction;

import com.rs2.model.interaction.FirstObjectActionTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class SearchBookcaseTask
extends CycleEvent {
    private final /* synthetic */ Player a;

    SearchBookcaseTask(FirstObjectActionTask firstObjectActionTask, Player player) {
        this.a = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        Player player = this.a;
        player.packetSender.sendGameMessage("None of them look very interesting.");
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.a.n(false);
    }
}

