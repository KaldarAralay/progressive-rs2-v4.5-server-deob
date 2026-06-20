/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GrandTreeQuest;
import com.rs2.model.task.TickTask;

final class GloughGuardArrestStartTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ Npc b;

    GloughGuardArrestStartTask(GrandTreeQuest grandTreeQuest, int n, Player player, Npc npc) {
        this.a = player;
        this.b = npc;
        super(3);
    }

    @Override
    public final void execute() {
        DialogueManager.continueDialogue(this.a, 2781, 100, 0);
        this.b.setScriptedMovementEnabled(true);
        this.stop();
    }
}

