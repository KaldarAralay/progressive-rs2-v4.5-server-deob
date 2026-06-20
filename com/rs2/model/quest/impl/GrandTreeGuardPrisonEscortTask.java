/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GrandTreeQuest;
import com.rs2.model.task.TickTask;

final class GrandTreeGuardPrisonEscortTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;

    GrandTreeGuardPrisonEscortTask(GrandTreeQuest grandTreeQuest, int n, Player player, int n2) {
        this.a = player;
        this.b = n2;
        super(3);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        this.a.moveTo(new Position(2464, 3496, 3));
        this.a.setQuestState(this.b, 9);
        DialogueManager.continueDialogue(this.a, 673, 1, 0);
        this.stop();
    }
}

