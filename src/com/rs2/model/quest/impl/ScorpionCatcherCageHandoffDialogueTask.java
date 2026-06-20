/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ScorpionCatcherQuest;
import com.rs2.model.task.TickTask;

final class ScorpionCatcherCageHandoffDialogueTask
extends TickTask {
    private final /* synthetic */ Player a;

    ScorpionCatcherCageHandoffDialogueTask(ScorpionCatcherQuest scorpionCatcherQuest, int n, Player player) {
        this.a = player;
        super(4);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        DialogueManager.continueDialogue(this.a, 389, 10, 0);
        this.stop();
    }
}

