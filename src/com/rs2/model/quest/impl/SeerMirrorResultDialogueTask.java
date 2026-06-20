/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.SeerMirrorGazeTask;
import com.rs2.model.task.TickTask;

final class SeerMirrorResultDialogueTask
extends TickTask {
    private final /* synthetic */ Player a;

    SeerMirrorResultDialogueTask(SeerMirrorGazeTask seerMirrorGazeTask, int n, Player player) {
        this.a = player;
        super(4);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        DialogueManager.continueDialogue(this.a, 388, 8, 0);
        this.stop();
    }
}

