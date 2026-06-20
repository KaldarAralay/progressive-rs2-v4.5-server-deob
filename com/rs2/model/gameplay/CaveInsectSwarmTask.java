/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay;

import com.rs2.model.World;
import com.rs2.model.combat.hit.HitType;
import com.rs2.model.gameplay.CaveInsectDamageTask;
import com.rs2.model.gameplay.CaveLightManager;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class CaveInsectSwarmTask
extends TickTask {
    private final /* synthetic */ Player a;

    CaveInsectSwarmTask(int n, Player player) {
        this.a = player;
        super(15);
    }

    @Override
    public final void execute() {
        if (!this.a.bW()) {
            this.stop();
            return;
        }
        if (CaveLightManager.a(this.a)) {
            if (this.a.eI() == 0 && this.a.eH == 0) {
                Player player = this.a;
                player.packetSender.sendGameMessage("You hear tiny insects skittering over the ground...");
                this.a.eH = 1;
                return;
            }
            if (this.a.eI() == 0 && this.a.eH == 1) {
                Object object = this.a;
                ((Player)object).packetSender.sendGameMessage("Tiny biting insects swarm all over you!");
                this.a.applyDirectHit(1, HitType.NORMAL);
                this.a.getRecentCombatTimer().setTargetDelay(null, 17);
                this.a.eH = 2;
                object = new CaveInsectDamageTask(this, 3, this.a);
                World.getTaskScheduler().schedule((TickTask)object);
                return;
            }
        } else {
            this.a.er = -1;
            this.stop();
        }
    }
}

