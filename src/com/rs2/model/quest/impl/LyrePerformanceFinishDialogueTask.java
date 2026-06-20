/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.LyrePerformanceStartTask;
import com.rs2.model.task.TickTask;

final class LyrePerformanceFinishDialogueTask
extends TickTask {
    private final /* synthetic */ Player a;

    LyrePerformanceFinishDialogueTask(LyrePerformanceStartTask lyrePerformanceStartTask, int n, Player player) {
        this.a = player;
        super(4);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        DialogueManager.continueDialogue(this.a, 1269, 100, 0);
        this.stop();
    }
}

