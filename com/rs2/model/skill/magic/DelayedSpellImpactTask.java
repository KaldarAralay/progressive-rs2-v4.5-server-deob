/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.skill.magic;

import com.rs2.model.Entity;
import com.rs2.model.Position;
import com.rs2.model.combat.hit.HitDefinition;
import com.rs2.model.player.Player;
import com.rs2.model.skill.magic.MagicSpellAction;
import com.rs2.model.task.TickTask;

final class DelayedSpellImpactTask
extends TickTask {
    private /* synthetic */ MagicSpellAction a;
    private final /* synthetic */ HitDefinition b;
    private final /* synthetic */ Entity c;
    private final /* synthetic */ Position d;

    DelayedSpellImpactTask(MagicSpellAction magicSpellAction, int n, HitDefinition hitDefinition, Entity entity, Position position) {
        this.a = magicSpellAction;
        this.b = hitDefinition;
        this.c = entity;
        this.d = position;
        super(n);
    }

    @Override
    public final void execute() {
        MagicSpellAction.finishDelayedImpact(this.a, this, this.b);
        if (this.b.getGraphic() != null) {
            if (this.c != null) {
                this.c.getUpdateState().setGraphic(this.b.getGraphic());
                return;
            }
            Player player = MagicSpellAction.getPlayer(this.a);
            player.packetSender.sendStillGraphic(this.b.getGraphic().getId(), this.d, this.b.getGraphic().getPackedDelay());
        }
    }
}

