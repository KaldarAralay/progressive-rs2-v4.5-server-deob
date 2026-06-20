/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet;

import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.net.packet.PacketSender;

final class RelativePositionUnlockEvent
extends CycleEvent {
    private boolean a = false;
    private /* synthetic */ PacketSender b;
    private final /* synthetic */ boolean c;

    RelativePositionUnlockEvent(PacketSender packetSender, boolean bl) {
        this.b = packetSender;
        this.c = bl;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.a) {
            PacketSender.a(this.b).setActionLocked(false);
            cycleEventContainer.stop();
        }
        this.a = true;
    }

    @Override
    public final void onStop() {
        if (this.c) {
            PacketSender.a((PacketSender)this.b).aw = false;
        }
    }
}

