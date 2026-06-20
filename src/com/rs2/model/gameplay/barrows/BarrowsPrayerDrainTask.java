/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.gameplay.barrows;

import com.rs2.model.World;
import com.rs2.model.gameplay.barrows.BarrowsManager;
import com.rs2.model.gameplay.barrows.BarrowsPrayerDrainResetTask;
import com.rs2.model.player.Player;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

final class BarrowsPrayerDrainTask
extends TickTask {
    private final /* synthetic */ Player a;

    BarrowsPrayerDrainTask(int n, Player player) {
        this.a = player;
        super(30);
    }

    @Override
    public final void execute() {
        if (!this.a.bW()) {
            this.stop();
            return;
        }
        if (this.a.isInBarrows()) {
            int n = BarrowsManager.a[GameUtil.h(12)];
            int n2 = BarrowsManager.b[GameUtil.h(6)];
            Player player = this.a;
            player.packetSender.sendInterfaceModel(n2, 100, n);
            player = this.a;
            player.packetSender.sendInterfaceAnimation(n2, 2085);
            n = BarrowsManager.d(this.a);
            int[] nArray = this.a.getSkillManager().getCurrentLevels();
            nArray[5] = nArray[5] - (n += 8);
            if (this.a.getSkillManager().getCurrentLevels()[5] < 0) {
                this.a.getSkillManager().getCurrentLevels()[5] = 0;
            }
            this.a.getSkillManager().refreshSkill(5);
            BarrowsPrayerDrainResetTask barrowsPrayerDrainResetTask = new BarrowsPrayerDrainResetTask(this, 3, this.a, n2);
            World.getTaskScheduler().schedule(barrowsPrayerDrainResetTask);
            return;
        }
        this.a.eq = -1;
        this.stop();
    }
}

