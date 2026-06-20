/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.consumable;

import com.rs2.model.item.consumable.FoodHandler;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class FoodHealMessageEvent
extends CycleEvent {
    private /* synthetic */ FoodHandler a;

    FoodHealMessageEvent(FoodHandler foodHandler) {
        this.a = foodHandler;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (this.a.a.getSkillManager().getBaseLevel(3) < this.a.a.getSkillManager().getBaseLevel(3)) {
            Player player = this.a.a;
            player.packetSender.sendGameMessage("It heals some health.");
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

