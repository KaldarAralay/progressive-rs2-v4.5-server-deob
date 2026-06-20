/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.PiratesTreasureQuest;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class PiratesTreasureDigCompletionEvent
extends CycleEvent {
    private /* synthetic */ PiratesTreasureQuest a;
    private final /* synthetic */ Player b;
    private final /* synthetic */ int c;

    PiratesTreasureDigCompletionEvent(PiratesTreasureQuest piratesTreasureQuest, Player player, int n) {
        this.a = piratesTreasureQuest;
        this.b = player;
        this.c = n;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.b.isCurrentActionSequence(this.c)) {
            cycleEventContainer.stop();
            return;
        }
        Player player = this.b;
        player.packetSender.sendGameMessage("and find the treasure.");
        this.a.awardCompletionRewards(this.b);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.b.resetAnimation();
    }
}

