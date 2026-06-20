/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.combat;

import com.rs2.model.Entity;
import com.rs2.model.npc.Npc;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class DeathAnimationTask
extends TickTask {
    private final /* synthetic */ int a;
    private final /* synthetic */ Entity b;
    private final /* synthetic */ Entity c;

    DeathAnimationTask(int n, int n2, Entity entity, Entity entity2) {
        this.a = n2;
        this.b = entity;
        this.c = entity2;
        super(2);
    }

    @Override
    public final void execute() {
        Player player;
        int n = this.a;
        if (this.b.isPlayer()) {
            player = (Player)this.b;
            player.x(true);
            this.b.getUpdateState().setFaceEntity(-1);
            player.setAppearanceUpdateRequired(true);
            if (player.ak > 0) {
                n = new Npc(player.ak).getDefinition().getDeathAnimationId();
            }
        }
        this.b.getUpdateState().setAnimation(n);
        if (this.b != null && this.c != null && this.b.isNpc() && this.c.isPlayer()) {
            player = (Player)this.c;
            Npc npc = (Npc)this.b;
            this.b.getUpdateState().setFaceEntity(-1);
            player.packetSender.sendSoundEffect(npc.getDeathSoundId(), 1, 20);
        }
        this.stop();
    }
}

