/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.GertrudesCatQuest;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

final class KittenCrateSearchTask
extends TickTask {
    private final /* synthetic */ Player a;

    KittenCrateSearchTask(GertrudesCatQuest gertrudesCatQuest, int n, Player player) {
        this.a = player;
        super(3);
    }

    @Override
    public final void execute() {
        if (GameUtil.h(5) == 0) {
            this.a.getDialogueManager().showOneLineStatement("You find a kitten! You carefully place it in your backpack.");
            this.a.getInventoryManager().b(new ItemStack(1554, 1));
        } else {
            Player player = this.a;
            player.packetSender.sendGameMessage("You find nothing.");
        }
        this.a.n(false);
        this.stop();
    }
}

