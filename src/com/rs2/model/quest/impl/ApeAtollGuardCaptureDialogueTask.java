/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class ApeAtollGuardCaptureDialogueTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;

    ApeAtollGuardCaptureDialogueTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player, int n2) {
        this.a = player;
        this.b = n2;
        super(10);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        this.a.moveTo(new Position(2772, 2794, 0));
        this.a.aN();
        this.a.ai = false;
        this.a.pendingGameMode = 0;
        Player player = this.a;
        player.packetSender.showWalkableInterface(-1);
        if (this.b == 11) {
            DialogueManager.continueDialogue(this.a, 1413, 100, 0);
        }
        this.stop();
    }
}

