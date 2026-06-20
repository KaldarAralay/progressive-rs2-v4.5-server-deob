/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.PiratesTreasureQuest;
import com.rs2.model.task.TickTask;

final class HectorsChestMessageTask
extends TickTask {
    private final /* synthetic */ Player a;

    HectorsChestMessageTask(PiratesTreasureQuest piratesTreasureQuest, int n, Player player) {
        this.a = player;
        super(4);
    }

    @Override
    public final void execute() {
        this.a.getInventoryManager().removeItem(new ItemStack(432, 1));
        this.a.getInventoryManager().addOrDropItem(new ItemStack(433, 1));
        Player player = this.a;
        player.packetSender.sendGameMessage("You take the message from the chest.");
        this.stop();
    }
}

