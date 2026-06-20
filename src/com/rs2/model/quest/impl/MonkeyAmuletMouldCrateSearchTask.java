/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class MonkeyAmuletMouldCrateSearchTask
extends TickTask {
    private final /* synthetic */ Player a;

    MonkeyAmuletMouldCrateSearchTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = player;
        super(2);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        this.a.getDialogueManager().showItemMessage("This crate is full of ... monkey amulet moulds!", new ItemStack(4020, 1));
        this.stop();
    }
}

