/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.npc;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class MageArenaChallengeStartTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ Npc b;

    MageArenaChallengeStartTask(Npc npc, int n, Player player, Npc npc2) {
        this.a = player;
        this.b = npc2;
        super(12);
    }

    @Override
    public final void execute() {
        GameplayHelper.a(this.a, new Position(3106, 3934, 0), this.b, true, false);
        this.b.getUpdateState().setGraphic(86, 0);
        if (this.a.dN == 0) {
            this.b.getUpdateState().setForcedText("You must prove yourself... now!");
        } else {
            this.b.getUpdateState().setForcedText("Let us continue with our battle.");
        }
        this.stop();
    }
}

