/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.FremennikTrialsQuest;
import com.rs2.model.task.TickTask;

final class PeerTheSeerHouseGuardianTask
extends TickTask {
    private final /* synthetic */ Player a;

    PeerTheSeerHouseGuardianTask(FremennikTrialsQuest fremennikTrialsQuest, int n, Player player) {
        this.a = player;
        super(10);
    }

    @Override
    public final void execute() {
        if (!this.a.isRegistered()) {
            this.stop();
            return;
        }
        boolean bl = false;
        int n = 0;
        while (n < FremennikTrialsQuest.a.length) {
            if (FremennikTrialsQuest.a[n].containsExclusive(this.a.getPosition())) {
                bl = true;
                if (!GameplayHelper.i(this.a, 1290)) {
                    Npc npc = new Npc(1290);
                    GameplayHelper.a(this.a, npc, true, true);
                    npc.getUpdateState().setForcedText("Prepare to die, " + this.a.getUsername() + "!");
                }
                this.stop();
            }
            ++n;
        }
        if (!bl) {
            this.a.eq = -1;
            this.stop();
        }
    }
}

