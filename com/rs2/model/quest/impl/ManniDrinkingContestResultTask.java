/*
 * Decompiled with CFR 0.152.
 */
package com.rs2.model.quest.impl;

import com.rs2.model.player.Player;
import com.rs2.model.quest.impl.ManniDrinkingContestPlayerDrinkTask;
import com.rs2.model.task.TickTask;
import com.rs2.util.GameUtil;

final class ManniDrinkingContestResultTask
extends TickTask {
    private final /* synthetic */ Player a;
    private final /* synthetic */ int b;

    ManniDrinkingContestResultTask(ManniDrinkingContestPlayerDrinkTask manniDrinkingContestPlayerDrinkTask, int n, Player player, int n2) {
        this.a = player;
        this.b = n2;
        super(7);
    }

    @Override
    public final void execute() {
        this.a.n(false);
        if ((this.a.bI[this.b] & GameUtil.b(1)) == 0) {
            int n = this.b;
            this.a.bI[n] = this.a.bI[n] + GameUtil.b(1);
        }
        if (this.a.pendingGameMode == 3711) {
            if ((this.a.getQuestState(this.b) & GameUtil.b(3)) == 0) {
                this.a.addQuestState(this.b, GameUtil.b(3));
            }
            this.a.getDialogueManager().showPlayerTwoLineDialogue("Aaaah, lovely stuff. So you want to get the next round", "in, or shall I? You don't look so good there!", 591);
            Player player = this.a;
            player.packetSender.sendGameMessage("Congratulations! You have completed the Revellers' trial.");
            this.a.getDialogueManager().setNextDialogueStep(8);
        } else {
            this.a.getDialogueManager().showPlayerTwoLineDialogue("Ish no' fair! (hic) I canna drink another drop! I alsho", "feel veddy, veddy ill...", 591);
        }
        this.stop();
    }
}

