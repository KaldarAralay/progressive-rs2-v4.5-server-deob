/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.MonkeyMadnessQuest;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

final class DaeroTrainingXpRewardTask
extends TickTask {
    private /* synthetic */ MonkeyMadnessQuest a;
    private final /* synthetic */ Player b;
    private final /* synthetic */ int c;
    private final /* synthetic */ int d;

    DaeroTrainingXpRewardTask(MonkeyMadnessQuest monkeyMadnessQuest, int n, Player player, int n2, int n3) {
        this.a = monkeyMadnessQuest;
        this.b = player;
        this.c = n2;
        this.d = n3;
        super(10);
    }

    @Override
    public final void execute() {
        Player player = this.b;
        player.packetSender.showWalkableInterface(-1);
        player = this.b;
        player.packetSender.closeInterfaces();
        this.b.setActionLocked(false);
        if (this.c == 1) {
            player = this.b;
            player.packetSender.sendGameMessage("You gain 35,000 Strength XP.");
            player = this.b;
            player.packetSender.sendGameMessage("You gain 35,000 Hitpoints XP.");
            player = this.b;
            player.packetSender.sendGameMessage("You gain 20,000 Attack XP.");
            player = this.b;
            player.packetSender.sendGameMessage("You gain 20,000 Defence XP.");
            this.b.getSkillManager().addQuestExperience(2, 35000.0);
            this.b.getSkillManager().addQuestExperience(3, 35000.0);
            this.b.getSkillManager().addQuestExperience(0, 20000.0);
            this.b.getSkillManager().addQuestExperience(1, 20000.0);
        } else {
            player = this.b;
            player.packetSender.sendGameMessage("You gain 35,000 Attack XP.");
            player = this.b;
            player.packetSender.sendGameMessage("You gain 35,000 Defence XP.");
            player = this.b;
            player.packetSender.sendGameMessage("You gain 20,000 Strength XP.");
            player = this.b;
            player.packetSender.sendGameMessage("You gain 20,000 Hitpoints XP.");
            this.b.getSkillManager().addQuestExperience(0, 35000.0);
            this.b.getSkillManager().addQuestExperience(1, 35000.0);
            this.b.getSkillManager().addQuestExperience(2, 20000.0);
            this.b.getSkillManager().addQuestExperience(3, 20000.0);
        }
        if (!this.a.e(this.b, 17)) {
            int n = this.d;
            this.b.questProgressFlags[n] = this.b.questProgressFlags[n] + GameUtil.bitFlag(17);
        }
        this.stop();
    }
}

