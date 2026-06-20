/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.magic;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.TeleportManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class ScriptedTeleportTask
extends CycleEvent {
    private int a;
    private /* synthetic */ TeleportManager b;
    private final /* synthetic */ int c;
    private final /* synthetic */ int d;
    private final /* synthetic */ int e;
    private final /* synthetic */ int f;
    private final /* synthetic */ int g;
    private final /* synthetic */ int h;
    private final /* synthetic */ String i;

    ScriptedTeleportTask(TeleportManager teleportManager, int n, int n2, int n3, int n4, int n5, int n6, int n7, String string) {
        this.b = teleportManager;
        this.c = n2;
        this.d = n3;
        this.e = n4;
        this.f = n5;
        this.g = n6;
        this.h = n7;
        this.i = string;
        this.a = n;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        --this.a;
        if (!TeleportManager.a(this.b).isDead()) {
            if (this.a == 4) {
                TeleportManager.a(this.b).getUpdateState().setAnimation(this.c);
                TeleportManager.a(this.b).getUpdateState().setGraphicHeight100(this.d);
            }
            if (this.a == 2) {
                TeleportManager.a(this.b).n(false);
                TeleportManager.a(this.b).getUpdateState().setAnimation(this.e);
                TeleportManager.a(this.b).moveTo(new Position(this.f, this.g, this.h));
                if (this.i != null) {
                    Player player = TeleportManager.a(this.b);
                    player.packetSender.sendGameMessage(this.i);
                }
            }
        } else {
            this.a = 0;
        }
        if (this.a <= 0) {
            cycleEventContainer.stop();
        }
    }

    @Override
    public final void onStop() {
        TeleportManager.a(this.b).n(false);
        TeleportManager.a(this.b).getAttributes().put("canTakeDamage", Boolean.TRUE);
    }
}

