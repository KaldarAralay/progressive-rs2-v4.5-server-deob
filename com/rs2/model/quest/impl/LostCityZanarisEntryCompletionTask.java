/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.LostCityQuest;
import com.rs2.model.task.TickTask;

final class LostCityZanarisEntryCompletionTask
extends TickTask {
    private /* synthetic */ LostCityQuest a;
    private final /* synthetic */ Player b;

    LostCityZanarisEntryCompletionTask(LostCityQuest lostCityQuest, int n, Player player) {
        this.a = lostCityQuest;
        this.b = player;
        super(4);
    }

    @Override
    public final void execute() {
        this.a.awardCompletionRewards(this.b);
        this.stop();
    }
}

