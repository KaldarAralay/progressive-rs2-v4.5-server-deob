/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GrandTreeQuest;
import com.rs2.model.task.TickTask;

final class GloughChestScrollFindTask
extends TickTask {
    private final /* synthetic */ Player a;

    GloughChestScrollFindTask(GrandTreeQuest grandTreeQuest, int n, Player player) {
        this.a = player;
        super(2);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        this.a.getDialogueManager().showItemMessage("You have found a scroll!", new ItemStack(794, 1));
        this.a.getInventoryManager().b(new ItemStack(794, 1));
        this.stop();
    }
}

