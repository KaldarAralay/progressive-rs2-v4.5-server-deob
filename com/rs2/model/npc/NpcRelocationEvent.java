/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.npc;

import com.rs2.model.GameplayHelper;
import com.rs2.model.Position;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class NpcRelocationEvent
extends CycleEvent {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;
    private final /* synthetic */ int c;
    private final /* synthetic */ int d;
    private final /* synthetic */ boolean e;
    private final /* synthetic */ Npc f;

    NpcRelocationEvent(Npc npc, Player player, int n, int n2, int n3, boolean bl, Npc npc2) {
        this.a = player;
        this.b = n;
        this.c = n2;
        this.d = n3;
        this.e = bl;
        this.f = npc2;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.a.moveTo(new Position(this.b, this.c, this.d));
        Player player = this.a;
        player.packetSender.closeInterfaces();
        this.a.getUpdateState().setAnimation(65535, 0);
        if (this.e) {
            Player player2 = this.a;
            player = player2;
            player = this.a;
            player2.packetSender.sendStillGraphic(86, player.H.getPosition(), 0x640000);
            GameplayHelper.unregisterTemporaryNpc(this.f);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.a.setActionLocked(false);
    }
}

