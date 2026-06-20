/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.combat.hit.HitType;
import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

final class ApeAtollDungeonHazardDamageTask
extends TickTask {
    private /* synthetic */ MonkeyMadnessQuest a;
    private final /* synthetic */ Player b;

    ApeAtollDungeonHazardDamageTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player) {
        this.a = monkeyMadnessQuest;
        this.b = player;
        super(25);
    }

    @Override
    public final void execute() {
        if (!this.b.bW()) {
            this.stop();
            return;
        }
        if (this.a.a.containsExclusive(this.b.getPosition()) && !this.a.b.containsExclusive(this.b.getPosition())) {
            this.b.getUpdateState().setGraphic(60, 0);
            this.b.applyDirectHit(GameUtil.g(2), HitType.NORMAL);
            return;
        }
        this.b.er = -1;
        this.stop();
    }
}

