/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet;

import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.net.packet.PacketSender;

final class DelayedUnlockEvent
extends CycleEvent {
    private /* synthetic */ PacketSender a;

    DelayedUnlockEvent(PacketSender packetSender) {
        this.a = packetSender;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        PacketSender.a(this.a).setActionLocked(false);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

