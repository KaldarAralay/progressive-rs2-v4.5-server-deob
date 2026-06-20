/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.combat.CombatManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.VampireCoffinRiseTask;
import com.rs2.model.task.TickTask;

final class VampireAttackStartTask
extends TickTask {
    private final /* synthetic */ Npc a;
    private final /* synthetic */ Player b;

    VampireAttackStartTask(VampireCoffinRiseTask vampireCoffinRiseTask, int n, Npc npc, Player player) {
        this.a = npc;
        this.b = player;
        super(5);
    }

    @Override
    public final void execute() {
        this.a.getUpdateState().setAnimation(808);
        CombatManager.startCombat(this.a, this.b);
        this.a.moveTo(new Position(3077, 9774, 0));
        if (this.b.getInventoryManager().containsItem(1550)) {
            Player player = this.b;
            player.packetSender.sendGameMessage("The vampire seems to weaken.");
        }
        this.stop();
    }
}

