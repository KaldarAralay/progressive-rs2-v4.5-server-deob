/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.Position;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

final class ShipyardCrateTunnelDescentTask
extends TickTask {
    private final /* synthetic */ Player a;

    ShipyardCrateTunnelDescentTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = player;
        super(5);
    }

    @Override
    public final void execute() {
        this.a.moveTo(new Position(2802, 9169, 0));
        Player player = this.a;
        player.packetSender.closeInterfaces();
        this.a.applyDirectHit(1 + GameUtil.g(9), HitType.NORMAL);
        this.a.n(false);
        this.stop();
    }
}

