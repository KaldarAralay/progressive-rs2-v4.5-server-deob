/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.consumable;

import com.rs2.model.item.consumable.FoodHandler;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

public final class FoodHealMessageEvent
extends CycleEvent {
    private /* synthetic */ FoodHandler foodHandler;

    public FoodHealMessageEvent(FoodHandler foodHandler) {
        this.foodHandler = foodHandler;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.foodHandler.player.getSkillManager().getBaseLevel(3) < this.foodHandler.player.getSkillManager().getBaseLevel(3)) {
            Player player = this.foodHandler.player;
            player.packetSender.sendGameMessage("It heals some health.");
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

