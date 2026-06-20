/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.item.ItemStack;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.WitchsHouseQuest;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;

final class WitchsHouseGardenEjectEvent
extends CycleEvent {
    private int a = 6;
    private final /* synthetic */ Player b;

    WitchsHouseGardenEjectEvent(WitchsHouseQuest witchsHouseQuest, Player player) {
        this.b = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        --this.a;
        if (!this.b.isDead()) {
            if (this.a == 3) {
                if (this.b.getInventoryManager().containsItem(2407)) {
                    this.b.getInventoryManager().removeItem(new ItemStack(2407, 1));
                }
                this.b.moveTo(new Position(2917, 3456, 0));
            }
        } else {
            this.a = 0;
        }
        if (this.a <= 0) {
            cycleEventContainer.stop();
        }
    }

    @Override
    public final void onStop() {
        this.b.n(false);
        this.b.getAttributes().put("canTakeDamage", Boolean.TRUE);
    }
}

