/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.net.packet.handler.ItemActionPacketHandler;

final class DigSearchTask
extends CycleEvent {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;
    private final /* synthetic */ boolean c;

    DigSearchTask(ItemActionPacketHandler itemActionPacketHandler, Player player, int n, boolean bl) {
        this.a = player;
        this.b = n;
        this.c = bl;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.a.isCurrentActionSequence(this.b)) {
            cycleEventContainer.stop();
            return;
        }
        if (this.c) {
            Player player = this.a;
            player.packetSender.sendGameMessage("but do not find anything.");
            cycleEventContainer.stop();
        }
    }

    @Override
    public final void onStop() {
        this.a.aN();
    }
}

