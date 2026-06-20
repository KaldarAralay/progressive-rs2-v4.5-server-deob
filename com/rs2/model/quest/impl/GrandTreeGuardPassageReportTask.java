/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GrandTreeQuest;
import com.rs2.model.task.TickTask;

final class GrandTreeGuardPassageReportTask
extends TickTask {
    private final /* synthetic */ Player a;

    GrandTreeGuardPassageReportTask(GrandTreeQuest grandTreeQuest, int n, Player player) {
        this.a = player;
        super(5);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        Player player = this.a;
        player.packetSender.closeInterfaces();
        DialogueManager.continueDialogue(this.a, 2781, 100, 0);
        this.stop();
    }
}

