/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.magic;

import com.rs2.model.Position;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.TeleportManager;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class StandardTeleportTask
extends CycleEvent {
    private int a = 8;
    private /* synthetic */ TeleportManager b;
    private final /* synthetic */ int c;
    private final /* synthetic */ int d;
    private final /* synthetic */ int e;
    private final /* synthetic */ String f;

    StandardTeleportTask(TeleportManager teleportManager, int n, int n2, int n3, String string) {
        this.b = teleportManager;
        this.c = n;
        this.d = n2;
        this.e = n3;
        this.f = string;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        --this.a;
        if (!TeleportManager.a(this.b).isDead()) {
            if (this.a == 4) {
                TeleportManager.a(this.b).getUpdateState().setAnimation(714);
                TeleportManager.a(this.b).getUpdateState().setGraphicHeight100(301);
            }
            if (this.a == 2) {
                TeleportManager.a(this.b).getUpdateState().setAnimation(715);
                TeleportManager.a(this.b).moveTo(new Position(this.c, this.d, this.e));
                if (this.f != null) {
                    Player player = TeleportManager.a(this.b);
                    player.packetSender.sendGameMessage(this.f);
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

