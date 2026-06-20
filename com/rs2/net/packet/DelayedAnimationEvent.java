/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet;

import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.net.packet.PacketSender;

final class DelayedAnimationEvent
extends CycleEvent {
    private /* synthetic */ PacketSender a;
    private final /* synthetic */ int b;

    DelayedAnimationEvent(PacketSender packetSender, int n) {
        this.a = packetSender;
        this.b = n;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        PacketSender.a(this.a).getUpdateState().setAnimation(this.b);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

