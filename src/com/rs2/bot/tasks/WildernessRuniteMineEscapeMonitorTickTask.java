/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.tasks;

import com.rs2.bot.combat.BotCombatEscapeHandler;
import com.rs2.bot.tasks.WildernessRuniteMineBotTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

final class WildernessRuniteMineEscapeMonitorTickTask
extends TickTask {
    private final /* synthetic */ Player a;

    WildernessRuniteMineEscapeMonitorTickTask(WildernessRuniteMineBotTask wildernessRuniteMineBotTask, int n, Player player) {
        this.a = player;
        super(2);
    }

    @Override
    public final void execute() {
        if (this.a.isDead() || !this.a.bW() || !this.a.currentBotTask.usesEscapeMonitor) {
            this.stop();
            return;
        }
        if (this.a.getInventoryManager().getItemAmount(451) >= 8 && this.a.botTaskState.equals("do task") && GameUtil.h(3) == 0) {
            this.a.currentBotTask.startWalkToBank(this.a);
            return;
        }
        if (this.a.getInventoryManager().getContainer().getFreeSlots() <= 0 && this.a.botTaskState.equals("do task")) {
            this.a.currentBotTask.startWalkToBank(this.a);
            return;
        }
        if (!this.a.getRecentCombatTimer().hasElapsed()) {
            BotCombatEscapeHandler.a(this.a);
            this.a.botTaskState = "escape";
        }
    }
}

