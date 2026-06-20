/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.net.packet.handler;

import com.rs2.model.EntityTargetMovement;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.net.packet.handler.PlayerInteractionPacketHandler;

final class FollowPlayerTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ Player b;
    private final /* synthetic */ int c;

    FollowPlayerTask(PlayerInteractionPacketHandler playerInteractionPacketHandler, int n, Player player, Player player2, int n2) {
        this.a = player;
        this.b = player2;
        this.c = n2;
        super(1);
    }

    @Override
    public final void execute() {
        if (this.a == null || this.a.isDead() || !this.b.isCurrentActionSequence(this.c)) {
            EntityTargetMovement.clearMovementTarget(this.b);
            this.b.setInteractionTarget(null);
            this.b.getMovementQueue().clear();
            this.stop();
            return;
        }
        if (this.b.isWithinReach(this.a, 1) && !this.b.isOverlapping(this.a)) {
            this.b.getUpdateState().setAnimation(1833);
            EntityTargetMovement.clearMovementTarget(this.b);
            this.b.getUpdateState().setFacePosition(this.a.getPosition());
            this.b.setInteractionTarget(null);
            this.b.getMovementQueue().clear();
            this.stop();
        }
    }
}

