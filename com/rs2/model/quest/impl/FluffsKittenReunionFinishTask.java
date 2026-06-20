/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.GameplayHelper;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.FluffsKittenReunionStartTask;
import com.rs2.model.task.TickTask;

final class FluffsKittenReunionFinishTask
extends TickTask {
    private final /* synthetic */ Npc a;
    private final /* synthetic */ Player b;
    private final /* synthetic */ Npc c;

    FluffsKittenReunionFinishTask(FluffsKittenReunionStartTask fluffsKittenReunionStartTask, int n, Npc npc, Player player, Npc npc2) {
        this.a = npc;
        this.b = player;
        this.c = npc2;
        super(3);
    }

    @Override
    public final void execute() {
        CombatManager.finishDeath(this.a, this.b, false);
        GameplayHelper.a(this.c);
        this.b.setActionLocked(false);
        this.b.getDialogueManager().showOneLineStatement("Fluffs has run off home with her offspring.");
        this.stop();
    }
}

