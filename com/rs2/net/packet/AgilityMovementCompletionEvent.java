/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.net.packet.PacketSender;

final class AgilityMovementCompletionEvent
extends CycleEvent {
    private boolean a = false;
    private /* synthetic */ PacketSender b;
    private final /* synthetic */ String c;
    private final /* synthetic */ double d;
    private final /* synthetic */ boolean e;
    private final /* synthetic */ boolean f;

    AgilityMovementCompletionEvent(PacketSender packetSender, String string, double d, boolean bl, boolean bl2) {
        this.b = packetSender;
        this.c = string;
        this.d = d;
        this.e = bl;
        this.f = bl2;
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
        PacketSender.a(this.b).setWalkAnimationOverride(-1);
        PacketSender.a(this.b).setAppearanceUpdateRequired(true);
        Player player = PacketSender.a(this.b);
        player.packetSender.sendGameMessage(this.c);
        PacketSender.a(this.b).getSkillManager().addExperience(16, this.d);
        if (this.e) {
            PacketSender.a(this.b).getMovementQueue().setRunning(true);
        }
        if (this.f) {
            PacketSender.a((PacketSender)this.b).aw = false;
        }
        if (PacketSender.a((PacketSender)this.b).botEnabled) {
            PacketSender.a((PacketSender)this.b).dm = false;
        }
    }
}

