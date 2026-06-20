/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class MonkeyAmuletSmithingTask
extends TickTask {
    private final /* synthetic */ Player a;

    MonkeyAmuletSmithingTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = player;
        super(4);
    }

    @Override
    public final void execute() {
        this.a.setActionLocked(false);
        this.a.getInventoryManager().removeItem(new ItemStack(4007, 1));
        this.a.getInventoryManager().addOrDropItem(new ItemStack(4022, 1));
        Player player = this.a;
        player.packetSender.sendGameMessage("You smith the enchanted gold into an amulet.");
        this.stop();
    }
}

