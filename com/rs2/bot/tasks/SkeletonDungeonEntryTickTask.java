/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.tasks;

import com.rs2.bot.tasks.EdgevilleDungeonSkeletonCombatBotTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import java.util.ArrayList;

final class SkeletonDungeonEntryTickTask
extends TickTask {
    private final /* synthetic */ Player a;

    SkeletonDungeonEntryTickTask(EdgevilleDungeonSkeletonCombatBotTask edgevilleDungeonSkeletonCombatBotTask, int n, Player player) {
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

