/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.model.player.Player;
import com.rs2.model.task.CycleEvent;
import com.rs2.model.task.CycleEventContainer;
import com.rs2.net.packet.handler.ItemActionPacketHandler;

final class TinderboxOnGroundItemTask
extends CycleEvent {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;

    TinderboxOnGroundItemTask(ItemActionPacketHandler itemActionPacketHandler, Player player, int n) {
        this.a = player;
        this.b = n;
    }

    @Override
    public final void execute(CycleEventContainer cycleEventContainer) {
        if (!this.a.isCurrentActionSequence(this.b)) {
            cycleEventContainer.stop();
            return;
        }
        if (this.a.getPosition().getX() == this.a.getInteractionTargetX() && this.a.getPosition().getY() == this.a.getInteractionTargetY()) {
            this.a.getFiremakingHandler().startFiremaking(this.a.getInteractionTargetId(), 0, true, this.a.getInteractionTargetX(), this.a.getInteractionTargetY(), this.a.getPosition().getPlane());
            cycleEventContainer.stop();
        }
    }

    @Override
    public final void onStop() {
    }
}

