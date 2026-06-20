/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.tasks;

import com.rs2.bot.tasks.VarrockSewerGiantRatCombatBotTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import java.util.ArrayList;

final class VarrockSewerGiantRatDungeonEntryTickTask
extends TickTask {
    private final /* synthetic */ Player a;

    VarrockSewerGiantRatDungeonEntryTickTask(VarrockSewerGiantRatCombatBotTask varrockSewerGiantRatCombatBotTask, int n, Player player) {
        this.a = player;
        super(3);
    }

    @Override
    public final void execute() {
        if (this.a.isDead() || !this.a.bW()) {
            this.stop();
            return;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(882);
        this.a.interactWithBotObjectTargetsNoRetry(arrayList, false);
        this.stop();
    }
}

