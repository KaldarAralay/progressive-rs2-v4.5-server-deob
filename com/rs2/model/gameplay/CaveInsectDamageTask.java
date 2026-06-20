/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay;

import com.rs2.model.combat.hit.HitType;
import com.rs2.model.gameplay.CaveInsectSwarmTask;
import com.rs2.model.gameplay.CaveLightManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class CaveInsectDamageTask
extends TickTask {
    private final /* synthetic */ Player a;

    CaveInsectDamageTask(CaveInsectSwarmTask caveInsectSwarmTask, int n, Player player) {
        this.a = player;
        super(3);
    }

    @Override
    public final void execute() {
        if (!this.a.bW()) {
            this.stop();
            return;
        }
        if (CaveLightManager.a(this.a)) {
            if (this.a.eI() == 0) {
                this.a.applyDirectHit(1, HitType.NORMAL);
                this.a.getRecentCombatTimer().setTargetDelay(null, 17);
                return;
            }
            this.a.eH = 0;
            this.stop();
            return;
        }
        this.a.eH = 0;
        this.stop();
    }
}

