/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.PiratesTreasureQuest;
import com.rs2.model.task.TickTask;

final class BananaCratePromptTask
extends TickTask {
    private final /* synthetic */ Player a;

    BananaCratePromptTask(PiratesTreasureQuest piratesTreasureQuest, int n, Player player) {
        this.a = player;
        super(2);
    }

    @Override
    public final void execute() {
        this.a.getDialogueManager().showTwoOptionsWithTitle("Do you want to take a banana?", "Yes.", "No.");
        this.stop();
    }
}

