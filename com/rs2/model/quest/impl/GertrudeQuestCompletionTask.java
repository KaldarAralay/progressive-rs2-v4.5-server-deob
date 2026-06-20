/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GertrudeRewardFoodTask;
import com.rs2.model.task.TickTask;

final class GertrudeQuestCompletionTask
extends TickTask {
    private /* synthetic */ GertrudeRewardFoodTask a;
    private final /* synthetic */ Player b;

    GertrudeQuestCompletionTask(GertrudeRewardFoodTask gertrudeRewardFoodTask, int n, Player player) {
        this.a = gertrudeRewardFoodTask;
        this.b = player;
        super(4);
    }

    @Override
    public final void execute() {
        this.b.setActionLocked(false);
        GertrudeRewardFoodTask gertrudeRewardFoodTask = this.a;
        gertrudeRewardFoodTask.a.awardCompletionRewards(this.b);
        this.stop();
    }
}

