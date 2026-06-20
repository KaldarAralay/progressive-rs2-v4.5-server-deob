/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class KrukAwowogeiEscortTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;

    KrukAwowogeiEscortTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player, int n2) {
        this.a = player;
        this.b = n2;
        super(5);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        this.a.moveTo(new Position(2802, 2762, 0));
        Player player = this.a;
        player.packetSender.sendGameMessage("Kruk escorts you to Awowogei.");
        this.a.setQuestState(this.b, 16);
        player = this.a;
        player.packetSender.closeInterfaces();
        this.stop();
    }
}

