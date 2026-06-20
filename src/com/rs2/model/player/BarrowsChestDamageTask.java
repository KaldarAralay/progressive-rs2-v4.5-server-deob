/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.player;

import com.rs2.model.combat.hit.HitType;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

final class BarrowsChestDamageTask
extends TickTask {
    private /* synthetic */ Player a;

    BarrowsChestDamageTask(Player player, int n) {
        this.a = player;
        super(50);
    }

    @Override
    public final void execute() {
        if (!this.a.bW()) {
            this.stop();
            return;
        }
        if (!this.a.bU) {
            this.stop();
            return;
        }
        this.a.getUpdateState().setGraphic(60, 0);
        this.a.applyDirectHit(GameUtil.g(5), HitType.NORMAL);
    }
}

