/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class NarnodeOrdersWritingTask
extends TickTask {
    private final /* synthetic */ Player a;

    NarnodeOrdersWritingTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = player;
        super(4);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        DialogueManager.continueDialogue(this.a, 670, 13, 0);
        this.stop();
    }
}

