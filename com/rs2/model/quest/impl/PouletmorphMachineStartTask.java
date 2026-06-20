/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.World;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ErnestTheChickenQuest;
import com.rs2.model.quest.impl.PouletmorphMachineTransformTask;
import com.rs2.model.task.TickTask;

final class PouletmorphMachineStartTask
extends TickTask {
    private TickTask b;
    final /* synthetic */ ErnestTheChickenQuest a;
    private final /* synthetic */ Player c;

    PouletmorphMachineStartTask(ErnestTheChickenQuest ernestTheChickenQuest, int n, Player player) {
        this.a = ernestTheChickenQuest;
        this.c = player;
        super(n);
        this.b = new PouletmorphMachineTransformTask(this, 1, player);
    }

    @Override
    public final void execute() {
        Player player = this.c;
        player.packetSender.sendGameMessage("Oddenstein starts up the machine.");
        World.getTaskScheduler().schedule(this.b);
        this.stop();
    }
}

