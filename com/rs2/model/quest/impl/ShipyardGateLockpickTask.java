/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;

final class ShipyardGateLockpickTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ boolean b;

    ShipyardGateLockpickTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player, boolean bl) {
        this.a = player;
        this.b = bl;
        super(3);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        if (this.b) {
            Player player = this.a;
            player.packetSender.openSingleDoor(4799, 2771, 2795, this.a.getPosition().getPlane());
            player = this.a;
            player.packetSender.queueRelativeMovementStep(0, 1, true);
            player = this.a;
            player.packetSender.sendGameMessage("You manage to pick the lock.");
        } else {
            Player player = this.a;
            player.packetSender.sendGameMessage("You fail to pick the lock.");
        }
        this.stop();
    }
}

