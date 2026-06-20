/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.bot.tasks;

import com.rs2.bot.tasks.AlKharidCowhideTanningBotTask;
import com.rs2.model.GameplayHelper;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;

final class TanningBotTickTask
extends TickTask {
    private final /* synthetic */ Player a;

    TanningBotTickTask(AlKharidCowhideTanningBotTask alKharidCowhideTanningBotTask, int n, Player player) {
        this.a = player;
        super(2);
    }

    @Override
    public final void execute() {
        if (this.a.isDead() || !this.a.isRegistered() || !this.a.currentBotTask.usesCustomTaskAction || !this.a.botTaskState.equals("do task")) {
            this.stop();
            return;
        }
        if (!this.a.getInventoryManager().containsItem(this.a.botTaskItemId)) {
            this.a.currentBotTask.startWalkToBank(this.a);
            this.stop();
            return;
        }
        if (this.a.getOpenInterfaceId() == 14670 || this.a.getOpenInterfaceId() == 679) {
            Player player = this.a;
            int n = player.botTaskItemId;
            int n2 = 1;
            int n3 = 1;
            if (n == 1739) {
                if (player.getSkillManager().getCurrentLevels()[12] < 28) {
                    n2 = 1;
                    n3 = 1741;
                } else {
                    n2 = 3;
                    n3 = 1743;
                }
            } else if (n == 1753) {
                n2 = 20;
                n3 = 1745;
            } else if (n == 1751) {
                n2 = 20;
                n3 = 2505;
            } else if (n == 1749) {
                n2 = 20;
                n3 = 2507;
            } else if (n == 1747) {
                n2 = 20;
                n3 = 2509;
            }
            GameplayHelper.b(player, 27, n2, n, n3);
            return;
        }
        this.a.botInteractionOption = 2;
        this.a.interactWithBotNpcTargets(this.a.botInteractionTargetIds);
    }
}

