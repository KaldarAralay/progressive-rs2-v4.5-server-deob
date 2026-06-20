/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat;

import com.rs2.model.Entity;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class NpcRespawnCombatEvent
extends CycleEvent {
    private final /* synthetic */ Npc a;
    private final /* synthetic */ Entity b;

    NpcRespawnCombatEvent(Npc npc, Entity entity) {
        this.a = npc;
        this.b = entity;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        CombatManager.finishDeath(this.a, this.b, false);
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

