/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.interaction;

import com.rs2.model.combat.hit.HitType;
import com.rs2.model.interaction.FirstObjectActionTask;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.util.GameUtil;

final class SearchHayTask
extends CycleEvent {
    private final /* synthetic */ Player a;

    SearchHayTask(FirstObjectActionTask firstObjectActionTask, Player player) {
        this.a = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (GameUtil.randomInclusive(99) == 0) {
            if (GameUtil.randomInclusive(1) == 0) {
                this.a.getDialogueManager().showPlayerTwoLineDialogue("Wow! A needle!", "Now what are the chances of finding that?", 588);
                this.a.getDialogueManager().finishDialogue();
                this.a.getInventoryManager().addItem(new ItemStack(1733));
            } else {
                this.a.applyDirectHit(1, HitType.NORMAL);
                this.a.getDialogueManager().showPlayerOneLineDialogue("Ow! There's something sharp in there!", 599);
                this.a.getDialogueManager().finishDialogue();
            }
        } else {
            Player player = this.a;
            player.packetSender.sendGameMessage("You find nothing of interest.");
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
        this.a.setActionLocked(false);
    }
}

