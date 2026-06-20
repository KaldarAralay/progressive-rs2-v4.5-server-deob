/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ApothecaryCadavaPotionMixTask;
import com.rs2.model.task.TickTask;

final class ApothecaryCadavaPotionReadyTask
extends TickTask {
    private final /* synthetic */ Player a;

    ApothecaryCadavaPotionReadyTask(ApothecaryCadavaPotionMixTask apothecaryCadavaPotionMixTask, int n, Player player) {
        this.a = player;
        super(2);
    }

    @Override
    public final void execute() {
        this.a.getDialogueManager().showNpcOneLineDialogue("Phew! Here is what you need.", 591);
        this.stop();
    }
}

