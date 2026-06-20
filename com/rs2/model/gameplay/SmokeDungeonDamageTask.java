/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay;

import com.rs2.model.combat.hit.HitType;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class SmokeDungeonDamageTask
extends TickTask {
    private final /* synthetic */ Player a;

    SmokeDungeonDamageTask(int n, Player player) {
        this.a = player;
        super(20);
    }

    @Override
    public final void execute() {
        if (!this.a.isRegistered()) {
            this.stop();
            return;
        }
        if (this.a.isInSmokeDungeon()) {
            if (this.a.getEquipmentManager().getItemIdAtSlot(0) != 4164 && this.a.getCurrentHitpoints() > 1) {
                this.a.applyDirectHit(this.a.getCurrentHitpoints() - 20 <= 0 ? this.a.getCurrentHitpoints() - 1 : 20, HitType.NORMAL);
                Player player = this.a;
                player.packetSender.sendGameMessage("You should wear a facemask to protect yourself from the smoke.");
                return;
            }
        } else {
            this.a.eq = -1;
            this.stop();
        }
    }
}

