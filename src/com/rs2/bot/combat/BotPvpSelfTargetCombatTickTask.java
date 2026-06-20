/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.combat;

import com.rs2.bot.combat.BotCombatHelper;
import com.rs2.bot.combat.BotPvpCombatHandler;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class BotPvpSelfTargetCombatTickTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ Player b;

    BotPvpSelfTargetCombatTickTask(int n, Player player, Player player2) {
        this.a = player;
        this.b = player2;
        super(1);
    }

    @Override
    public final void execute() {
        if (this.a.isDead() || !this.a.bW()) {
            BotCombatHelper.k(this.b);
            this.stop();
        }
        if (this.b.isDead() || !this.b.bW()) {
            BotCombatHelper.k(this.b);
            this.stop();
        }
        if (this.b.getCombatTarget() == null) {
            BotCombatHelper.k(this.b);
            this.stop();
        }
        BotPvpCombatHandler.processBotPvpCombatTick(this.b, this.a);
    }
}

