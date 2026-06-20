/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ErnestTheChickenQuest;
import com.rs2.model.task.TickTask;

final class CompostHeapKeyFindTask
extends TickTask {
    private final /* synthetic */ Player a;

    CompostHeapKeyFindTask(ErnestTheChickenQuest ernestTheChickenQuest, int n, Player player) {
        this.a = player;
        super(2);
    }

    @Override
    public final void execute() {
        this.a.getInventoryManager().b(new ItemStack(275, 1));
        Player player = this.a;
        player.packetSender.sendGameMessage("...and find a small key.");
        this.stop();
    }
}

