/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.RestlessGhostQuest;
import com.rs2.model.task.TickTask;

final class RestlessGhostCoffinGhostSpawnTask
extends TickTask {
    private final /* synthetic */ Player a;

    RestlessGhostCoffinGhostSpawnTask(RestlessGhostQuest restlessGhostQuest, int n, Player player) {
        this.a = player;
        super(n);
    }

    @Override
    public final void execute() {
        GameplayHelper.a(this.a, new Npc(457), 3250, 3195, 0, 1500, false, false);
        this.stop();
    }
}

