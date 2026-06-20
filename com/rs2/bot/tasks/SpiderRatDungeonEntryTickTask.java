/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.tasks;

import com.rs2.bot.tasks.EdgevilleDungeonSpiderRatCombatBotTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import java.util.ArrayList;

final class SpiderRatDungeonEntryTickTask
extends TickTask {
    private final /* synthetic */ Player a;

    SpiderRatDungeonEntryTickTask(EdgevilleDungeonSpiderRatCombatBotTask edgevilleDungeonSpiderRatCombatBotTask, int n, Player player) {
        this.a = player;
        super(3);
    }

    @Override
    public final void execute() {
        if (this.a.isDead() || !this.a.isRegistered()) {
            this.stop();
            return;
        }
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        arrayList.add(1570);
        this.a.interactWithBotObjectTargetsNoRetry(arrayList, false);
        this.stop();
    }
}

