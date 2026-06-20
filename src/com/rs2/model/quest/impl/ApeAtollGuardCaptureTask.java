/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class ApeAtollGuardCaptureTask
extends TickTask {
    private final /* synthetic */ Player a;

    ApeAtollGuardCaptureTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = player;
        super(3);
    }

    @Override
    public final void execute() {
        this.a.ai = false;
        this.a.n(false);
        this.a.moveTo(new Position(2772, 2794, 0));
        this.a.aN();
        Player player = this.a;
        player.packetSender.showWalkableInterface(-1);
        this.stop();
    }
}

