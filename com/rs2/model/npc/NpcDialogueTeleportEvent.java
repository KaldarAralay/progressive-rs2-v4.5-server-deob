/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.npc;

import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class NpcDialogueTeleportEvent
extends CycleEvent {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;
    private final /* synthetic */ int c;
    private final /* synthetic */ int d;

    NpcDialogueTeleportEvent(Npc npc, Player player, int n, int n2, int n3) {
        this.a = player;
        this.b = n;
        this.c = n2;
        this.d = n3;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.a.getTeleportManager().a(this.b, this.c, this.d);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.a.n(false);
    }
}

