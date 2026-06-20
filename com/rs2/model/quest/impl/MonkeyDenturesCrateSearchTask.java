/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class MonkeyDenturesCrateSearchTask
extends TickTask {
    private final /* synthetic */ Player a;

    MonkeyDenturesCrateSearchTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = player;
        super(2);
    }

    @Override
    public final void execute() {
        this.a.setActionLocked(false);
        this.a.getDialogueManager().showThreeLineItemMessage("", "This crate is full of ... magical monkey talking", "dentures!", new ItemStack(4006, 1));
        this.stop();
    }
}

