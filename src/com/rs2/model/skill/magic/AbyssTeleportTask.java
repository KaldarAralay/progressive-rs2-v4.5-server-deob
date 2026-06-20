/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.magic;

import com.rs2.model.Position;
import com.rs2.model.skill.magic.TeleportManager;
import com.rs2.model.skill.runecrafting.RunecraftingHandler;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class AbyssTeleportTask
extends CycleEvent {
    private int a = 3;
    private /* synthetic */ TeleportManager b;
    private final /* synthetic */ int c;
    private final /* synthetic */ int d;
    private final /* synthetic */ int e;

    AbyssTeleportTask(TeleportManager teleportManager, int n, int n2, int n3) {
        this.b = teleportManager;
        this.c = n;
        this.d = n2;
        this.e = n3;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!TeleportManager.a(this.b).isDead()) {
            if (this.a == 3) {
                TeleportManager.a(this.b).moveTo(new Position(this.c, this.d, this.e));
                if (TeleportManager.a(this.b).getInventoryManager().containsItem(5519)) {
                    RunecraftingHandler.recordScryingOrbTeleport(TeleportManager.a(this.b));
                }
                cycleEventContainer.stop();
            }
        } else {
            this.a = 0;
        }
        if (this.a <= 0) {
            cycleEventContainer.stop();
        }
        --this.a;
    }

    @Override
    public final void onStop() {
        TeleportManager.a(this.b).n(false);
        TeleportManager.a(this.b).getAttributes().put("canTakeDamage", Boolean.TRUE);
    }
}

