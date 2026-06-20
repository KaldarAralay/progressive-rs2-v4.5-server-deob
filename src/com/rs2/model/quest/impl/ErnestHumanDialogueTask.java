/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.dialogue.DialogueManager;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ErnestTheChickenQuest;
import com.rs2.model.task.TickTask;

final class ErnestHumanDialogueTask
extends TickTask {
    private final /* synthetic */ Player a;

    ErnestHumanDialogueTask(ErnestTheChickenQuest ernestTheChickenQuest, int n, Player player) {
        this.a = player;
        super(n);
    }

    @Override
    public final void execute() {
        Npc npc = Npc.findByDefinitionId(288);
        npc.transformToNpcId(287, 100);
        DialogueManager.continueDialogue(this.a, 287, 100, 0);
        this.stop();
    }
}

