/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.item.action;

import com.rs2.model.item.ItemStack;
import com.rs2.model.item.action.BrokenPlateDropEvent;
import com.rs2.model.item.action.SpinningPlateHandler;
import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.model.task.CycleEventHandler;
import com.rs2.util.GameUtil;

final class SpinningPlateResultEvent
extends CycleEvent {
    private final /* synthetic */ Player a;

    SpinningPlateResultEvent(Player player) {
        this.a = player;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        this.a.n(false);
        if (GameUtil.h(3) == 0) {
            this.a.getUpdateState().setAnimation(SpinningPlateHandler.a(), 0);
            this.a.getInventoryManager().removeItem(new ItemStack(SpinningPlateHandler.b(), 1));
            CycleEventHandler.getInstance().schedule(this.a, new BrokenPlateDropEvent(this, this.a), 1);
        } else {
            this.a.getUpdateState().setAnimation(SpinningPlateHandler.d(), 0);
        }
        cycleEventContainer.stop();
    }

    @Override
    public final void onStop() {
    }
}

