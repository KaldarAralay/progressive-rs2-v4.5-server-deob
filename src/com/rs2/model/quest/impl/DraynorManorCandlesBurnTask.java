/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.VampireSlayerQuest;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

final class DraynorManorCandlesBurnTask
extends TickTask {
    private /* synthetic */ VampireSlayerQuest a;
    private final /* synthetic */ Player b;

    DraynorManorCandlesBurnTask(VampireSlayerQuest vampireSlayerQuest, int n, Player player) {
        this.a = vampireSlayerQuest;
        this.b = player;
        super(5);
    }

    @Override
    public final void execute() {
        if (!this.b.bW()) {
            this.stop();
            return;
        }
        if (this.a.b.containsExclusive(this.b.getPosition())) {
            Player player = this.b;
            player.packetSender.sendGameMessage("The candles burn your feet!");
            this.b.getUpdateState().setForcedText(this.a.a[GameUtil.g(this.a.a.length - 1)]);
            return;
        }
        this.b.eq = -1;
        this.stop();
    }
}

