/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.World;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GrandTreeQuest;
import com.rs2.model.task.TickTask;

final class GloughDemonSpawnTask
extends TickTask {
    private final /* synthetic */ Player a;

    GloughDemonSpawnTask(GrandTreeQuest grandTreeQuest, int n, Player player) {
        this.a = player;
        super(3);
    }

    @Override
    public final void execute() {
        Npc npc;
        if (this.a.getInteractionTarget() != null && this.a.getInteractionTarget().isNpc() && (npc = (Npc)this.a.getInteractionTarget()).getNpcId() == 671) {
            npc.setActive(false);
            World.b(npc);
        }
        npc = new Npc(677);
        GameplayHelper.b(this.a, npc, 2484, 9864, 0, -1, true, false);
        this.a.n(false);
        this.stop();
    }
}

